package com.purrComplexity.TrabajoYa.CalificacionTrabajador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionTrabajadorRepository extends JpaRepository<CalificacionTrabajador,Long> {
}
