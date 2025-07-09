package com.purrComplexity.TrabajoYa.Contrato;

import com.purrComplexity.TrabajoYa.Contrato.dto.ContratoDTO;
import com.purrComplexity.TrabajoYa.Contrato.dto.CreateContratoDTO;
import com.purrComplexity.TrabajoYa.Contrato.dto.UpdateContratoDTO;
import java.util.List;

public interface ContratoService {

    ContratoDTO createContrato(Long idUsuario ,Long idOfertaEmpleo,Long idTrabajador);
    ContratoDTO getContratoById(Long id);
    List<ContratoDTO> getAllContratos(Long userId);
    ContratoDTO updateContrato(Long id, UpdateContratoDTO contratoDTO);
    void deleteContrato(Long id);
    List<ContratoDTO> getContratosByTrabajadorId(Long personaId);
}

