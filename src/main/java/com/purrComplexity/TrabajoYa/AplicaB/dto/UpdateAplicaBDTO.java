package com.purrComplexity.TrabajoYa.AplicaB.dto;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.Setter;

@Setter
public class UpdateAplicaBDTO {

    @NotNull(message = "El estado de la aplicion es requerido. True o False")
    private boolean estadoAplicacion;

}
