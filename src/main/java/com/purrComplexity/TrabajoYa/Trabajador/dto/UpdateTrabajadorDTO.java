package com.purrComplexity.TrabajoYa.Trabajador.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import java.util.Date;

@Data
public class UpdateTrabajadorDTO {
    private Double latitud;
    private Double longitud;
    
    @Email(message = "El formato del correo no es válido")
    private String correo;
    
    private Date fechaNacimiento;
    private String habilidades;
    private String dni;
    private String nombresCompletos;
}

