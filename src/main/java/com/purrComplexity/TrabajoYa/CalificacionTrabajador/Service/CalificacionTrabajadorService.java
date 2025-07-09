package com.purrComplexity.TrabajoYa.CalificacionTrabajador.Service;

import com.purrComplexity.TrabajoYa.CalificacionEmpresa.CalificacionEmpresa;
import com.purrComplexity.TrabajoYa.CalificacionEmpresa.CalificacionEmpresaRepository;
import com.purrComplexity.TrabajoYa.CalificacionTrabajador.CalificacionTrabajador;
import com.purrComplexity.TrabajoYa.CalificacionTrabajador.CalificacionTrabajadorRepository;
import com.purrComplexity.TrabajoYa.CalificacionTrabajador.dto.CalificacionTrabajadorRequestDTO;
import com.purrComplexity.TrabajoYa.CalificacionTrabajador.dto.CalificacionTrabajadorResponseDTO;
import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.Contrato.ContratoRepository;
import com.purrComplexity.TrabajoYa.User.Repository.UserAccountRepository;
import com.purrComplexity.TrabajoYa.User.UserAccount;
import com.purrComplexity.TrabajoYa.exception.*;
import com.purrComplexity.TrabajoYa.Trabajador.Trabajador;
import com.purrComplexity.TrabajoYa.Trabajador.TrabajadorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalificacionTrabajadorService {

    private final ModelMapper modelMapper;
    private final CalificacionTrabajadorRepository calificacionTrabajadorRepository;
    private final CalificacionEmpresaRepository calificacionEmpresaRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final ContratoRepository contratoRepository;
    private final UserAccountRepository userAccountRepository;

    public CalificacionTrabajadorResponseDTO crearCalificacionTrabajador(Long idContrato,Long idUsuario,
                                                               CalificacionTrabajadorRequestDTO calificacionTrabajadorRequestDTO){
        UserAccount userAccount=userAccountRepository.findById(idUsuario).orElseThrow(()->new UsernameNotFoundException("No existe el usuario"));

        if (!userAccount.getIsTrabajador()){
            throw new UsuarioNoEsTrabajadorException();
        }

        Trabajador trabajador=userAccount.getTrabajador();

        Contrato contrato=contratoRepository.findById(idContrato).orElseThrow(
                ContratoNotFound::new
        );

        if (!contrato.getTrabajadorContratado().equals(trabajador)){
            throw new TrabajadorNoContratadoException();
        }

        CalificacionTrabajador calificacion = new CalificacionTrabajador();
        calificacion.setPuntuacionTrabajador(calificacionTrabajadorRequestDTO.getPuntuacionTrabajador());

        CalificacionTrabajador savedCalificacion = calificacionTrabajadorRepository.save(calificacion);

        contrato.setCalificacionTrabajador(savedCalificacion);

        CalificacionEmpresa calificacionEmpresa = contrato.getCalificacionEmpresa();
        Double pt = savedCalificacion.getPuntuacionTrabajador();

        if (calificacionEmpresa == null) {
            contrato.setCalificacionPromedio(pt);
        } else {
            contrato.setCalificacionPromedio((pt + calificacionEmpresa.getPuntuacionEmpresa()) / 2.0);
        }

        Contrato savedContrato=contratoRepository.save(contrato);

        CalificacionTrabajadorResponseDTO calificacionTrabajadorResponseDTO=new CalificacionTrabajadorResponseDTO();

        calificacionTrabajadorResponseDTO.setPuntuacionContrato(savedContrato.getCalificacionPromedio());

        calificacionTrabajadorResponseDTO.setContratoId(contrato.getId());

        return calificacionTrabajadorResponseDTO;

    }

}
