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
public class    OfertaEmpleoResponseDTO {

    private Long idOfertaEmpleo;
    private String periodoPago;
    private Long montoPorPeriodo;

    @Enumerated(EnumType.STRING)
    private modalidad modalidadEmpleo; // VIRTUAL, PRESENCIAL, HIBRIDO

    private Double latitud;

    private Double longitud;

    private String habilidades;

    private Long numeroPostulaciones;

    @URL(message = "La URL  de la imagen no es válida")
    private String imagen;

    private LocalDateTime fechaLimite;

    @Enumerated(EnumType.STRING)
    private SistemaRemuneracion sistemaRemuneracion;

    private String hoursPerDay;

    private WeekDays weekDays;

}
