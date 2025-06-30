package com.purrComplexity.TrabajoYa.Empleador;

import com.purrComplexity.TrabajoYa.OfertaEmpleo.OfertaEmpleo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Empleador {
    @Id
    @Column(unique = true, nullable = false)
    private String ruc;
    private String razonSocial;
    private Long telefonoPrincipal;
    @Email
    private String correo;

    @OneToMany(mappedBy = "empleador",cascade = CascadeType.ALL)
    private List<OfertaEmpleo> ofertas;
}
