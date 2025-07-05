package com.purrComplexity.TrabajoYa.CalificacionTrabajador.Service;

import com.purrComplexity.TrabajoYa.CalificacionTrabajador.CalificacionTrabajador;
import com.purrComplexity.TrabajoYa.CalificacionTrabajador.CalificacionTrabajadorRepository;
import com.purrComplexity.TrabajoYa.CalificacionTrabajador.dto.CalificacionTrabajadorRequestDTO;
import com.purrComplexity.TrabajoYa.CalificacionTrabajador.dto.CalificacionTrabajadorResponseDTO;
import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.Contrato.ContratoRepository;
import com.purrComplexity.TrabajoYa.exception.TrabajadorNotFound;
import com.purrComplexity.TrabajoYa.Trabajador.Trabajador;
import com.purrComplexity.TrabajoYa.Trabajador.TrabajadorRepository;
import com.purrComplexity.TrabajoYa.exception.ContratoNotFound;
import com.purrComplexity.TrabajoYa.exception.ContratoNotInEmpleador;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalificacionTrabajadorService {

    private final ModelMapper modelMapper;
    private final CalificacionTrabajadorRepository calificacionTrabajadorRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final ContratoRepository contratoRepository;

    public CalificacionTrabajadorResponseDTO crearCalificacion(Long idContrato, Long idTrabajador,
                                                               CalificacionTrabajadorRequestDTO calificacionTrabajadorRequestDTO){
        Trabajador trabajador=trabajadorRepository.findById(idTrabajador).orElseThrow(
                TrabajadorNotFound::new
        );

        Contrato contrato=contratoRepository.findById(idContrato).orElseThrow(
                ContratoNotFound::new
        );

        if (contrato.getTrabajadorContratado()!=trabajador){
            throw new ContratoNotInEmpleador();
        }

        CalificacionTrabajador calificacionTrabajador =modelMapper.map(calificacionTrabajadorRequestDTO, CalificacionTrabajador.class);

        calificacionTrabajador = calificacionTrabajadorRepository.save(calificacionTrabajador);

        CalificacionTrabajadorResponseDTO calificacionTrabajadorResponseDTO=modelMapper.map(calificacionTrabajador,CalificacionTrabajadorResponseDTO.class);

        calificacionTrabajadorResponseDTO.setContratoId(contrato.getId());

        return calificacionTrabajadorResponseDTO;

    }

}
