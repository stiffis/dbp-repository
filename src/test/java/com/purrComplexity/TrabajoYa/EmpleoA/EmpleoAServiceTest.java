package com.purrComplexity.TrabajoYa.EmpleoA;

import com.purrComplexity.TrabajoYa.Empleador.Empleador;
import com.purrComplexity.TrabajoYa.Empleador.Repository.EmpleadorRepository;
import com.purrComplexity.TrabajoYa.Empleador.Service.EmpleadorService;
import com.purrComplexity.TrabajoYa.EmpleoA.Repository.EmpleoARepository;
import com.purrComplexity.TrabajoYa.EmpleoA.Service.EmpleoAService;
import com.purrComplexity.TrabajoYa.EmpleoA.dto.EmpleoARequestDTO;
import com.purrComplexity.TrabajoYa.EmpleoA.dto.EmpleoAResponseDTO;
import com.purrComplexity.TrabajoYa.Enum.Habilidad;
import com.purrComplexity.TrabajoYa.Enum.SistemaRemuneracion;
import com.purrComplexity.TrabajoYa.Enum.WeekDays;
import com.purrComplexity.TrabajoYa.Enum.modalidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmpleoAServiceTest {

    @Mock
    private EmpleoARepository empleoARepository;

    @Mock
    private EmpleadorRepository empleadorRepository;


    private EmpleoAService empleoAService;

    private ModelMapper modelMapper;

    private EmpleoA testEmpleoA;
    private EmpleoARequestDTO testempleoARequestDTO;
    private Empleador testempleador;

    @BeforeEach
    public void setup() {
        modelMapper = new ModelMapper(); // 🔧 Instancia real

        empleoAService = new EmpleoAService(empleoARepository, empleadorRepository, modelMapper);// 🔧 Inyección manual

        testempleador=new Empleador();
        testempleador.setRuc("123");
        testempleador.setRazonSocial("telecorp");
        testempleador.setTelefonoPrincipal(932123458L);
        testempleador.setCorreo("correo@gmal.com");

        testEmpleoA = new EmpleoA();
        testEmpleoA.setIdOfertaEmpleo(1L);
        testEmpleoA.setPeriodoPago("Mensual");
        testEmpleoA.setMontoPorPeriodo(2000L);
        testEmpleoA.setModalidadEmpleo(modalidad.VIRTUAL);
        testEmpleoA.setLugar("Av.Juarez");
        testEmpleoA.setHabilidades(Habilidad.ARCHIVADO_DOCUMENTOS);
        testEmpleoA.setNumeroPostulaciones(1L);
        testEmpleoA.setImagen("https://ejemplo.com/logo.png");
        testEmpleoA.setFechaLimite(LocalDateTime.now().plusDays(30));
        testEmpleoA.setSistemaRemuneracion(SistemaRemuneracion.FIJO);
        testEmpleoA.setHoursPerDay("2");
        testEmpleoA.setWeekDays(WeekDays.MO);
        testEmpleoA.setPuesto("Contador");
        testEmpleoA.setFuncionesPuesto("Desarrollo y mantenimiento de APIs.");
        testEmpleoA.setTipoEmpleo("Tiempo completo");
        testEmpleoA.setContratos(Collections.emptyList());

        testempleoARequestDTO = new EmpleoARequestDTO();
        testempleoARequestDTO.setPeriodoPago("Mensual");
        testempleoARequestDTO.setMontoPorPeriodo(2000L);
        testempleoARequestDTO.setModalidadEmpleo(modalidad.VIRTUAL);
        testempleoARequestDTO.setLugar("Av.Juarez");
        testempleoARequestDTO.setHabilidades(Habilidad.ARCHIVADO_DOCUMENTOS);
        testempleoARequestDTO.setNumeroPostulaciones(1L);
        testempleoARequestDTO.setImagen("https://ejemplo.com/logo.png");
        testempleoARequestDTO.setFechaLimite(LocalDateTime.now().plusDays(30));
        testempleoARequestDTO.setSistemaRemuneracion(SistemaRemuneracion.FIJO);
        testempleoARequestDTO.setHoursPerDay("2");
        testempleoARequestDTO.setWeekDays(WeekDays.MO);
        testempleoARequestDTO.setPuesto("Contador");
        testempleoARequestDTO.setFuncionesPuesto("Contar la cantidad de dinero en caja.");
        testempleoARequestDTO.setTipoEmpleo("Tiempo completo");
    }

    @Test
    public void testCreateEmpleoA() {
        when(empleoARepository.save(any(EmpleoA.class))).thenReturn(testEmpleoA);
        when(empleadorRepository.findById(anyString())).thenReturn(Optional.of(testempleador));

        EmpleoAResponseDTO result = empleoAService.crearYAsignarEmpleoA(testempleoARequestDTO,"1234");

        assertNotNull(result);


        assertEquals("Av.Juarez", result.getLugar());
        assertEquals("Mensual", result.getPeriodoPago());
        assertEquals(2000, result.getMontoPorPeriodo());
        assertEquals("telecorp",result.getRazonSocial());
        assertEquals(Habilidad.ARCHIVADO_DOCUMENTOS, result.getHabilidades());
        assertEquals(modalidad.VIRTUAL, result.getModalidadEmpleo());

        verify(empleoARepository).save(any(EmpleoA.class));
        verify(empleadorRepository).findById(anyString());
    }

    @Test
    public void testEmpleoANoEncontrado(){
        when(empleoARepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            empleoAService.obtenerEmpleoAPorId(10L);
        });

        assertTrue(exception.getMessage().contains("No se encontró el Empleo de tipo A"));

        verify(empleoARepository).findById(anyLong());

    }

}

