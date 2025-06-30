package com.purrComplexity.TrabajoYa.AplicaB;

import com.purrComplexity.TrabajoYa.EmpleoB.EmpleoB;
import jakarta.persistence.*;

import java.util.List;


@Entity
public class AplicaB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // valor por default  es False
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean estadoAplicacion = false;
}

