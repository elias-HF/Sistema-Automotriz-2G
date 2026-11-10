package com.example.automotriz.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminInitializer implements CommandLineRunner {
    private static final String USUARIO = "admin";
    private static final String CLAVE = "Admin123!";

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        List<String> hashes = jdbcTemplate.queryForList(
                "SELECT contrasenia FROM empleado WHERE usuario = ?", String.class, USUARIO);

        if (hashes.isEmpty()) {
            jdbcTemplate.update("""
                    INSERT INTO empleado (nombre, correo, codigo, usuario, contrasenia, activo, rol)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                    """,
                    "Administrador del sistema", "admin@2gautomotriz.local", "EMP-001",
                    USUARIO, passwordEncoder.encode(CLAVE), true, "ADMINISTRADOR");
            return;
        }

        // Ya existía: se repara para que siempre se pueda entrar (solo para desarrollo)
        jdbcTemplate.update("UPDATE empleado SET activo = TRUE, rol = 'ADMINISTRADOR' WHERE usuario = ?", USUARIO);
        if (!passwordEncoder.matches(CLAVE, hashes.get(0))) {
            jdbcTemplate.update("UPDATE empleado SET contrasenia = ? WHERE usuario = ?",
                    passwordEncoder.encode(CLAVE), USUARIO);
        }
    }
}