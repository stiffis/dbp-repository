package com.purrComplexity.TrabajoYa.Trabajador;

import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.Trabajador.dto.CreateTrabajadorDTO;
import com.purrComplexity.TrabajoYa.Trabajador.dto.TrabajadorDTO;
import com.purrComplexity.TrabajoYa.Trabajador.dto.UpdateTrabajadorDTO;

import java.util.List;

public interface TrabajadorService {
    TrabajadorDTO createPersona(Long idUsuario, CreateTrabajadorDTO persona);
    TrabajadorDTO getPersonaById(Long id);
    List<Trabajador> getAllPersonas();
    TrabajadorDTO updateTrabajador(Long idUsuario,Long id, UpdateTrabajadorDTO trabajador);
    void deleteTrabajador(Long userId,Long id);
    List<Contrato> getContratosByPersona(Long personaId);

}

