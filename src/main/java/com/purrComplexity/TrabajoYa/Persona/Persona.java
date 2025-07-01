package com.purrComplexity.TrabajoYa.Persona;

import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.Enum.Habilidad;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.OfertaEmpleo;
import com.purrComplexity.TrabajoYa.User.UserAccount;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import java.util.Date;

@Data
@Entity
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ubicacion;

    @Column(unique = true)
    @Email
    private String correo;
    private Date fechaNacimiento;
// ubicacion, correo, fecha de nacimient, habilidad, dni, nombresCompletos, calificaciones, contratos,
    @Enumerated(EnumType.STRING)
    private Habilidad habilidades;

    private String dni;
    private String nombresCompletos;

    //Relaciones
    @OneToMany(mappedBy = "personaContratada", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contrato> contratos;


    @ManyToMany
    @JoinTable(
            name = "persona_postulacion", // nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "persona_id"),
            inverseJoinColumns = @JoinColumn(name = "oferta_id")
    )
    private List<OfertaEmpleo> postulaste =new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "persona_aplicacion", // otra tabla intermedia distinta
            joinColumns = @JoinColumn(name = "persona_id"),
            inverseJoinColumns = @JoinColumn(name = "oferta_id")
    )
    private List<OfertaEmpleo> aplicaste=new ArrayList<>();

    @OneToOne(mappedBy = "trabajador",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserAccount usuario;

}
