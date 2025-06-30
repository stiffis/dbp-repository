package com.purrComplexity.TrabajoYa.CalificacionEmpresa;

import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CalificacionEmpresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long puntuacionEmpresa;

    @OneToOne(mappedBy = "calificacionEmpresa")
    private Contrato contrato;
}
