package com.purrComplexity.TrabajoYa.EmpleoA;

import com.purrComplexity.TrabajoYa.AplicaA.AplicaA;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.OfertaEmpleo;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class EmpleoA extends OfertaEmpleo {
    // private String FechaInicio;
    private String Puesto;
    private String FuncionesPuesto;
    private String TipoEmpleo;



}
