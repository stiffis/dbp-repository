package com.purrComplexity.TrabajoYa.Contrato.mapper;

import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.Contrato.dto.ContratoDTO;
import com.purrComplexity.TrabajoYa.Contrato.dto.CreateContratoDTO;
import com.purrComplexity.TrabajoYa.Contrato.dto.UpdateContratoDTO;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.OfertaEmpleo;
import com.purrComplexity.TrabajoYa.Trabajador.Trabajador;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContratoMapper {
    
    public ContratoDTO toDTO(Contrato contrato) {
        ContratoDTO dto = new ContratoDTO();
        dto.setId(contrato.getId());
        dto.setFechaCreacion(contrato.getFechaCreacion());
        if (contrato.getTrabajadorContratado() != null) {
            dto.setPersonaContratadaId(contrato.getTrabajadorContratado().getId());
        }

        // Cálculo del promedio de calificaciones
        Double calificaciones = contrato.getCalificacionPromedio();

        dto.setPromedioCalificaciones(calificaciones);

        return dto;
    }

    public void updateEntityFromDTO(UpdateContratoDTO dto, Contrato contrato, Trabajador trabajador) {
        if (dto.getFechaCreacion() != null) {
            contrato.setFechaCreacion(dto.getFechaCreacion());
        }
        if (trabajador != null) {
            contrato.setTrabajadorContratado(trabajador);
        }
    }
}

