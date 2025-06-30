package com.purrComplexity.TrabajoYa.AplicaA.Repository;

import com.purrComplexity.TrabajoYa.AplicaA.AplicaA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AplicaARepository extends JpaRepository<AplicaA, Long> {
    // Por ejemplo, para encontrar postulaciones por empleo:
    // List<AplicaA> findByEmpleoId(Long empleoId);
}
