package com.purrComplexity.TrabajoYa.EmpleoA.Repository;

import com.purrComplexity.TrabajoYa.EmpleoA.EmpleoA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleoARepository extends JpaRepository<EmpleoA, Long> {
}
