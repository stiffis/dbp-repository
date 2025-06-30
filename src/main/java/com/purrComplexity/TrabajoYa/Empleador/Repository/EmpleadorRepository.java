package com.purrComplexity.TrabajoYa.Empleador.Repository;

import com.purrComplexity.TrabajoYa.Empleador.Empleador;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadorRepository extends JpaRepository<Empleador,String> {
    boolean existsByCorreo(@Email String correo);
}
