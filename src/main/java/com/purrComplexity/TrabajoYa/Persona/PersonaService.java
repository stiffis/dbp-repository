package com.purrComplexity.TrabajoYa.Persona;

import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.Persona.dto.CreatePersonaDTO;
import com.purrComplexity.TrabajoYa.Persona.dto.PersonaDTO;

import java.util.List;

public interface PersonaService {
    PersonaDTO createPersona(CreatePersonaDTO persona);
    PersonaDTO getPersonaById(Long id);
    List<Persona> getAllPersonas();
    Persona updatePersona(Long id, Persona persona);
    void deletePersona(Long id);
    List<Contrato> getContratosByPersona(Long personaId);

}

