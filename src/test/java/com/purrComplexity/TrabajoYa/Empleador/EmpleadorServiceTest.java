package com.purrComplexity.TrabajoYa.Empleador;

import com.purrComplexity.TrabajoYa.Empleador.Repository.EmpleadorRepository;
import com.purrComplexity.TrabajoYa.Empleador.Service.EmpleadorService;
import com.purrComplexity.TrabajoYa.Empleador.dto.EmpleadorRequestDTO;
import com.purrComplexity.TrabajoYa.Empleador.dto.EmpleadorResponseDTO;
import com.purrComplexity.TrabajoYa.EmpleoA.EmpleoA;
import com.purrComplexity.TrabajoYa.EmpleoA.Service.EmpleoAService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmpleadorServiceTest {

    @Mock
    private EmpleadorRepository empleadorRepository;

    private EmpleadorService empleadorService;

    private ModelMapper modelMapper;

    private Empleador empleador;
    private EmpleadorRequestDTO empleadorRequestDTO;

    @BeforeEach
    public void setup() {
        modelMapper = new ModelMapper();

        empleadorService = new EmpleadorService(empleadorRepository, modelMapper);

        empleador=new Empleador();

        empleador.setRuc("123");
        empleador.setRazonSocial("Intercorp");
        empleador.setTelefonoPrincipal(912321231L);

        empleadorRequestDTO=new EmpleadorRequestDTO();

        empleadorRequestDTO.setRuc("123");
        empleadorRequestDTO.setRazonSocial("Intercorp");
        empleadorRequestDTO.setTelefonoPrincipal(912321231L);
    }

    @Test
    public void testCreateEmpleador() {
        when(empleadorRepository.save(any(Empleador.class))).thenReturn(empleador);

        EmpleadorResponseDTO result=empleadorService.crearEmpleador(empleadorRequestDTO);

        assertNotNull(result);

        assertEquals("Intercorp", result.getRazonSocial());
        assertEquals(912321231, result.getTelefonoPrincipal());

        verify(empleadorRepository).save(any(Empleador.class));
    }

    @Test
    public void testEmpleadorNoEncontrad(){
        when(empleadorRepository.findById(anyString())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            empleadorService.obtenerEmpleadorPorId("10");
        });

        assertTrue(exception.getMessage().contains("No se encontró al empleador"));

    }

}
