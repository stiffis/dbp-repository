package com.purrComplexity.TrabajoYa.OfertaEmpleo.dto;

import com.purrComplexity.TrabajoYa.Enum.SistemaRemuneracion;
import com.purrComplexity.TrabajoYa.Enum.WeekDays;
import com.purrComplexity.TrabajoYa.Enum.modalidad;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class OfertaEmpleoRequestDTO {

    private String periodoPago;
    private Long montoPorPeriodo;

    @Enumerated(EnumType.STRING)
    private modalidad modalidadEmpleo;

    private String lugar;

    private String habilidades;

    private Long numeroPostulaciones;

    private LocalDateTime fechaLimite;

    @URL(message = "La URL  de la imagen no es válida")
    private String imagen;

    @Enumerated(EnumType.STRING)
    private SistemaRemuneracion sistemaRemuneracion;

    private String hoursPerDay;

    private WeekDays weekDays;
}
