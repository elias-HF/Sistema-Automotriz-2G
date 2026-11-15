package com.example.automotriz;

import com.example.automotriz.entity.ClienteEntity;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ValidacionClienteTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void probarClienteConDatosInvalidos() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNombre(""); //inválido si tiene @NotBlank
        cliente.setCorreo("correo-invalido"); // Sin formato de email válido

        var violaciones = validator.validate(cliente);

        // Se comprueba que JUnit detectó los errores de validación
        assertFalse(violaciones.isEmpty(), "Las validaciones deben rechazar datos vacíos o incorrectos");
    }

}
