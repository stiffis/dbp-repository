package com.purrComplexity.TrabajoYa.CalificacionPersona.Service;

import com.purrComplexity.TrabajoYa.CalificacionPersona.CalificacionPersona;
import com.purrComplexity.TrabajoYa.CalificacionPersona.CalificacionPersonaRepository;
import com.purrComplexity.TrabajoYa.CalificacionPersona.dto.CalificacionPersonaRequestDTO;
import com.purrComplexity.TrabajoYa.CalificacionPersona.dto.CalificacionPersonaResponseDTO;
import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.Contrato.ContratoRepository;
import com.purrComplexity.TrabajoYa.Persona.Exceptions.PersonaNotFound;
import com.purrComplexity.TrabajoYa.Persona.Persona;
import com.purrComplexity.TrabajoYa.Persona.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalificacionPersonaService {

    private final ModelMapper modelMapper;
    private final CalificacionPersonaRepository calificacionPersonaRepository;
    private final PersonaRepository personaRepository;
    private final ContratoRepository contratoRepository;

    public CalificacionPersonaResponseDTO crearCalificacion(Long idContrato, Long idPersona,
                                                            CalificacionPersonaRequestDTO calificacionPersonaRequestDTO){
        Persona persona=personaRepository.findById(idPersona).orElseThrow(
                ()->new PersonaNotFound("La persona con ese id no exsite")
        );

        Contrato contrato=contratoRepository.findById(idContrato).orElseThrow(
                ()->new RuntimeException("El contrato con ese id no existe")
        );

        if (contrato.getPersonaContratada()!=persona){
            throw new RuntimeException("Este contrato no pertenece a la persona con este id");
        }

        CalificacionPersona calificacionPersona=modelMapper.map(calificacionPersonaRequestDTO,CalificacionPersona.class);

        calificacionPersona=calificacionPersonaRepository.save(calificacionPersona);

        CalificacionPersonaResponseDTO calificacionPersonaResponseDTO=modelMapper.map(calificacionPersona,CalificacionPersonaResponseDTO.class);

        calificacionPersonaResponseDTO.setContratoId(contrato.getId());

        return calificacionPersonaResponseDTO;

    }

}
