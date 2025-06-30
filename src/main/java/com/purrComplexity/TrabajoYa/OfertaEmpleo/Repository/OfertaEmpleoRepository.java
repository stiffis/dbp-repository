package com.purrComplexity.TrabajoYa.OfertaEmpleo.Repository;

import com.purrComplexity.TrabajoYa.OfertaEmpleo.OfertaEmpleo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertaEmpleoRepository extends JpaRepository<OfertaEmpleo, Long> {
}
