package com.purrComplexity.TrabajoYa.OfertaEmpleo.Service;

import com.purrComplexity.TrabajoYa.OfertaEmpleo.OfertaEmpleo;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.Repository.OfertaEmpleoRepository;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.dto.OfertaEmpleoRequestDTO;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.dto.OfertaEmpleoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfertaEmpleoService {
    private final OfertaEmpleoRepository ofertaEmpleoRepository;
    private final ModelMapper modelMapper;

    public OfertaEmpleoResponseDTO publicarOfertaEmpleo(OfertaEmpleoRequestDTO ofertaEmpleoRequestDTO) {
        OfertaEmpleo ofertaEmpleo = modelMapper.map(ofertaEmpleoRequestDTO, OfertaEmpleo.class);

        ofertaEmpleo = ofertaEmpleoRepository.save(ofertaEmpleo);

        OfertaEmpleoResponseDTO ofertaEmpleoResponseDTO = modelMapper.map(ofertaEmpleo, OfertaEmpleoResponseDTO.class);

        return ofertaEmpleoResponseDTO;
    }

    public void eliminarOfertaEmpleo(Long id){
        OfertaEmpleo ofertaEmpleo=
                ofertaEmpleoRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontró la oferta de empleo"));

        ofertaEmpleoRepository.delete(ofertaEmpleo);

    }

    public OfertaEmpleoResponseDTO actulizarOfertaEmpleo(OfertaEmpleoRequestDTO ofertaEmpleoRequestDTO, Long id){
        OfertaEmpleo ofertaEmpleo=
                ofertaEmpleoRepository.findById(id).orElseThrow(()->new RuntimeException("No e encontró la oferta de empleo"));

        modelMapper.map(ofertaEmpleoRequestDTO, ofertaEmpleo);

        ofertaEmpleo = ofertaEmpleoRepository.save(ofertaEmpleo);

        OfertaEmpleoResponseDTO responseDTO = modelMapper.map(ofertaEmpleo, OfertaEmpleoResponseDTO.class);

        return responseDTO;
    }

    public OfertaEmpleoResponseDTO actulizarParcialOfertaEmpleo(OfertaEmpleoRequestDTO ofertaEmpleoRequestDTO, Long id){
        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        OfertaEmpleo ofertaEmpleo=
                ofertaEmpleoRepository.findById(id).orElseThrow(()->new RuntimeException("No e encontró la oferta de empleo"));


        mapper.map(ofertaEmpleoRequestDTO, ofertaEmpleo);

        ofertaEmpleo = ofertaEmpleoRepository.save(ofertaEmpleo);

        OfertaEmpleoResponseDTO responseDTO = mapper.map(ofertaEmpleo, OfertaEmpleoResponseDTO.class);

        return responseDTO;
    }

    public OfertaEmpleoResponseDTO obtenerOfertaEmpleoPorId(Long id){
        OfertaEmpleo ofertaEmpleo=
                ofertaEmpleoRepository.findById(id).orElseThrow(()->new RuntimeException("No e encontró la oferta de empleo"));

        OfertaEmpleoResponseDTO responseDTO = modelMapper.map(ofertaEmpleo, OfertaEmpleoResponseDTO.class);

        return responseDTO;
    }

    public List<OfertaEmpleoResponseDTO> obtenerTodasOfertaEmpleo(){

        List<OfertaEmpleo> ofertasEmpleos= ofertaEmpleoRepository.findAll();

        List<OfertaEmpleoResponseDTO> ofertaEmpleoResponseDTOS=new ArrayList<>();

        for (OfertaEmpleo oferta : ofertasEmpleos) {
            OfertaEmpleoResponseDTO dto = modelMapper.map(oferta, OfertaEmpleoResponseDTO.class);
            ofertaEmpleoResponseDTOS.add(dto);
        }
        return ofertaEmpleoResponseDTOS;
    }

}
