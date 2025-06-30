package com.purrComplexity.TrabajoYa.EmpleoA.dto;

import com.purrComplexity.TrabajoYa.OfertaEmpleo.dto.OfertaEmpleoResponseDTO;
import lombok.Data;

@Data
public class EmpleoAResponseDTO extends OfertaEmpleoResponseDTO {
    private String puesto;
    private String funcionesPuesto;
    private String tipoEmpleo;
    private String razonSocial;


}
