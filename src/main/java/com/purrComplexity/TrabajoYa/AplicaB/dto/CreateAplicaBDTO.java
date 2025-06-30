package com.purrComplexity.TrabajoYa.AplicaB.dto;

import com.purrComplexity.TrabajoYa.EmpleoB.EmpleoB;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CreateAplicaBDTO {

    // private EmpleoB empleo; // estaba mal porque Se expone toda una entidad dentro del DTO. Y solo hemos requerido su Id.

    // El id es creado por deafult, tonces no es necesario incluirlo en el DTO

    @NotNull(message = "El id del empleo a aplicar es obligatorio")
    @Positive(message = "El id del empleo a aplicar debe ser un número positivo")
    private Long empleoId;

    // private Boolean estadoAplicacion;

}
