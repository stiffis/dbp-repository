package com.purrComplexity.TrabajoYa.Contrato;

import com.purrComplexity.TrabajoYa.Contrato.dto.ContratoDTO;
import com.purrComplexity.TrabajoYa.Contrato.dto.CreateContratoDTO;
import com.purrComplexity.TrabajoYa.Contrato.dto.UpdateContratoDTO;
import com.purrComplexity.TrabajoYa.Contrato.mapper.ContratoMapper;
import com.purrComplexity.TrabajoYa.Empleador.Empleador;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.OfertaEmpleo;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.Repository.OfertaEmpleoRepository;
import com.purrComplexity.TrabajoYa.Persona.PersonaRepository;
import com.purrComplexity.TrabajoYa.Persona.Persona;
import com.purrComplexity.TrabajoYa.email.EmailService;
import com.purrComplexity.TrabajoYa.exception.FutureDateNotAllowedException;
import com.purrComplexity.TrabajoYa.exception.RatingOutOfRangeException;
import com.purrComplexity.TrabajoYa.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContratoServiceImpl implements ContratoService {
    private final ContratoRepository contratoRepository;
    private final PersonaRepository personaRepository;
    private final OfertaEmpleoRepository ofertaEmpleoRepository;
    private final ContratoMapper contratoMapper;
    private final EmailService emailService;

    @Override
    public ContratoDTO createContrato(CreateContratoDTO createContratoDTO) {
        Long personaId = createContratoDTO.personaContratadaId();
        Long ofertaEmpleoId = createContratoDTO.ofertaEmpleoId();

        Persona personaContratada = personaRepository.findById(personaId)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con id: " + personaId));

        OfertaEmpleo oferta = ofertaEmpleoRepository.findById(ofertaEmpleoId)
                .orElseThrow(() -> new ResourceNotFoundException("Oferta de empleo no encontrada con id: " + ofertaEmpleoId));

        Empleador empleador = oferta.getEmpleador();

        if (createContratoDTO.fechaCreacion().after(new Date())) {
            throw new FutureDateNotAllowedException(createContratoDTO.fechaCreacion());
        }

        if (createContratoDTO.calificaciones() != null) {
            createContratoDTO.calificaciones().forEach(puntaje -> {
                if (puntaje < 1 || puntaje > 5) {
                    throw new RatingOutOfRangeException("Calificaciones deben ser 1-5");
                }
            });
        }

        Contrato contrato = contratoMapper.toEntity(createContratoDTO, personaContratada, oferta);
        Contrato savedContrato = contratoRepository.save(contrato);

        String mensajeEmpleador = """
Estimado empleador,

Nos complace informarle que se ha creado un nuevo contrato en la plataforma TrabajoYa.

Por favor, revise los detalles y confirme con la persona contratada para asegurar el correcto inicio de la relación laboral.

Quedamos a su disposición para cualquier consulta o apoyo adicional.

Atentamente,
Equipo TrabajoYa
""";

        emailService.sendSimpleMessage(empleador.getCorreo(), "Notificación de nuevo contrato en TrabajoYa", mensajeEmpleador);

        String mensajePersona = """
Estimado/a,

Le informamos que ha sido seleccionado/a para un nuevo contrato a través de TrabajoYa.

Le recomendamos revisar la oferta y comunicarse con el empleador para coordinar los detalles.

Gracias por confiar en nosotros.

Atentamente,
Equipo TrabajoYa
""";

        emailService.sendSimpleMessage(personaContratada.getCorreo(), "Confirmación de nuevo contrato en TrabajoYa", mensajePersona);

        return contratoMapper.toDTO(savedContrato);
    }

    @Override
    public ContratoDTO getContratoById(Long id) {
        Contrato contrato = contratoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Contrato no encontrado con id: " + id));
        return contratoMapper.toDTO(contrato);
    }
    
    @Override
    public List<ContratoDTO> getAllContratos() {
        return contratoRepository.findAll().stream()
            .map(contratoMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public ContratoDTO updateContrato(Long id, UpdateContratoDTO updateContratoDTO) {
        Contrato contrato = contratoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Contrato no encontrado con id: " + id));
            
        Persona personaContratada = null;
        if (updateContratoDTO.getPersonaContratadaId() != null) {
            personaContratada = personaRepository.findById(updateContratoDTO.getPersonaContratadaId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con id: " + updateContratoDTO.getPersonaContratadaId()));
        }
        
        contratoMapper.updateEntityFromDTO(updateContratoDTO, contrato, personaContratada);
        Contrato updatedContrato = contratoRepository.save(contrato);
        return contratoMapper.toDTO(updatedContrato);
    }
    
    @Override
    public void deleteContrato(Long id) {
        if (!contratoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrato no encontrado con id: " + id);
        }
        contratoRepository.deleteById(id);
    }
    
    @Override
    public List<ContratoDTO> getContratosByPersonaId(Long personaId) {
        if (!personaRepository.existsById(personaId)) {
            throw new ResourceNotFoundException("Persona no encontrada con id: " + personaId);
        }
        return contratoRepository.findByPersonaContratadaId(personaId).stream()
            .map(contratoMapper::toDTO)
            .collect(Collectors.toList());
    }
}

