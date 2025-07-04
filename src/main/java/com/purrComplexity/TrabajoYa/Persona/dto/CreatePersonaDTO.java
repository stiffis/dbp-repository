package com.purrComplexity.TrabajoYa.Persona.dto;

import com.purrComplexity.TrabajoYa.Enum.Habilidad;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreatePersonaDTO {

    private Double longitud;
    private Double latitud;

    
    @NotBlank(message = "El correo es requerido")
    @Email(message = "El formato del correo no es válido")
    private String correo;
    
    @NotNull(message = "La fecha de nacimiento es requerida")
    private Date fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private Habilidad habilidades;
    
    @NotBlank(message = "El DNI es requerido")
    private String dni;
    
    @NotBlank(message = "Los nombres completos son requeridos")
    private String nombresCompletos;
}

