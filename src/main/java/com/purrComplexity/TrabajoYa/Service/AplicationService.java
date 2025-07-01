package com.purrComplexity.TrabajoYa.Service;

import com.purrComplexity.TrabajoYa.Empleador.Empleador;
import com.purrComplexity.TrabajoYa.Empleador.Exceptions.EmpleadorNotFound;
import com.purrComplexity.TrabajoYa.Empleador.Repository.EmpleadorRepository;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.OfertaEmpleo;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.Repository.OfertaEmpleoRepository;
import com.purrComplexity.TrabajoYa.Persona.Exceptions.PersonaNotFound;
import com.purrComplexity.TrabajoYa.Persona.Persona;
import com.purrComplexity.TrabajoYa.Persona.PersonaRepository;
import com.purrComplexity.TrabajoYa.Persona.dto.AceptadoDTO;
import com.purrComplexity.TrabajoYa.User.Repository.UserAccountRepository;
import com.purrComplexity.TrabajoYa.User.UserAccount;
import com.purrComplexity.TrabajoYa.exception.EmpleoNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AplicationService {

    private final OfertaEmpleoRepository ofertaEmpleoRepository;
    private final PersonaRepository personaRepository;
    private final EmpleadorRepository empleadorRepository;
    private final UserAccountRepository userAccountRepository;

    public AceptadoDTO aceptarPersona(Long idUsuario,Long idEmpleo, Long idPostulante){
        OfertaEmpleo ofertaEmpleo=ofertaEmpleoRepository.findById(idEmpleo).orElseThrow(()-> new EmpleoNotFound("No se encontro el emeplo"));
        Persona persona=personaRepository.findById(idPostulante).orElseThrow(()-> new PersonaNotFound("No se encontró la persona"));

        UserAccount userAccount=userAccountRepository.findById(idUsuario).orElseThrow(()-> new RuntimeException("No existe el usuario"));

        Empleador empleador=userAccount.getEmpresario();

        if (empleador==null){
            throw new RuntimeException("El usuario no se creo una cuenta para ser empleador");
        }

        List<Persona> postulantes=ofertaEmpleo.getPostulantes();

        if (!Objects.equals(ofertaEmpleo.getEmpleador().getRuc(), empleador.getRuc())){
            throw new RuntimeException("No puedes contratar a esta persona porque tu no creaste el empleo");
        }

        boolean esPostulante = postulantes.stream()
                .anyMatch(p -> p.getId().equals(idPostulante));

        if (!esPostulante) {
            throw new IllegalArgumentException("La persona no ha postulado a esta oferta");
        }


        persona.getAplicaste().add(ofertaEmpleo);

        personaRepository.save(persona);

        AceptadoDTO aceptadoDTO= new AceptadoDTO();

        aceptadoDTO.setNombreCompletoEmpleado(persona.getNombresCompletos());
        aceptadoDTO.setIdEmpleo(idEmpleo);
        aceptadoDTO.setLugar(ofertaEmpleo.getLugar());
        aceptadoDTO.setCorreoEmpleador(empleador.getCorreo());

        return aceptadoDTO;
    }

    public String rechazarPersona(Long idUsuario,Long idEmpleo,Long idPostulante){

        OfertaEmpleo ofertaEmpleo=ofertaEmpleoRepository.findById(idEmpleo).orElseThrow(()-> new EmpleoNotFound("No se encontro el emeplo"));
        Persona persona=personaRepository.findById(idPostulante).orElseThrow(()-> new PersonaNotFound("No se encontró la persona"));

        UserAccount userAccount=userAccountRepository.findById(idUsuario).orElseThrow(()-> new RuntimeException("No existe el usuario"));

        Empleador empleador=userAccount.getEmpresario();

        if (empleador==null){
            throw new RuntimeException("El usuario no se creo una cuenta para ser empleador");
        }

        List<Persona> postulantes=ofertaEmpleo.getPostulantes();

        if (!Objects.equals(ofertaEmpleo.getEmpleador().getRuc(), empleador.getRuc())){
            throw new RuntimeException("No puedes rechazar a esta persona porque tu no creaste el empleo");
        }

        boolean esPostulante = postulantes.stream()
                .anyMatch(p -> p.getId().equals(idPostulante));

        if (!esPostulante) {
            throw new IllegalArgumentException("La persona no ha postulado a esta oferta");
        }

        persona.getPostulaste().remove(ofertaEmpleo);

        personaRepository.save(persona);

        return "Postulante rechazado con éxito";
    }
}
