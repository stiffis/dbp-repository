package com.purrComplexity.TrabajoYa.Empleo.Repository;

import com.purrComplexity.TrabajoYa.Empleo.Empleo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleoARepository extends JpaRepository<Empleo, Long> {
}
