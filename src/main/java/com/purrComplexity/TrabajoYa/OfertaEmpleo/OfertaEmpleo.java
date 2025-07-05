package com.purrComplexity.TrabajoYa.OfertaEmpleo;

import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.Empleador.Empleador;
import com.purrComplexity.TrabajoYa.Enum.WeekDays;
import com.purrComplexity.TrabajoYa.Enum.Habilidad;
import com.purrComplexity.TrabajoYa.Enum.SistemaRemuneracion;
import com.purrComplexity.TrabajoYa.Enum.modalidad;
import com.purrComplexity.TrabajoYa.Trabajador.Trabajador;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OfertaEmpleo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOfertaEmpleo;
    private String periodoPago;
    private Long montoPorPeriodo;

    @Enumerated(EnumType.STRING)
    private modalidad modalidadEmpleo; // VIRTUAL, PRESENCIAL, HIBRIDO

    //private String lugar;
    private Double longitud;

    private Double latitud;

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


    @OneToMany(mappedBy = "ofertaEmpleo", cascade = CascadeType.ALL)
    private List<Contrato> contratos =new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "empleador_id")
    private Empleador empleador;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean estadoAplicacion = false;

    @ManyToMany(mappedBy = "postulaste")
    private List<Trabajador> postulantes =new ArrayList<>();

    @ManyToMany(mappedBy = "contratado")
    private List<Trabajador> contratados =new ArrayList<>();

};
