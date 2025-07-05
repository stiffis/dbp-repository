package com.purrComplexity.TrabajoYa.Empleo.dto;

import com.purrComplexity.TrabajoYa.OfertaEmpleo.dto.OfertaEmpleoRequestDTO;
import lombok.Data;

@Data
public class EmpleoRequestDTO extends OfertaEmpleoRequestDTO {
    private String puesto;
    private String funcionesPuesto;
    private String tipoEmpleo;

}
