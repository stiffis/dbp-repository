package com.purrComplexity.TrabajoYa.Contrato;

import com.purrComplexity.TrabajoYa.CalificacionEmpresa.CalificacionEmpresa;
import com.purrComplexity.TrabajoYa.CalificacionTrabajador.CalificacionTrabajador;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.OfertaEmpleo;
import com.purrComplexity.TrabajoYa.Trabajador.Trabajador;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fechaCreacion;
    //Relaciones
    @ManyToOne
    @JoinColumn(name = "trabajadorContratado_id")
    private Trabajador trabajadorContratado;

    @ManyToOne
    @JoinColumn(name = "ofertaEmpleo_id")
    private OfertaEmpleo ofertaEmpleo;

    @OneToOne
    @JoinColumn(name="calificacionTrabajador_id")
    private CalificacionTrabajador calificacionTrabajador;

    @OneToOne
    @JoinColumn(name="calificacionEmpresa_id")
    private CalificacionEmpresa calificacionEmpresa;

    @Valid
    private Double calificacionPromedio;


}
