package com.purrComplexity.TrabajoYa.Contrato;

import com.purrComplexity.TrabajoYa.Contrato.dto.ContratoDTO;
import com.purrComplexity.TrabajoYa.Contrato.dto.CreateContratoDTO;
import com.purrComplexity.TrabajoYa.Contrato.dto.UpdateContratoDTO;
import java.util.List;

public interface ContratoService {

    ContratoDTO createContrato(CreateContratoDTO createContratoDTO);
    ContratoDTO getContratoById(Long id);
    List<ContratoDTO> getAllContratos();
    ContratoDTO updateContrato(Long id, UpdateContratoDTO contratoDTO);
    void deleteContrato(Long id);
    List<ContratoDTO> getContratosByPersonaId(Long personaId);
}

