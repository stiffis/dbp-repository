package com.purrComplexity.TrabajoYa.EmpleoB.Service;

import com.purrComplexity.TrabajoYa.Empleador.Empleador;
import com.purrComplexity.TrabajoYa.Empleador.Repository.EmpleadorRepository;
import com.purrComplexity.TrabajoYa.EmpleoB.Repository.EmpleoBRepository;
import com.purrComplexity.TrabajoYa.EmpleoB.EmpleoB;
import com.purrComplexity.TrabajoYa.EmpleoB.dto.EmpleoBRequestDTO;
import com.purrComplexity.TrabajoYa.EmpleoB.dto.EmpleoBResponseDTO;
import com.purrComplexity.TrabajoYa.exception.EmpleoBNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleoBService {
    private final EmpleoBRepository empleoBRepository;
    private final EmpleadorRepository empleadorRepository;
    private final ModelMapper modelMapper;

    public EmpleoBResponseDTO publicarEmpleoB(EmpleoBRequestDTO empleoBRequestDTO){
        EmpleoB empleoB=modelMapper.map(empleoBRequestDTO, EmpleoB.class);

        empleoB =empleoBRepository.save(empleoB);

        EmpleoBResponseDTO empleoBResponseDTO=modelMapper.map(empleoB,EmpleoBResponseDTO.class);

        return empleoBResponseDTO;
    }

    public void eliminarEmpleoB(Long id){
        EmpleoB empleoB=
                empleoBRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontro el Empleo Tipo B"));

        empleoBRepository.delete(empleoB);

    }

    public EmpleoBResponseDTO actualizarEmpleoB(Long id, EmpleoBRequestDTO empleoBRequestDTO){
        EmpleoB empleoB=
                empleoBRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontro el Empleo Tipo B"));

        modelMapper.map(empleoBRequestDTO,empleoB);

        empleoB=empleoBRepository.save(empleoB);

        EmpleoBResponseDTO empleoBResponseDTO=modelMapper.map(empleoB, EmpleoBResponseDTO.class);

        return empleoBResponseDTO;
    }

    public EmpleoBResponseDTO actualizarParcialmenteEmpleoB(Long id, EmpleoBRequestDTO empleoBRequestDTO){
        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        EmpleoB empleoB=
                empleoBRepository.findById(id).orElseThrow(()->new EmpleoBNotFoundException(id));

        mapper.map(empleoBRequestDTO,empleoB);

        empleoB=empleoBRepository.save(empleoB);

        EmpleoBResponseDTO empleoBResponseDTO=modelMapper.map(empleoB, EmpleoBResponseDTO.class);

        return empleoBResponseDTO;
    }

    public EmpleoBResponseDTO obtenerEmpleoBPorId(Long id){
        EmpleoB empleoB=
                empleoBRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontro el Empleo Tipo B"));

        EmpleoBResponseDTO empleoBResponseDTO=modelMapper.map(empleoB, EmpleoBResponseDTO.class);
        return empleoBResponseDTO;
    }

    public List<EmpleoBResponseDTO> obtenerTodosEmpleoB(){
        List<EmpleoB> empleoBS=empleoBRepository.findAll();

        List<EmpleoBResponseDTO> empleoBResponseDTOS=new ArrayList<>();

        for (EmpleoB empleoB: empleoBS){
            EmpleoBResponseDTO dto=modelMapper.map(empleoB,EmpleoBResponseDTO.class);
            empleoBResponseDTOS.add(dto);
        }

        return empleoBResponseDTOS;
    }

    public EmpleoBResponseDTO crearYAsignarEmpleoB(EmpleoBRequestDTO empleoBRequestDTO, String id){
        EmpleoB empleoB=modelMapper.map(empleoBRequestDTO,EmpleoB.class);

        Empleador empleador=empleadorRepository.findById(id).
                orElseThrow(()->new RuntimeException("No existe un empleador con ese id"));

        empleoB.setEmpleador(empleador);

        empleoB=empleoBRepository.save(empleoB);

        EmpleoBResponseDTO empleoBResponseDTO=modelMapper.map(empleoB,EmpleoBResponseDTO.class);

        empleoBResponseDTO.setRazonSocial(empleador.getRazonSocial());

        return empleoBResponseDTO;
    }

}
