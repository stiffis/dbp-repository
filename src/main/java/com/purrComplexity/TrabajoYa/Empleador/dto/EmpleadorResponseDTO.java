package com.purrComplexity.TrabajoYa.Empleador.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class EmpleadorResponseDTO {
    private String ruc;
    private String RazonSocial;
    private Long TelefonoPrincipal;
    @Email
    private String Correo;
}
