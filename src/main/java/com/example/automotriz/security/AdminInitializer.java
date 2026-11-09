package com.example.automotriz.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Integer existe = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM empleado WHERE usuario = ?", Integer.class, "admin");

        if (existe != null && existe == 0) {
            jdbcTemplate.update(
                    """
                    INSERT INTO empleado
                    (nombre, correo, codigo, usuario, contrasenia, activo, rol)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                    """,
                    "Administrador del sistema",
                    "admin@2gautomotriz.local",
                    "EMP-001",
                    "admin",
                    passwordEncoder.encode("Admin123!"),
                    true,
                    "ADMINISTRADOR"
            );
            System.out.println("Administrador de prueba creado.");
        }
    }
}