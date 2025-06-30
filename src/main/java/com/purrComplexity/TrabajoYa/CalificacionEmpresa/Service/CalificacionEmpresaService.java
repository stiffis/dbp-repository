package com.purrComplexity.TrabajoYa.CalificacionEmpresa.Service;

import com.purrComplexity.TrabajoYa.CalificacionEmpresa.CalificacionEmpresa;
import com.purrComplexity.TrabajoYa.CalificacionEmpresa.CalificacionEmpresaRepository;
import com.purrComplexity.TrabajoYa.CalificacionEmpresa.dto.CalificacionEmpresaRequestDTO;
import com.purrComplexity.TrabajoYa.CalificacionEmpresa.dto.CalificacionEmpresaResponseDTO;
import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.Contrato.ContratoRepository;
import com.purrComplexity.TrabajoYa.Empleador.Empleador;
import com.purrComplexity.TrabajoYa.Empleador.Repository.EmpleadorRepository;
import com.purrComplexity.TrabajoYa.Persona.Exceptions.PersonaNotFound;
import com.purrComplexity.TrabajoYa.Persona.Persona;
import com.purrComplexity.TrabajoYa.Persona.PersonaRepository;
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
                ()->new PersonaNotFound("El empleador con ese id no exsite")
        );

        Contrato contrato=contratoRepository.findById(idContrato).orElseThrow(
                ()->new RuntimeException("El contrato con ese id no existe")
        );

        if (contrato.getOfertaEmpleo().getEmpleador()!=empleador){
            throw new RuntimeException("Este contrato no pertenece al empleador con este id");
        }

        CalificacionEmpresa calificacionEmpresa=modelMapper.map(calificacionEmpresaRequestDTO,CalificacionEmpresa.class);

        calificacionEmpresa=calificacionEmpresaRepository.save(calificacionEmpresa);

        CalificacionEmpresaResponseDTO calificacionEmpresaResponseDTO=modelMapper.map(calificacionEmpresa,CalificacionEmpresaResponseDTO.class);

        calificacionEmpresaResponseDTO.setContratoId(contrato.getId());

        return calificacionEmpresaResponseDTO;

    }

}
