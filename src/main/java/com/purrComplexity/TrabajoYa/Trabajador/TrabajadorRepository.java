package com.purrComplexity.TrabajoYa.Trabajador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {

    boolean existsByCorreo(String correo);

}

