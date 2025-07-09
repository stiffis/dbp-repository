package com.purrComplexity.TrabajoYa.CalificacionTrabajador.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
public class CalificacionTrabajadorRequestDTO {

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double puntuacionTrabajador;
}
