package com.purrComplexity.TrabajoYa.EmpleoB;

import com.purrComplexity.TrabajoYa.AplicaB.AplicaB;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.OfertaEmpleo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class EmpleoB extends OfertaEmpleo {
    // private String FechaInicio;
    private String descripcion;

}
