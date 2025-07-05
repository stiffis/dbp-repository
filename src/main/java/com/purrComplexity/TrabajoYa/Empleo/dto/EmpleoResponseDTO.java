package com.purrComplexity.TrabajoYa.Empleo.dto;

import com.purrComplexity.TrabajoYa.OfertaEmpleo.dto.OfertaEmpleoResponseDTO;
import lombok.Data;

@Data
public class EmpleoResponseDTO extends OfertaEmpleoResponseDTO {
    private String puesto;
    private String funcionesPuesto;
    private String tipoEmpleo;
    private String razonSocial;


}
