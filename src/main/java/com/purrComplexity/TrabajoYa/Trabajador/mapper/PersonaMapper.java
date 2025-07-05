package com.purrComplexity.TrabajoYa.Trabajador.mapper;

import com.purrComplexity.TrabajoYa.Trabajador.Trabajador;
import com.purrComplexity.TrabajoYa.Trabajador.dto.TrabajadorDTO;
import com.purrComplexity.TrabajoYa.Trabajador.dto.CreateTrabajadorDTO;
import com.purrComplexity.TrabajoYa.Trabajador.dto.UpdateTrabajadorDTO;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper {
    
    public TrabajadorDTO toDTO(Trabajador persona) {
        TrabajadorDTO dto = new TrabajadorDTO();
        dto.setLatitud(persona.getLatitud());
        dto.setLongitud(persona.getLongitud());
        dto.setCorreo(persona.getCorreo());
        dto.setFechaNacimiento(persona.getFechaNacimiento());
        dto.setDni(persona.getDni());
        dto.setNombresCompletos(persona.getNombresCompletos());
        return dto;
    }
    
    public Trabajador toEntity(CreateTrabajadorDTO dto) {
        Trabajador trabajador = new Trabajador();
        trabajador.setLongitud(dto.getLongitud());
        trabajador.setLatitud(dto.getLatitud());

        trabajador.setCorreo(dto.getCorreo());
        trabajador.setFechaNacimiento(dto.getFechaNacimiento());
        trabajador.setHabilidades(dto.getHabilidades());
        trabajador.setDni(dto.getDni());
        trabajador.setNombresCompletos(dto.getNombresCompletos());
        return trabajador;
    }
    
    public void updateEntityFromDTO(UpdateTrabajadorDTO dto, Trabajador trabajador) {

        if (dto.getCorreo() != null) {
            trabajador.setCorreo(dto.getCorreo());
        }
        if (dto.getFechaNacimiento() != null) {
            trabajador.setFechaNacimiento(dto.getFechaNacimiento());
        }
        if (dto.getDni() != null) {
            trabajador.setDni(dto.getDni());
        }
        if (dto.getNombresCompletos() != null) {
            trabajador.setNombresCompletos(dto.getNombresCompletos());
        }
    }
}

