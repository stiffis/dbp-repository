package com.purrComplexity.TrabajoYa.Empleador.Service;

import com.purrComplexity.TrabajoYa.Empleador.Empleador;
import com.purrComplexity.TrabajoYa.Empleador.Exceptions.EmpleadorWithTheSameCorreo;
import com.purrComplexity.TrabajoYa.Empleador.Exceptions.EmpleadorWithTheSameRUC;
import com.purrComplexity.TrabajoYa.Empleador.Repository.EmpleadorRepository;
import com.purrComplexity.TrabajoYa.Empleador.dto.EmpleadorRequestDTO;
import com.purrComplexity.TrabajoYa.Empleador.dto.EmpleadorResponseDTO;
import com.purrComplexity.TrabajoYa.Persona.Exceptions.PersonaWithSameCorreo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadorService {
    private final EmpleadorRepository empleadorRepository;
    private final ModelMapper modelMapper;

    public EmpleadorResponseDTO crearEmpleador(EmpleadorRequestDTO empleadorRequestDTO) {
        if (empleadorRepository.existsByCorreo(empleadorRequestDTO.getCorreo())) {
            throw new EmpleadorWithTheSameCorreo("El correo ya está registrado");
        }
        if (empleadorRepository.existsById(empleadorRequestDTO.getRuc())) {
            throw new EmpleadorWithTheSameRUC("El RUC ya está registrado");
        }

        Empleador empleador = modelMapper.map(empleadorRequestDTO, Empleador.class);
        empleador = empleadorRepository.save(empleador);

        return modelMapper.map(empleador, EmpleadorResponseDTO.class);
    }

    public void eliminarEmpleador(String id){
        Empleador empleador=
                empleadorRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontró al empleador"));

        empleadorRepository.delete(empleador);

    }

    public EmpleadorResponseDTO actulizarEmpleador(EmpleadorRequestDTO empleadorRequestDTO, String id) {
        Empleador empleador=
                empleadorRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontró al empleador"));

        modelMapper.map(empleadorRequestDTO,empleadorRepository);

        empleador=empleadorRepository.save(empleador);

        EmpleadorResponseDTO empleadorResponseDTO=modelMapper.map(empleador,EmpleadorResponseDTO.class);

        return empleadorResponseDTO;
    }

    public EmpleadorResponseDTO actualizarParcialmenteEmpleador(EmpleadorRequestDTO empleadorRequestDTO, String id){
        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        Empleador empleador=
                empleadorRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontró al empleador"));

        mapper.map(empleadorRequestDTO,empleador);

        empleador=empleadorRepository.save(empleador);

        EmpleadorResponseDTO empleadorResponseDTO=modelMapper.map(empleador,EmpleadorResponseDTO.class);

        return empleadorResponseDTO;
    }

    public EmpleadorResponseDTO obtenerEmpleadorPorId(String id){
        Empleador empleador=
                empleadorRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontró al empleador"));

        EmpleadorResponseDTO empleadorResponseDTO=modelMapper.map(empleador,EmpleadorResponseDTO.class);

        return empleadorResponseDTO;
    }

    public List<EmpleadorResponseDTO> obtenerTodosEmpleador(){
        List<Empleador> empleadors=empleadorRepository.findAll();
        List<EmpleadorResponseDTO> empleadorResponseDTOS=new ArrayList<>();

        for (Empleador empleador: empleadors){
            EmpleadorResponseDTO dto=modelMapper.map(empleador,EmpleadorResponseDTO.class);
            empleadorResponseDTOS.add(dto);
        }

        return empleadorResponseDTOS;
    }
}
