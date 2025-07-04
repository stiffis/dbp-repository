/*package com.purrComplexity.TrabajoYa.EmpleoB;

import com.purrComplexity.TrabajoYa.Empleador.Empleador;
import com.purrComplexity.TrabajoYa.Empleador.Repository.EmpleadorRepository;
import com.purrComplexity.TrabajoYa.EmpleoB.Repository.EmpleoBRepository;
import com.purrComplexity.TrabajoYa.EmpleoB.Service.EmpleoBService;
import com.purrComplexity.TrabajoYa.EmpleoB.dto.EmpleoBRequestDTO;
import com.purrComplexity.TrabajoYa.EmpleoB.dto.EmpleoBResponseDTO;
import com.purrComplexity.TrabajoYa.Enum.Habilidad;
import com.purrComplexity.TrabajoYa.Enum.SistemaRemuneracion;
import com.purrComplexity.TrabajoYa.Enum.WeekDays;
import com.purrComplexity.TrabajoYa.Enum.modalidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmpleoBServiceTest {

    @Mock
    private EmpleoBRepository empleoBRepository;

    @Mock
    private  EmpleadorRepository empleadorRepository;

    private EmpleoBService empleoBService;

    private ModelMapper modelMapper;

    private EmpleoB testEmpleoB;
    private EmpleoBRequestDTO empleoBRequestDTO;
    private Empleador testempleador;

    @BeforeEach
    public void setup() {
        modelMapper = new ModelMapper(); // 🔧 Instancia real

        empleoBService = new EmpleoBService(empleoBRepository, empleadorRepository, modelMapper);// 🔧 Inyección manual

        testempleador=new Empleador();
        testempleador.setRuc("123");
        testempleador.setRazonSocial("Aron");
        testempleador.setTelefonoPrincipal(932123458L);
        testempleador.setCorreo("correo@gmail.com");

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

        empleoBRequestDTO=new EmpleoBRequestDTO();

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


    }

    @Test
    public void testCreateEmpleoB() {
        when(empleoBRepository.save(any(EmpleoB.class))).thenReturn(testEmpleoB);
        when(empleadorRepository.findById(anyString())).thenReturn(Optional.of(testempleador));

        EmpleoBResponseDTO result = empleoBService.crearYAsignarEmpleoB(empleoBRequestDTO,"1234");

        assertNotNull(result);


        assertEquals("Av.Juarez", result.getLugar());
        assertEquals("Mensual", result.getPeriodoPago());
        assertEquals(2000, result.getMontoPorPeriodo());
        assertEquals("Aron",result.getRazonSocial());
        assertEquals(Habilidad.ARCHIVADO_DOCUMENTOS, result.getHabilidades());
        assertEquals(modalidad.VIRTUAL, result.getModalidadEmpleo());

        verify(empleoBRepository).save(any(EmpleoB.class));
        verify(empleadorRepository).findById(anyString());

    }

    @Test
    public void testEmpleoBNoEncontrado(){
        when(empleoBRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            empleoBService.obtenerEmpleoBPorId(10L);
        });

        assertTrue(exception.getMessage().contains("No se encontro el Empleo Tipo B"));

        verify(empleoBRepository).findById(anyLong());

    }
}
*/