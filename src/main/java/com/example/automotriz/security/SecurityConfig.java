package com.example.automotriz.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private static final String ADMIN = "ADMINISTRADOR";
    private static final String RECEPCION = "RECEPCIONISTA";
    private static final String MECANICO = "MECANICO";
    private static final String ALMACEN = "ALMACEN";

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Bean
    public DaoAuthenticationProvider authenticationProvider(EmpleadoUserDetailsService uds, PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(uds);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, DaoAuthenticationProvider authenticationProvider) throws Exception {
        http
                .authenticationProvider(authenticationProvider)
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**", "/login", "/recuperar-password",
                                "/css/**", "/js/**", "/img/**", "/error").permitAll()

                        .requestMatchers("/dashboard/**").hasRole(ADMIN)
                        .requestMatchers("/empleados/**", "/servicios/**").hasRole(ADMIN)

                        .requestMatchers("/citas/**", "/comprobante/**").hasAnyRole(ADMIN, RECEPCION)
                        // Recepción: solo clientes y vehículos (el controlador limita las tarjetas)
                        .requestMatchers("/administracion/**", "/clientes/**", "/vehiculos/**").hasAnyRole(ADMIN, RECEPCION)

                        .requestMatchers("/ordenes/**").hasAnyRole(ADMIN, MECANICO)
                        .requestMatchers("/almacen/**", "/articulos/**").hasAnyRole(ADMIN, ALMACEN)

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler((req, res, auth) ->
                                res.sendRedirect(req.getContextPath() + rutaInicio(auth)))
                        .failureUrl("/login?error")
                        .permitAll()
                )

                // Si entra a una pestaña que no le corresponde, vuelve a la suya
                .exceptionHandling(ex -> ex.accessDeniedHandler((req, res, e) ->
                        res.sendRedirect(req.getContextPath()
                                + rutaInicio(SecurityContextHolder.getContext().getAuthentication()))))

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }

    private static String rutaInicio(Authentication auth) {
        if (tieneRol(auth, ADMIN)) return "/dashboard";
        if (tieneRol(auth, RECEPCION)) return "/citas";
        if (tieneRol(auth, MECANICO)) return "/ordenes";
        if (tieneRol(auth, ALMACEN)) return "/almacen";
        return "/login?logout";
    }

    private static boolean tieneRol(Authentication auth, String rol) {
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + rol));
    }
}