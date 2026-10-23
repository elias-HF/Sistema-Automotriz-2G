package com.example.automotriz.security;

import com.example.automotriz.entity.EmpleadoEntity;
import com.example.automotriz.repository.EmpleadoRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpleadoUserDetailsService implements UserDetailsService {
    private final EmpleadoRepository empleadoRepository;
    public EmpleadoUserDetailsService(EmpleadoRepository empleadoRepository) {this.empleadoRepository = empleadoRepository;}

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmpleadoEntity empleado = empleadoRepository.findByUsuario(username).orElseThrow(() -> new UsernameNotFoundException("Empleado no encontrado"));

        return User.withUsername(empleado.getUsuario())
                .password(empleado.getContrasenia())
                .authorities("ROLE_" + empleado.getRol().getNombre())
                .disabled(!empleado.getActivo())
                .build();
    }
}

