package com.purrComplexity.TrabajoYa.Trabajador;

import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.Trabajador.dto.CreateTrabajadorDTO;
import com.purrComplexity.TrabajoYa.Trabajador.dto.TrabajadorDTO;

import java.util.List;

public interface TrabajadorService {
    TrabajadorDTO createPersona(Long idUsuario, CreateTrabajadorDTO persona);
    TrabajadorDTO getPersonaById(Long id);
    List<Trabajador> getAllPersonas();
    Trabajador updatePersona(Long id, Trabajador trabajador);
    void deletePersona(Long id);
    List<Contrato> getContratosByPersona(Long personaId);

}

