package com.purrComplexity.TrabajoYa.Trabajador;

import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.Empleo.Empleo;
import com.purrComplexity.TrabajoYa.Empleo.Repository.EmpleoARepository;
import com.purrComplexity.TrabajoYa.Trabajador.dto.CreateTrabajadorDTO;
import com.purrComplexity.TrabajoYa.Trabajador.dto.TrabajadorDTO;
import com.purrComplexity.TrabajoYa.Trabajador.dto.TrabajadorPostulaDTO;
import com.purrComplexity.TrabajoYa.User.Repository.UserAccountRepository;
import com.purrComplexity.TrabajoYa.User.UserAccount;
import com.purrComplexity.TrabajoYa.exception.OfertaEmpleoNotFound;
import com.purrComplexity.TrabajoYa.exception.TrabajadorNotFound;
import com.purrComplexity.TrabajoYa.exception.TrabajadorWithSameCorreo;
import com.purrComplexity.TrabajoYa.exception.UsuarioYaEsTrabajadorException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
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
    public TrabajadorDTO getPersonaById(Long id) {
        Trabajador trabajador = trabajadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada con id: " + id));

        return modelMapper.map(trabajador, TrabajadorDTO.class);
    }
    
    @Override
    public List<Trabajador> getAllPersonas() {
        return trabajadorRepository.findAll();
    }
    
    @Override
    public Trabajador updatePersona(Long id, Trabajador trabajadorDetails) {
        Trabajador trabajador = trabajadorRepository.findById(id).orElseThrow(
                TrabajadorNotFound::new
        );

        trabajador.setNombresCompletos(trabajadorDetails.getNombresCompletos());
        trabajador.setCorreo(trabajadorDetails.getCorreo());
        trabajador.setLatitud(trabajadorDetails.getLatitud());
        trabajador.setLongitud(trabajador.getLongitud());
        trabajador.setFechaNacimiento(trabajadorDetails.getFechaNacimiento());
        trabajador.setHabilidades(trabajadorDetails.getHabilidades());
        trabajador.setDni(trabajadorDetails.getDni());
        return trabajadorRepository.save(trabajador);
    }
    
    @Override
    public void deletePersona(Long id) {
        Trabajador persona = trabajadorRepository.findById(id).orElseThrow(
                TrabajadorNotFound::new
        );
        trabajadorRepository.delete(persona);
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

