package com.purrComplexity.TrabajoYa.Trabajador.dto;

import lombok.Data;
import java.util.Date;

@Data
public class TrabajadorDTO {
    private Double id;
    private Double latitud;
    private Double longitud;

    private String correo;
    private Date fechaNacimiento;
    private String habilidades;
    private String dni;
    private String nombresCompletos;
}

