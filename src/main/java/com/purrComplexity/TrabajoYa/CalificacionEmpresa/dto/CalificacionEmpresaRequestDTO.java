package com.purrComplexity.TrabajoYa.CalificacionEmpresa.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
public class CalificacionEmpresaRequestDTO {

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double puntuacionEmpresa;
}
