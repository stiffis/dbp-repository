package com.purrComplexity.TrabajoYa.Contrato.mapper;

import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.Contrato.dto.ContratoDTO;
import com.purrComplexity.TrabajoYa.Contrato.dto.CreateContratoDTO;
import com.purrComplexity.TrabajoYa.Contrato.dto.UpdateContratoDTO;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.OfertaEmpleo;
import com.purrComplexity.TrabajoYa.Persona.Persona;
import org.springframework.stereotype.Component;

@Component
public class ContratoMapper {
    
    public ContratoDTO toDTO(Contrato contrato) {
        ContratoDTO dto = new ContratoDTO();
        dto.setId(contrato.getId());
        dto.setFechaCreacion(contrato.getFechaCreacion());
        if (contrato.getPersonaContratada() != null) {
            dto.setPersonaContratadaId(contrato.getPersonaContratada().getId());
        }
        return dto;
    }
    
    public Contrato toEntity(CreateContratoDTO dto, Persona personaContratada, OfertaEmpleo ofertaEmpleo) {
        Contrato contrato = new Contrato(); // estoy ya crea todos los campos de un contrato, pero estan vacios inicialmente
        contrato.setFechaCreacion(dto.fechaCreacion());
        contrato.setPersonaContratada(personaContratada);
        contrato.setOfertaEmpleo(ofertaEmpleo);
        return contrato;
    }
    
    public void updateEntityFromDTO(UpdateContratoDTO dto, Contrato contrato, Persona personaContratada) {
        if (dto.getFechaCreacion() != null) {
            contrato.setFechaCreacion(dto.getFechaCreacion());
        }
        if (personaContratada != null) {
            contrato.setPersonaContratada(personaContratada);
        }
    }
}

