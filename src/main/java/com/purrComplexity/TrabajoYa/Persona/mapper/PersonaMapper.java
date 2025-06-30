package com.purrComplexity.TrabajoYa.Persona.mapper;

import com.purrComplexity.TrabajoYa.Persona.Persona;
import com.purrComplexity.TrabajoYa.Persona.dto.PersonaDTO;
import com.purrComplexity.TrabajoYa.Persona.dto.CreatePersonaDTO;
import com.purrComplexity.TrabajoYa.Persona.dto.UpdatePersonaDTO;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper {
    
    public PersonaDTO toDTO(Persona persona) {
        PersonaDTO dto = new PersonaDTO();
        dto.setUbicacion(persona.getUbicacion());
        dto.setCorreo(persona.getCorreo());
        dto.setFechaNacimiento(persona.getFechaNacimiento());
        dto.setDni(persona.getDni());
        dto.setNombresCompletos(persona.getNombresCompletos());
        return dto;
    }
    
    public Persona toEntity(CreatePersonaDTO dto) {
        Persona persona = new Persona();
        persona.setUbicacion(dto.getUbicacion());
        persona.setCorreo(dto.getCorreo());
        persona.setFechaNacimiento(dto.getFechaNacimiento());
        persona.setHabilidades(dto.getHabilidades());
        persona.setDni(dto.getDni());
        persona.setNombresCompletos(dto.getNombresCompletos());
        return persona;
    }
    
    public void updateEntityFromDTO(UpdatePersonaDTO dto, Persona persona) {
        if (dto.getUbicacion() != null) {
            persona.setUbicacion(dto.getUbicacion());
        }
        if (dto.getCorreo() != null) {
            persona.setCorreo(dto.getCorreo());
        }
        if (dto.getFechaNacimiento() != null) {
            persona.setFechaNacimiento(dto.getFechaNacimiento());
        }
        if (dto.getDni() != null) {
            persona.setDni(dto.getDni());
        }
        if (dto.getNombresCompletos() != null) {
            persona.setNombresCompletos(dto.getNombresCompletos());
        }
    }
}

