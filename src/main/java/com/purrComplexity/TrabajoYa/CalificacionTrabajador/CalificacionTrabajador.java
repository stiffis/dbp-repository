package com.purrComplexity.TrabajoYa.CalificacionTrabajador;

import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CalificacionTrabajador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double puntuacionTrabajador;

    @OneToOne(mappedBy = "calificacionTrabajador")
    private Contrato contrato;
}
