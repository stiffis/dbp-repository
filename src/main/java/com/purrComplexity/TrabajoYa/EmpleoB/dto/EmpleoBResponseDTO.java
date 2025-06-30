package com.purrComplexity.TrabajoYa.EmpleoB.dto;

import com.purrComplexity.TrabajoYa.OfertaEmpleo.dto.OfertaEmpleoResponseDTO;
import lombok.Data;

@Data
public class EmpleoBResponseDTO extends OfertaEmpleoResponseDTO {

    private String descripcion;
    private String razonSocial;
}
