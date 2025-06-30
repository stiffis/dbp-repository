package com.purrComplexity.TrabajoYa.EmpleoA.dto;

import com.purrComplexity.TrabajoYa.OfertaEmpleo.dto.OfertaEmpleoRequestDTO;
import lombok.Data;

@Data
public class EmpleoARequestDTO extends OfertaEmpleoRequestDTO {
    private String puesto;
    private String funcionesPuesto;
    private String tipoEmpleo;

}
