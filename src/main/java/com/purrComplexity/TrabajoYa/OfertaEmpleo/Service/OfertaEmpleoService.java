package com.purrComplexity.TrabajoYa.OfertaEmpleo.Service;

import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.Contrato.dto.ContratoDTO;
import com.purrComplexity.TrabajoYa.Empleo.Empleo;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.OfertaEmpleo;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.Repository.OfertaEmpleoRepository;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.dto.OfertaEmpleoRequestDTO;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.dto.OfertaEmpleoResponseDTO;
import com.purrComplexity.TrabajoYa.Trabajador.Trabajador;
import com.purrComplexity.TrabajoYa.Trabajador.dto.TrabajadorDTO;
import com.purrComplexity.TrabajoYa.User.Repository.UserAccountRepository;
import com.purrComplexity.TrabajoYa.User.UserAccount;
import com.purrComplexity.TrabajoYa.exception.OfertaEmpleoNoPerteneceAlEmpleadorException;
import com.purrComplexity.TrabajoYa.exception.OfertaEmpleoNotFound;
import com.purrComplexity.TrabajoYa.exception.UsuarioNoEsEmpleadorException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfertaEmpleoService {
    private final OfertaEmpleoRepository ofertaEmpleoRepository;
    private final ModelMapper modelMapper;
    private final UserAccountRepository userAccountRepository;

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

    public Page<OfertaEmpleoResponseDTO> obtenerOfertasEmpleoPaginadas(Pageable pageable) {
        Page<OfertaEmpleo> page = ofertaEmpleoRepository.findAll(pageable);
        return page.map(oferta -> modelMapper.map(oferta, OfertaEmpleoResponseDTO.class));
    }

    public List<TrabajadorDTO> obtenerPosultantesOfertaEmpleo(Long userId, Long id){
        UserAccount userAccount=userAccountRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("No existe el usuario"));

        if (!userAccount.getIsEmpresario()){
            throw new UsuarioNoEsEmpleadorException();
        }

        OfertaEmpleo ofertaEmpleo=ofertaEmpleoRepository.findById(id).orElseThrow(OfertaEmpleoNotFound::new);

        if (!userAccount.getEmpresario().getRuc().equals(ofertaEmpleo.getEmpleador().getRuc())){
            throw new OfertaEmpleoNoPerteneceAlEmpleadorException();
        }

        List<Trabajador> trabajadors=ofertaEmpleo.getPostulantes();

        List<TrabajadorDTO> trabajadorDTOS=new ArrayList<>();

        for (Trabajador trabajador : trabajadors) {
            TrabajadorDTO dto = modelMapper.map(trabajador, TrabajadorDTO.class);
            trabajadorDTOS.add(dto);
        }

        return trabajadorDTOS;
    }

    public List<TrabajadorDTO> obtenerContratadosOfertaEmpleo(Long userId, Long id){
        UserAccount userAccount=userAccountRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("No existe el usuario"));

        if (!userAccount.getIsEmpresario()){
            throw new UsuarioNoEsEmpleadorException();
        }

        OfertaEmpleo ofertaEmpleo=ofertaEmpleoRepository.findById(id).orElseThrow(OfertaEmpleoNotFound::new);

        if (!userAccount.getEmpresario().getRuc().equals(ofertaEmpleo.getEmpleador().getRuc())){
            throw new OfertaEmpleoNoPerteneceAlEmpleadorException();
        }

        List<Trabajador> trabajadors=ofertaEmpleo.getContratados();

        List<TrabajadorDTO> trabajadorDTOS=new ArrayList<>();

        for (Trabajador trabajador : trabajadors) {
            TrabajadorDTO dto = modelMapper.map(trabajador, TrabajadorDTO.class);
            trabajadorDTOS.add(dto);
        }

        return trabajadorDTOS;
    }

    public List<ContratoDTO> obtenerContratosOfertaEmpleo(Long userId, Long idOferta){
        UserAccount userAccount=userAccountRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("No existe el usuario"));

        if (!userAccount.getIsEmpresario()){
            throw new UsuarioNoEsEmpleadorException();
        }

        OfertaEmpleo ofertaEmpleo=ofertaEmpleoRepository.findById(idOferta).orElseThrow(OfertaEmpleoNotFound::new);

        if (!ofertaEmpleo.getEmpleador().getRuc().equals(userAccount.getEmpresario().getRuc())){
            throw new OfertaEmpleoNoPerteneceAlEmpleadorException();
        }

        List<Contrato> contratos=ofertaEmpleo.getContratos();

        List<ContratoDTO> contratoDTOS=new ArrayList<>();

        for (Contrato a:contratos){
            ContratoDTO contratoDTO=new ContratoDTO();
            contratoDTO.setId(a.getId());
            contratoDTO.setFechaCreacion(a.getFechaCreacion());
            contratoDTO.setOfertaEmpleoId(a.getOfertaEmpleo().getIdOfertaEmpleo());
            contratoDTO.setPersonaContratadaId(a.getTrabajadorContratado().getId());
            contratoDTO.setPromedioCalificaciones(a.getCalificacionPromedio());

            contratoDTOS.add(contratoDTO);
        }

        return contratoDTOS;

    }

}
