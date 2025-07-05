/*package com.purrComplexity.TrabajoYa.Contrato;

import com.purrComplexity.TrabajoYa.Contrato.dto.ContratoDTO;
import com.purrComplexity.TrabajoYa.Contrato.dto.CreateContratoDTO;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.Repository.OfertaEmpleoRepository;
import com.purrComplexity.TrabajoYa.Trabajador.PersonaRepository;
import com.purrComplexity.TrabajoYa.exception.FutureDateNotAllowedException;
import com.purrComplexity.TrabajoYa.exception.ResourceNotFoundException;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.OfertaEmpleo;
import com.purrComplexity.TrabajoYa.Trabajador.Persona;
import com.purrComplexity.TrabajoYa.Contrato.mapper.ContratoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContratoServiceTest{

    @Mock
    private OfertaEmpleoRepository ofertaEmpleoRepository;

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private ContratoRepository contratoRepository;

    @Mock
    private ContratoMapper contratoMapper;

    @InjectMocks
    private ContratoServiceImpl contratoService;

    @Test
    void createContrato_DatosValidos_RetornaContratoDTO() {
        // Arrange
        CreateContratoDTO dto = new CreateContratoDTO(
                1L, // personaContratadaId
                1L, // ofertaEmpleoId
                new Date(), // fechaPasada
                List.of(4, 5) // calificaciones
        );

        Persona personaMock = new Persona();
        OfertaEmpleo ofertaMock = new OfertaEmpleo();
        Contrato contratoMock = new Contrato();
        ContratoDTO dtoMock = new ContratoDTO();

        // Configurar mocks
        when(personaRepository.findById(1L)).thenReturn(Optional.of(personaMock));
        when(ofertaEmpleoRepository.findById(1L)).thenReturn(Optional.of(ofertaMock));
        when(contratoMapper.toEntity(any(), any(), any())).thenReturn(contratoMock);
        when(contratoRepository.save(any())).thenReturn(contratoMock);
        when(contratoMapper.toDTO(any())).thenReturn(dtoMock);

        // Act
        ContratoDTO resultado = contratoService.createContrato(dto);

        // Assert
        assertNotNull(resultado);
        verify(personaRepository, times(1)).findById(1L);
        verify(ofertaEmpleoRepository, times(1)).findById(1L);
        verify(contratoRepository, times(1)).save(any());
    }

    // validando exepciones
    @Test
    void createContrato_PersonaNoExiste_LanzaExcepcion() {
        CreateContratoDTO dto = new CreateContratoDTO(999L, 1L, new Date(), List.of(4));
        when(personaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                contratoService.createContrato(dto)
        );
    }

    // Exepcion testeando que fecha futura no se permita
    @Test
    void createContrato_FechaFutura_LanzaExcepcion() {
        CreateContratoDTO dto = new CreateContratoDTO(
                1L,
                1L,
                new Date(System.currentTimeMillis() + 86400000), // Fecha futura
                List.of(4)
        );

        when(personaRepository.findById(1L)).thenReturn(Optional.of(new Persona()));
        when(ofertaEmpleoRepository.findById(1L)).thenReturn(Optional.of(new OfertaEmpleo()));

        assertThrows(FutureDateNotAllowedException.class, () ->
                contratoService.createContrato(dto)
        );
    }



}
*/
