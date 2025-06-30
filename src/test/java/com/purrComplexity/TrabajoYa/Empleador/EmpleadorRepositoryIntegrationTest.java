package com.purrComplexity.TrabajoYa.Empleador;


import com.purrComplexity.TrabajoYa.Empleador.Repository.EmpleadorRepository;
import com.purrComplexity.TrabajoYa.EmpleoA.EmpleoA;
import com.purrComplexity.TrabajoYa.EmpleoA.Repository.EmpleoARepository;
import com.purrComplexity.TrabajoYa.config.PostgresTestContainerConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Testcontainers
@Import(PostgresTestContainerConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")  // Opcional, si usas perfil test
public class EmpleadorRepositoryIntegrationTest {

    @Autowired
    private EmpleadorRepository empleadorRepository;

    @Test
    public void testGuardarYBuscarEmpleoA() {
        Empleador empleador=new Empleador();

        empleador.setRuc("123");
        empleador.setRazonSocial("Intercorp");
        empleador.setTelefonoPrincipal(932145643L);
        empleador.setCorreo("inter12@gmail.com");

        Empleador saved = empleadorRepository.save(empleador);

        assertThat(saved).isNotNull();
        assertThat(saved.getRuc()).isNotNull();
        assertThat(saved.getRuc()).isEqualTo("123");

        Empleador found = empleadorRepository.findById(saved.getRuc()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getCorreo()).isEqualTo("inter12@gmail.com");
    }


}