package com.purrComplexity.TrabajoYa.Contrato;

import com.purrComplexity.TrabajoYa.CalificacionEmpresa.CalificacionEmpresa;
import com.purrComplexity.TrabajoYa.CalificacionPersona.CalificacionPersona;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.OfertaEmpleo;
import com.purrComplexity.TrabajoYa.Persona.Persona;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fechaCreacion;
    //Relaciones
    @ManyToOne
    @JoinColumn(name = "personaContratada_id")
    private Persona personaContratada;

    @ManyToOne
    @JoinColumn(name = "ofertaEmpleo_id")
    private OfertaEmpleo ofertaEmpleo;

    @OneToOne
    @JoinColumn(name="calificacionPersona_id")
    private CalificacionPersona calificacionPersona;

    @OneToOne
    @JoinColumn(name="calificacionEmpresa_id")
    private CalificacionEmpresa calificacionEmpresa;

}
