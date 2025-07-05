package com.purrComplexity.TrabajoYa.Contrato.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor // Expected 3 arguments but found 0
@Data
public class ContratoDTO {
    private Long id;
    private Date fechaCreacion;
    private Long personaContratadaId;
    private Double promedioCalificaciones;
}

