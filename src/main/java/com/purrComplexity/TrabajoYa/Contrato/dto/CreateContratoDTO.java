package com.purrComplexity.TrabajoYa.Contrato.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/*
@Data
public class CreateContratoDTO {
    @NotNull(message = "La fecha de creación es requerida")
    private Date fechaCreacion;

    @NotNull(message = "El ID de la persona contratada es requerido")
    private Long personaContratadaId;
}
*/


public record CreateContratoDTO(
        @NotNull(message = "La persona contratada es obligatoria")
        Long personaContratadaId,

        @NotNull(message = "La oferta de empleo es obligatoria")
        Long ofertaEmpleoId,

        @PastOrPresent(message = "La fecha de creación no puede ser futura")
        Date fechaCreacion,

        @Valid
        List<@Min(1) @Max(5) Integer> calificaciones
) {}
