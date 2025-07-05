/*package com.purrComplexity.TrabajoYa.Persona;

import com.purrComplexity.TrabajoYa.Empleador.Empleador;
import com.purrComplexity.TrabajoYa.EmpleoA.EmpleoA;
import com.purrComplexity.TrabajoYa.EmpleoA.Repository.EmpleoARepository;
import com.purrComplexity.TrabajoYa.EmpleoA.dto.EmpleoARequestDTO;
import com.purrComplexity.TrabajoYa.EmpleoA.dto.EmpleoAResponseDTO;
import com.purrComplexity.TrabajoYa.EmpleoB.EmpleoB;
import com.purrComplexity.TrabajoYa.EmpleoB.Repository.EmpleoBRepository;
import com.purrComplexity.TrabajoYa.EmpleoB.Service.EmpleoBService;
import com.purrComplexity.TrabajoYa.EmpleoB.dto.EmpleoBRequestDTO;
import com.purrComplexity.TrabajoYa.Enum.Habilidad;
import com.purrComplexity.TrabajoYa.Enum.SistemaRemuneracion;
import com.purrComplexity.TrabajoYa.Enum.WeekDays;
import com.purrComplexity.TrabajoYa.Enum.modalidad;
import com.purrComplexity.TrabajoYa.Persona.dto.CreatePersonaDTO;
import com.purrComplexity.TrabajoYa.Persona.dto.PersonaPostulaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonaServiceTest {
    @Mock
    private EmpleoBRepository empleoBRepository;

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private EmpleoARepository empleoARepository;

    private PersonaServiceImpl personaService;

    private ModelMapper modelMapper;

    private Persona testpersona;
    private CreatePersonaDTO createPersonaDTO;
    private EmpleoA testEmpleoA;
    private EmpleoARequestDTO testempleoARequestDTO;
    private EmpleoB testEmpleoB;
    private EmpleoBRequestDTO empleoBRequestDTO;

    @BeforeEach
    public void setup() {
        modelMapper = new ModelMapper();

/*
        personaService = new PersonaServiceImpl(personaRepository, modelMapper, empleoARepository, empleoBRepository);


        testpersona = new Persona();
        testpersona.setId(1L);
        testpersona.setUbicacion("Lima");
        testpersona.setCorreo("correo@example.com");
        testpersona.setFechaNacimiento(new GregorianCalendar(1995, Calendar.JANUARY, 1).getTime());
        testpersona.setHabilidades(Habilidad.ARCHIVADO_DOCUMENTOS);
        testpersona.setDni("12345678");
        testpersona.setNombresCompletos("Juan Pérez");

        testEmpleoB = new EmpleoB();
        testEmpleoB.setIdOfertaEmpleo(1L);
        testEmpleoB.setPeriodoPago("Mensual");
        testEmpleoB.setMontoPorPeriodo(2000L);
        testEmpleoB.setModalidadEmpleo(modalidad.VIRTUAL);
        testEmpleoB.setLugar("Av.Juarez");
        testEmpleoB.setHabilidades(Habilidad.ARCHIVADO_DOCUMENTOS);
        testEmpleoB.setNumeroPostulaciones(1L);
        testEmpleoB.setImagen("https://ejemplo.com/logo.png");
        testEmpleoB.setFechaLimite(LocalDateTime.now().plusDays(30));
        testEmpleoB.setSistemaRemuneracion(SistemaRemuneracion.FIJO);
        testEmpleoB.setHoursPerDay("2");
        testEmpleoB.setWeekDays(WeekDays.MO);
        testEmpleoB.setContratos(Collections.emptyList());
        testEmpleoB.setDescripcion("Arreglar los documentos del escritorio");

        empleoBRequestDTO = new EmpleoBRequestDTO();

        empleoBRequestDTO.setPeriodoPago("Mensual");
        empleoBRequestDTO.setMontoPorPeriodo(2000L);
        empleoBRequestDTO.setModalidadEmpleo(modalidad.VIRTUAL);
        empleoBRequestDTO.setLugar("Av.Juarez");
        empleoBRequestDTO.setHabilidades(Habilidad.ARCHIVADO_DOCUMENTOS);
        empleoBRequestDTO.setNumeroPostulaciones(1L);
        empleoBRequestDTO.setImagen("https://ejemplo.com/logo.png");
        empleoBRequestDTO.setFechaLimite(LocalDateTime.now().plusDays(30));
        empleoBRequestDTO.setSistemaRemuneracion(SistemaRemuneracion.FIJO);
        empleoBRequestDTO.setHoursPerDay("2");
        empleoBRequestDTO.setWeekDays(WeekDays.MO);
        empleoBRequestDTO.setDescripcion("Arreglar los documentos del escritorio");

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


    }

    @Test
    public void testPostularEmpleoTipoB() {
        when(personaRepository.save(any(Persona.class))).thenReturn(testpersona);
        when(empleoBRepository.findById(anyLong())).thenReturn(Optional.of(testEmpleoB));
        when(personaRepository.findById(anyLong())).thenReturn(Optional.of(testpersona));

        PersonaPostulaDTO result = personaService.postularEmpleoTipoB(testpersona.getId(), testEmpleoB.getIdOfertaEmpleo());

        assertNotNull(result);

        assertEquals("Juan Pérez", result.getNombresCompletos());
        assertEquals(testEmpleoB.getIdOfertaEmpleo(), result.getIdOfertaEmpleo());
        assertEquals(testpersona.getCorreo(), result.getCorreo());

        verify(empleoBRepository).findById(anyLong());
        verify(personaRepository).findById(anyLong());
        verify(personaRepository).save(any(Persona.class));

    }

    @Test
    public void testPostularEmpleoTipoA(){
        when(personaRepository.findById(anyLong())).thenReturn(Optional.of(testpersona));
        when(empleoARepository.findById(anyLong())).thenReturn(Optional.of(testEmpleoA));
        when(personaRepository.save(any(Persona.class))).thenReturn(testpersona);

        PersonaPostulaDTO result=personaService.postularEmpleoTipoA(testpersona.getId(),testEmpleoA.getIdOfertaEmpleo());

        assertNotNull(result);

        assertEquals("Juan Pérez", result.getNombresCompletos());
        assertEquals(testEmpleoA.getIdOfertaEmpleo(),result.getIdOfertaEmpleo());
        assertEquals(testpersona.getCorreo(),result.getCorreo());

        verify(personaRepository).findById(anyLong());
        verify(personaRepository).save(any(Persona.class));
        verify(empleoARepository).findById(anyLong());

    }


}
*/