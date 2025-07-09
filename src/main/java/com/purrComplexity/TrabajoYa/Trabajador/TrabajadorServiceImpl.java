package com.purrComplexity.TrabajoYa.Trabajador;

import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.Empleo.Empleo;
import com.purrComplexity.TrabajoYa.Empleo.Repository.EmpleoARepository;
import com.purrComplexity.TrabajoYa.Trabajador.dto.CreateTrabajadorDTO;
import com.purrComplexity.TrabajoYa.Trabajador.dto.TrabajadorDTO;
import com.purrComplexity.TrabajoYa.Trabajador.dto.TrabajadorPostulaDTO;
import com.purrComplexity.TrabajoYa.Trabajador.dto.UpdateTrabajadorDTO;
import com.purrComplexity.TrabajoYa.User.Repository.UserAccountRepository;
import com.purrComplexity.TrabajoYa.User.UserAccount;
import com.purrComplexity.TrabajoYa.exception.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class TrabajadorServiceImpl implements TrabajadorService {
    private final TrabajadorRepository trabajadorRepository;
    private final ModelMapper modelMapper;
    private final EmpleoARepository empleoARepository;
    private final UserAccountRepository userAccountRepository;

    @Override
    public TrabajadorDTO createPersona(Long idUsuario, CreateTrabajadorDTO dto) {
        Trabajador trabajador = modelMapper.map(dto, Trabajador.class);

        if (trabajadorRepository.existsByCorreo(dto.getCorreo())){
            throw new TrabajadorWithSameCorreo();
        }

        UserAccount userAccount=userAccountRepository.findById(idUsuario).orElseThrow(()->new UsernameNotFoundException("No existe el usuario"));

        if (userAccount.getIsTrabajador()){
            throw new UsuarioYaEsTrabajadorException();
        }

        userAccount.setIsTrabajador(true);

        trabajador=trabajadorRepository.save(trabajador);

        userAccount.setTrabajador(trabajador);

        userAccountRepository.save(userAccount);

        TrabajadorDTO trabajadorDTO=modelMapper.map(trabajador, TrabajadorDTO.class);

        return trabajadorDTO;
    }

    @Override
    public TrabajadorDTO getTrabajador(Long idUsuario) {
        UserAccount userAccount = userAccountRepository.findById(idUsuario).orElseThrow(() -> new UsernameNotFoundException("No existe el usuario"));

        if (!userAccount.getIsTrabajador()){
            throw new UsuarioNoEsTrabajadorException();
        }

        Trabajador trabajador = userAccount.getTrabajador();

        return modelMapper.map(trabajador, TrabajadorDTO.class);
    }
    
    @Override
    public List<Trabajador> getAllPersonas() {
        return trabajadorRepository.findAll();
    }
    
    @Override
    public TrabajadorDTO updateTrabajador(Long idUsuario, UpdateTrabajadorDTO trabajadorDetails) {
        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        UserAccount userAccount=userAccountRepository.findById(idUsuario).orElseThrow(()->new UsernameNotFoundException("No existe el usuario"));
        if (!userAccount.getIsTrabajador()){
            throw new UsuarioNoEsTrabajadorException();
        }

        Trabajador trabajador = userAccount.getTrabajador();

        mapper.map(trabajadorDetails,trabajador);

        Trabajador savedTrabajador=trabajadorRepository.save(trabajador);

        TrabajadorDTO trabajadorDTO=modelMapper.map(savedTrabajador,TrabajadorDTO.class);

        return trabajadorDTO;
    }
    
    @Override
    public void deleteTrabajador(Long userId ,Long id) {
        UserAccount userAccount=userAccountRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("El usuario no existe"));
        if (!userAccount.getIsTrabajador()){
            throw new UsuarioNoEsTrabajadorException();
        }

        Trabajador trabajador = trabajadorRepository.findById(id).orElseThrow(
                TrabajadorNotFound::new
        );

        if (!userAccount.getTrabajador().getId().equals(trabajador.getId())){
            throw new TrabajadorNoPerteneceAlUsuarioException();
        }

        trabajadorRepository.delete(trabajador);
    }
    
    @Override
    public List<Contrato> getContratosByPersona(Long trabajadorID) {
        Trabajador persona = trabajadorRepository.findById(trabajadorID).orElseThrow(
                TrabajadorNotFound::new
        );
        return persona.getContratos();
    }

    public TrabajadorPostulaDTO postularEmpleoTipoA(Long idTrabajador, Long idEmpleoA){
        Trabajador trabajador=trabajadorRepository.findById(idTrabajador).orElseThrow(
                TrabajadorNotFound::new
        );

        Empleo empleo=empleoARepository.findById(idEmpleoA).orElseThrow(
                OfertaEmpleoNotFound::new
        );

        trabajador.getPostulaste().add(empleo);
        empleo.getPostulantes().add(trabajador);

        trabajador=trabajadorRepository.save(trabajador);

        TrabajadorPostulaDTO postulaDTO=modelMapper.map(trabajador, TrabajadorPostulaDTO.class);

        postulaDTO.setIdOfertaEmpleo(empleo.getIdOfertaEmpleo());

        return postulaDTO;

    }
}

