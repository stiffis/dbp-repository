package com.purrComplexity.TrabajoYa.AplicaA;


import com.purrComplexity.TrabajoYa.EmpleoA.EmpleoA;
import com.purrComplexity.TrabajoYa.Persona.Persona;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class AplicaA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // valor por default  es true
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean estadoAplicacion = false;


}
