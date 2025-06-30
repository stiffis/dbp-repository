package com.purrComplexity.TrabajoYa.AplicaB.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DeleteAplicaBDTO {
    @NotNull(message = "El ID es obligatorio")
    @Positive(message = "El ID debe ser positivo")
    private Long id;
}
