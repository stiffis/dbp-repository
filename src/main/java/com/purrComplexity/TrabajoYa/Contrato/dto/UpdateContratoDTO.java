package com.purrComplexity.TrabajoYa.Contrato.dto;

import lombok.Data;
import java.util.Date;

@Data
public class UpdateContratoDTO {
    private Date fechaCreacion;
    private Long personaContratadaId;
}

