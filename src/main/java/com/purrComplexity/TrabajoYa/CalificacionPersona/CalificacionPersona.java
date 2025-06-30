package com.purrComplexity.TrabajoYa.CalificacionPersona;

import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CalificacionPersona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long puntuacionTrabajador;

    @OneToOne(mappedBy = "calificacionPersona")
    private Contrato contrato;
}
