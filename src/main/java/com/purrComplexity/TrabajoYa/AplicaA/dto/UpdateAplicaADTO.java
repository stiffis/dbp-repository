package com.purrComplexity.TrabajoYa.AplicaA.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateAplicaADTO {
    @NotNull(message = "El ID del empleo es obligatorio")
    private Long empleoId;

    private Boolean estadoAplicacion = false; // Opcional, con valor por defecto
}