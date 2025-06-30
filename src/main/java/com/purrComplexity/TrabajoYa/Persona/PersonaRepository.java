package com.purrComplexity.TrabajoYa.Persona;

import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    boolean existsByCorreo(String correo);

}

