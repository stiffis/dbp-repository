package com.purrComplexity.TrabajoYa.CalificacionEmpresa.Service;

import com.purrComplexity.TrabajoYa.CalificacionEmpresa.CalificacionEmpresa;
import com.purrComplexity.TrabajoYa.CalificacionEmpresa.CalificacionEmpresaRepository;
import com.purrComplexity.TrabajoYa.CalificacionEmpresa.dto.CalificacionEmpresaRequestDTO;
import com.purrComplexity.TrabajoYa.CalificacionEmpresa.dto.CalificacionEmpresaResponseDTO;
import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.Contrato.ContratoRepository;
import com.purrComplexity.TrabajoYa.Empleador.Empleador;
import com.purrComplexity.TrabajoYa.Empleador.Repository.EmpleadorRepository;
import com.purrComplexity.TrabajoYa.exception.TrabajadorNotFound;
import com.purrComplexity.TrabajoYa.exception.ContratoNotFound;
import com.purrComplexity.TrabajoYa.exception.ContratoNotInEmpleador;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalificacionEmpresaService {
    private final CalificacionEmpresaRepository calificacionEmpresaRepository;
    private final ModelMapper modelMapper;
    private final EmpleadorRepository empleadorRepository;
    private final ContratoRepository contratoRepository;

    public CalificacionEmpresaResponseDTO crearCalificacion(Long idContrato, String idEmpresa,
                                                            CalificacionEmpresaRequestDTO calificacionEmpresaRequestDTO){
        Empleador empleador=empleadorRepository.findById(idEmpresa).orElseThrow(
                TrabajadorNotFound::new
        );

        Contrato contrato=contratoRepository.findById(idContrato).orElseThrow(
                ContratoNotFound::new
        );

        if (!contrato.getOfertaEmpleo().getEmpleador().equals(empleador)) {
            throw new ContratoNotInEmpleador();
        }

        CalificacionEmpresa calificacionEmpresa=modelMapper.map(calificacionEmpresaRequestDTO,CalificacionEmpresa.class);

        calificacionEmpresa=calificacionEmpresaRepository.save(calificacionEmpresa);

        CalificacionEmpresaResponseDTO calificacionEmpresaResponseDTO=modelMapper.map(calificacionEmpresa,CalificacionEmpresaResponseDTO.class);

        calificacionEmpresaResponseDTO.setContratoId(contrato.getId());

        return calificacionEmpresaResponseDTO;

    }

}
