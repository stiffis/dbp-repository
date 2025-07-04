package com.purrComplexity.TrabajoYa.Persona;

import com.purrComplexity.TrabajoYa.Contrato.Contrato;
import com.purrComplexity.TrabajoYa.EmpleoA.EmpleoA;
import com.purrComplexity.TrabajoYa.EmpleoA.Repository.EmpleoARepository;
import com.purrComplexity.TrabajoYa.EmpleoB.EmpleoB;
import com.purrComplexity.TrabajoYa.EmpleoB.Repository.EmpleoBRepository;
import com.purrComplexity.TrabajoYa.Persona.Exceptions.PersonaWithSameCorreo;
import com.purrComplexity.TrabajoYa.Persona.dto.CreatePersonaDTO;
import com.purrComplexity.TrabajoYa.Persona.dto.PersonaDTO;
import com.purrComplexity.TrabajoYa.Persona.dto.PersonaPostulaDTO;
import com.purrComplexity.TrabajoYa.User.Repository.UserAccountRepository;
import com.purrComplexity.TrabajoYa.User.UserAccount;
import com.purrComplexity.TrabajoYa.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class PersonaServiceImpl implements PersonaService {
    private final PersonaRepository personaRepository;
    private final ModelMapper modelMapper;
    private final EmpleoARepository empleoARepository;
    private final EmpleoBRepository empleoBRepository;
    private final UserAccountRepository userAccountRepository;

    @Override
    public PersonaDTO createPersona(Long idUsuario,CreatePersonaDTO dto) {
        Persona persona = modelMapper.map(dto, Persona.class);

        if (personaRepository.existsByCorreo(dto.getCorreo())){
            throw new PersonaWithSameCorreo("El correo ya esta registrado");
        }

        UserAccount userAccount=userAccountRepository.findById(idUsuario).orElseThrow(()->new UsernameNotFoundException("No existe el usuario"));

        if (userAccount.getIsTrabajador()){
            throw new RuntimeException("El usuario ya tiene una cuenta como trabajador");
        }

        userAccount.setIsTrabajador(true);

        persona=personaRepository.save(persona);

        userAccount.setTrabajador(persona);

        userAccountRepository.save(userAccount);

        PersonaDTO personaResponseDto=modelMapper.map(persona, PersonaDTO.class);

        return personaResponseDto;
    }

    @Override
    public PersonaDTO getPersonaById(Long id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada con id: " + id));

        return modelMapper.map(persona, PersonaDTO.class);
    }
    
    @Override
    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }
    
    @Override
    public Persona updatePersona(Long id, Persona personaDetails) {
        Persona persona = personaRepository.findById(id).orElseThrow(
                ()->new RuntimeException("No se encontro a la persona")
        );

        persona.setNombresCompletos(personaDetails.getNombresCompletos());
        persona.setCorreo(personaDetails.getCorreo());
        persona.setLatitud(personaDetails.getLatitud());
        persona.setLongitud(persona.getLongitud());
        persona.setFechaNacimiento(personaDetails.getFechaNacimiento());
        persona.setHabilidades(personaDetails.getHabilidades());
        persona.setDni(personaDetails.getDni());
        return personaRepository.save(persona);
    }
    
    @Override
    public void deletePersona(Long id) {
        Persona persona = personaRepository.findById(id).orElseThrow(
                ()->new RuntimeException("No se encontro a la persona")
        );
        personaRepository.delete(persona);
    }
    
    @Override
    public List<Contrato> getContratosByPersona(Long personaId) {
        Persona persona = personaRepository.findById(personaId).orElseThrow(
                ()->new RuntimeException("No se encontro a la persona")
        );
        return persona.getContratos();
    }

    public PersonaPostulaDTO postularEmpleoTipoA(Long idPersona, Long idEmpleoA){
        Persona persona=personaRepository.findById(idPersona).orElseThrow(
                ()-> new RuntimeException("No existe Persona conn ese id")
        );

        EmpleoA empleoA=empleoARepository.findById(idEmpleoA).orElseThrow(
                ()->new RuntimeException("No existe el Empleo de Tipo A con ese id")
        );

        persona.getPostulaste().add(empleoA);
        empleoA.getPostulantes().add(persona);

        persona=personaRepository.save(persona);

        PersonaPostulaDTO postulaDTO=modelMapper.map(persona,PersonaPostulaDTO.class);

        postulaDTO.setIdOfertaEmpleo(empleoA.getIdOfertaEmpleo());

        return postulaDTO;

    }

    public PersonaPostulaDTO postularEmpleoTipoB(Long idPersona, Long idEmpleoB){
        Persona persona=personaRepository.findById(idPersona).orElseThrow(
                ()-> new RuntimeException("No existe Persona conn ese id")
        );

        EmpleoB empleoB=empleoBRepository.findById(idEmpleoB).orElseThrow(
                ()->new RuntimeException("No existe el Empleo de Tipo B con ese id")
        );

        persona.getPostulaste().add(empleoB);
        empleoB.getPostulantes().add(persona);

        persona=personaRepository.save(persona);

        PersonaPostulaDTO postulaDTO=modelMapper.map(persona,PersonaPostulaDTO.class);

        postulaDTO.setIdOfertaEmpleo(empleoB.getIdOfertaEmpleo());

        return postulaDTO;

    }
}

