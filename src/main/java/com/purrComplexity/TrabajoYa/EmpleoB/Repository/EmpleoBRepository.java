package com.purrComplexity.TrabajoYa.EmpleoB.Repository;

import com.purrComplexity.TrabajoYa.EmpleoB.EmpleoB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleoBRepository extends JpaRepository<EmpleoB,Long> {
}
