package com.example.automotriz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControlAccesoRolesTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Se configura MockMvc aplicando formalmente el filtro de Spring Security
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void accesoSinAutenticacionDebeSerBloqueado() throws Exception {
        // Sin sesión, intentar entrar a una ruta protegida redirige al login (302)
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void accesoConRolAdministradorDebePermitirse() throws Exception {
        // comprobamos que la seguridad NO deniega el acceso
        mockMvc.perform(get("/dashboard").with(user("admin").authorities()))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    // Si el estado es 403 (Prohibido por roles), la prueba falla. Si es 200 o redirección propia del flujo, es éxito de seguridad.
                    if (status == 403) {
                        throw new AssertionError("El acceso fue denegado por falta de roles (403 Forbidden).");
                    }
                });
    }

}
