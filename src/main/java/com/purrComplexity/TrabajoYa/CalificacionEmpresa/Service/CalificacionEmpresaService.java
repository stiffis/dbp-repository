package com.purrComplexity.TrabajoYa.CalificacionEmpresa.Service;

import com.purrComplexity.TrabajoYa.CalificacionEmpresa.CalificacionEmpresa;
import com.purrComplexity.TrabajoYa.CalificacionEmpresa.CalificacionEmpresaRepository;
import com.purrComplexity.TrabajoYa.CalificacionEmpresa.dto.CalificacionEmpresaRequestDTO;
import com.purrComplexity.TrabajoYa.CalificacionEmpresa.dto.CalificacionEmpresaResponseDTO;
import com.purrComplexity.TrabajoYa.CalificacionTrabajador.CalificacionTrabajador;
import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.Contrato.ContratoRepository;
import com.purrComplexity.TrabajoYa.Empleador.Empleador;
import com.purrComplexity.TrabajoYa.Empleador.Repository.EmpleadorRepository;
import com.purrComplexity.TrabajoYa.User.Repository.UserAccountRepository;
import com.purrComplexity.TrabajoYa.User.UserAccount;
import com.purrComplexity.TrabajoYa.exception.TrabajadorNotFound;
import com.purrComplexity.TrabajoYa.exception.ContratoNotFound;
import com.purrComplexity.TrabajoYa.exception.ContratoNotInEmpleador;
import com.purrComplexity.TrabajoYa.exception.UsuarioNoEsEmpleadorException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalificacionEmpresaService {
    private final CalificacionEmpresaRepository calificacionEmpresaRepository;
    private final ModelMapper modelMapper;
    private final EmpleadorRepository empleadorRepository;
    private final ContratoRepository contratoRepository;
    private final UserAccountRepository userAccountRepository;

    public CalificacionEmpresaResponseDTO crearCalificacionEmpleador(Long idContrato, Long idUsuaro,
                                                            CalificacionEmpresaRequestDTO calificacionEmpresaRequestDTO){

        UserAccount userAccount=userAccountRepository.findById(idUsuaro).orElseThrow(()->new UsernameNotFoundException("No existe el usuario"));

        if (!userAccount.getIsEmpresario()){
            throw new UsuarioNoEsEmpleadorException();
        }

        Empleador empleador=userAccount.getEmpresario();

        Contrato contrato=contratoRepository.findById(idContrato).orElseThrow(
                ContratoNotFound::new
        );

        if (!contrato.getOfertaEmpleo().getEmpleador().equals(empleador)) {
            throw new ContratoNotInEmpleador();
        }

        CalificacionEmpresa calificacionEmpresa=new CalificacionEmpresa();

        calificacionEmpresa.setPuntuacionEmpresa(calificacionEmpresaRequestDTO.getPuntuacionEmpresa());

        CalificacionEmpresa calificacionEmpresa1=calificacionEmpresaRepository.save(calificacionEmpresa);

        contrato.setCalificacionEmpresa(calificacionEmpresa1);

        CalificacionTrabajador calificacionTrabajador = contrato.getCalificacionTrabajador();
        Double pt = calificacionEmpresa1.getPuntuacionEmpresa();

        if (calificacionTrabajador == null) {
            contrato.setCalificacionPromedio(pt);
        } else {
            contrato.setCalificacionPromedio((pt + calificacionTrabajador.getPuntuacionTrabajador()) / 2.0);
        }

        Contrato savedContrato=contratoRepository.save(contrato);

        CalificacionEmpresaResponseDTO calificacionEmpresaResponseDTO=new CalificacionEmpresaResponseDTO();

        calificacionEmpresaResponseDTO.setPuntuacionContrato(savedContrato.getCalificacionPromedio());


        return calificacionEmpresaResponseDTO;

    }

}
