package com.purrComplexity.TrabajoYa.Empleador.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.List;

@Data
public class EmpleadorRequestDTO {

    private String ruc;
    private String razonSocial;
    private Long telefonoPrincipal;
    @Email
    private String correo;

}
