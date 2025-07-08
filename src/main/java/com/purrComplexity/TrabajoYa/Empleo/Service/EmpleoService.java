package com.purrComplexity.TrabajoYa.Empleo.Service;

import com.purrComplexity.TrabajoYa.Empleador.Empleador;
import com.purrComplexity.TrabajoYa.Empleador.Repository.EmpleadorRepository;
import com.purrComplexity.TrabajoYa.Empleo.Empleo;
import com.purrComplexity.TrabajoYa.Empleo.Repository.EmpleoARepository;
import com.purrComplexity.TrabajoYa.Empleo.dto.EmpleoRequestDTO;
import com.purrComplexity.TrabajoYa.Empleo.dto.EmpleoResponseDTO;
import com.purrComplexity.TrabajoYa.User.Repository.UserAccountRepository;
import com.purrComplexity.TrabajoYa.User.UserAccount;
import com.purrComplexity.TrabajoYa.exception.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleoService {
    private final EmpleoARepository empleoARepository;
    private final EmpleadorRepository empleadorRepository;
    private final ModelMapper modelMapper;
    private final UserAccountRepository userAccountRepository;

    public void eliminarEmpleo(Long idUsuario,Long id){
        UserAccount userAccount=userAccountRepository.findById(idUsuario).orElseThrow(()->new UsernameNotFoundException("No existe el usuario"));
        if (!userAccount.getIsEmpresario()){
            throw new UsuarioNoEsEmpleadorException();
        }

        Empleo empleo=
                empleoARepository.findById(id).orElseThrow(OfertaEmpleoNotFound::new);


        if (!userAccount.getEmpresario().getRuc().equals(empleo.getEmpleador().getRuc())){
            throw new OfertaEmpleoNoPerteneceAlEmpleadorException();
        }

        empleoARepository.delete(empleo);
    }

    public EmpleoResponseDTO actulizarEmpleo(Long id, EmpleoRequestDTO empleoARequestDTO){
        Empleo empleo=
                empleoARepository.findById(id).orElseThrow(OfertaEmpleoNotFound::new);

        modelMapper.map(empleoARequestDTO, empleo);

        empleo = empleoARepository.save(empleo);

        EmpleoResponseDTO empleoAResponseDTO=modelMapper.map(empleo,EmpleoResponseDTO.class);

        return empleoAResponseDTO;

    }

    public EmpleoResponseDTO actualizarParcialmenteEmpleo(Long id, EmpleoRequestDTO empleoARequestDTO){
        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        Empleo empleo=
                empleoARepository.findById(id).orElseThrow(OfertaEmpleoNotFound::new);

        mapper.map(empleoARequestDTO,empleo);

        empleo=empleoARepository.save(empleo);

        EmpleoResponseDTO empleoAResponseDTO=modelMapper.map(empleo,EmpleoResponseDTO.class);

        return empleoAResponseDTO;
    }

    public EmpleoResponseDTO obtenerEmpleoAPorId(Long id){
        Empleo empleo=
                empleoARepository.findById(id).orElseThrow(OfertaEmpleoNotFound::new);
        EmpleoResponseDTO empleoResponseDTO=modelMapper.map(empleo,EmpleoResponseDTO.class);

        return empleoResponseDTO;
    }

    public List<EmpleoResponseDTO> obtenerTodosEmpleoA(){
        List<Empleo> empleos=empleoARepository.findAll();

        List<EmpleoResponseDTO> empleoAResponseDTOS=new ArrayList<>();

        for (Empleo empleoA:empleos){
            EmpleoResponseDTO dto=modelMapper.map(empleoA,EmpleoResponseDTO.class);
            empleoAResponseDTOS.add(dto);
        }

        return empleoAResponseDTOS;
    }

    public EmpleoResponseDTO crearYAsignarEmpleo(EmpleoRequestDTO empleoRequestDTO, Long idUsuario){

        UserAccount userAccount=userAccountRepository.findById(idUsuario).orElseThrow(()->new UsernameNotFoundException("El usuario no existe"));

        if (!userAccount.getIsEmpresario()){
            throw new UsuarioNoEsTrabajadorException();
        }

        String id=userAccount.getEmpresario().getRuc();

        Empleo empleo =modelMapper.map(empleoRequestDTO,Empleo.class);

        Empleador empleador=
                empleadorRepository.findById(id).orElseThrow(EmpleadorNotFound::new);

        empleo.setEmpleador(empleador);

        empleo=empleoARepository.save(empleo);

        EmpleoResponseDTO empleoAResponseDTO=modelMapper.map(empleo,EmpleoResponseDTO.class);

        empleoAResponseDTO.setRazonSocial(empleador.getRazonSocial());

        return empleoAResponseDTO;
    }

}
