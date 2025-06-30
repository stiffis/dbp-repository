package com.purrComplexity.TrabajoYa.OfertaEmpleo.dto;

import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.Enum.Habilidad;
import com.purrComplexity.TrabajoYa.Enum.SistemaRemuneracion;
import com.purrComplexity.TrabajoYa.Enum.WeekDays;
import com.purrComplexity.TrabajoYa.Enum.modalidad;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.OfertaEmpleo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OfertaEmpleoResponseDTO {

    private String periodoPago;
    private Long montoPorPeriodo;

    @Enumerated(EnumType.STRING)
    private modalidad modalidadEmpleo; // VIRTUAL, PRESENCIAL, HIBRIDO

    private String lugar;

    @Enumerated(EnumType.STRING)
    private Habilidad habilidades;

    private Long numeroPostulaciones;

    @URL(message = "La URL  de la imagen no es válida")
    private String imagen;

    private LocalDateTime fechaLimite;

    @Enumerated(EnumType.STRING)
    private SistemaRemuneracion sistemaRemuneracion;

    private String hoursPerDay;

    private WeekDays weekDays;


}
