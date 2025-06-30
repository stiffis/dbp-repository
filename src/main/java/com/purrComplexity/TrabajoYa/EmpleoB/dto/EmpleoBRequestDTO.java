package com.purrComplexity.TrabajoYa.EmpleoB.dto;

import com.purrComplexity.TrabajoYa.OfertaEmpleo.dto.OfertaEmpleoRequestDTO;
import lombok.Data;

@Data
public class EmpleoBRequestDTO extends OfertaEmpleoRequestDTO {

    private String descripcion;
}
