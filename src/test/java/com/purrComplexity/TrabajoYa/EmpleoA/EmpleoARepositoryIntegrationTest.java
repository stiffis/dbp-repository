package com.purrComplexity.TrabajoYa.EmpleoA;

import com.purrComplexity.TrabajoYa.EmpleoA.Repository.EmpleoARepository;
import com.purrComplexity.TrabajoYa.Enum.Habilidad;
import com.purrComplexity.TrabajoYa.Enum.SistemaRemuneracion;
import com.purrComplexity.TrabajoYa.Enum.modalidad;
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
public class EmpleoARepositoryIntegrationTest {

    @Autowired
    private EmpleoARepository empleoARepository;
    /*
    @Test
    public void testGuardarYBuscarEmpleoA() {
        EmpleoA empleo = new EmpleoA();
        empleo.setLugar("Av.Juarez");
        empleo.setPeriodoPago("Mensual");
        empleo.setMontoPorPeriodo(2000L);
        empleo.setModalidadEmpleo(modalidad.VIRTUAL);
        empleo.setHabilidades(Habilidad.ARCHIVADO_DOCUMENTOS);
        empleo.setNumeroPostulaciones(1L);
        empleo.setImagen("https://ejemplo.com/logo.png");
        empleo.setFechaLimite(LocalDateTime.now().plusDays(30));
        empleo.setSistemaRemuneracion(SistemaRemuneracion.FIJO);
        empleo.setHoursPerDay("2");
        empleo.setWeekDays(com.purrComplexity.TrabajoYa.Enum.WeekDays.MO);
        empleo.setPuesto("Contador");
        empleo.setFuncionesPuesto("Contar la cantidad de dinero en la caja");
        empleo.setTipoEmpleo("Tiempo completo");
        empleo.setContratos(Collections.emptyList());

        EmpleoA saved = empleoARepository.save(empleo);

        assertThat(saved).isNotNull();
        assertThat(saved.getIdOfertaEmpleo()).isNotNull();

        EmpleoA found = empleoARepository.findById(saved.getIdOfertaEmpleo()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getLugar()).isEqualTo("Av.Juarez");
        assertThat(found.getFuncionesPuesto()).isEqualTo("Contar la cantidad de dinero en la caja");
        assertThat(found.getSistemaRemuneracion()).isEqualTo(SistemaRemuneracion.FIJO);
    }
    */

}
