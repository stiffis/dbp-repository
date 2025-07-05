package com.purrComplexity.TrabajoYa.AplicaA;


import jakarta.persistence.*;

@Entity
public class AplicaA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // valor por default  es true
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean estadoAplicacion = false;


}
