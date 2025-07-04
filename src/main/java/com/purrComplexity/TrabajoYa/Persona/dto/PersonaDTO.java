package com.purrComplexity.TrabajoYa.Persona.dto;

import lombok.Data;
import java.util.Date;

@Data
public class PersonaDTO {
    private Double latitud;
    private Double longitud;

    private String correo;
    private Date fechaNacimiento;
    private String habilidades;
    private String dni;
    private String nombresCompletos;
}

