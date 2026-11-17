package com.example.automotriz.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(AdminInitializer.class);

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    private final String usuario;
    private final String clave;

    public AdminInitializer(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder,
                            @Value("${app.admin.usuario:admin}") String usuario,
                            @Value("${app.admin.clave:}") String clave) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        this.usuario = usuario;
        this.clave = clave;
    }

    @Override
    public void run(String... args) {
        // Solo se crea si NO existe ningún administrador. Nunca se pisa nada existente.
        Integer admins = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM empleado WHERE rol = 'ADMINISTRADOR'", Integer.class);
        if (admins != null && admins > 0) return;

        if (clave.isBlank()) {
            log.warn("No hay administradores y app.admin.clave está vacío: define ADMIN_CLAVE para crear el primero.");
            return;
        }

        jdbcTemplate.update("""
                INSERT INTO empleado (nombre, correo, codigo, usuario, contrasenia, activo, rol)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """,
                "Administrador del sistema", "admin@2gautomotriz.local", "EMP-001",
                usuario, passwordEncoder.encode(clave), true, "ADMINISTRADOR");
        log.info("Administrador inicial '{}' creado.", usuario);
    }
}