package com.purrComplexity.TrabajoYa.Controller;

import com.purrComplexity.TrabajoYa.Contrato.ContratoService;
import com.purrComplexity.TrabajoYa.Contrato.dto.ContratoDTO;
import com.purrComplexity.TrabajoYa.Contrato.dto.CreateContratoDTO;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.OfertaEmpleo;
import com.purrComplexity.TrabajoYa.Persona.Persona;
import com.purrComplexity.TrabajoYa.CalificacionEmpresa.CalificacionEmpresa;
import com.purrComplexity.TrabajoYa.CalificacionEmpresa.Service.CalificacionEmpresaService;
import com.purrComplexity.TrabajoYa.CalificacionEmpresa.dto.CalificacionEmpresaRequestDTO;
import com.purrComplexity.TrabajoYa.CalificacionEmpresa.dto.CalificacionEmpresaResponseDTO;
import com.purrComplexity.TrabajoYa.CalificacionPersona.CalificacionPersonaRepository;
import com.purrComplexity.TrabajoYa.CalificacionPersona.Service.CalificacionPersonaService;
import com.purrComplexity.TrabajoYa.CalificacionPersona.dto.CalificacionPersonaRequestDTO;
import com.purrComplexity.TrabajoYa.CalificacionPersona.dto.CalificacionPersonaResponseDTO;
import com.purrComplexity.TrabajoYa.Empleador.Service.EmpleadorService;
import com.purrComplexity.TrabajoYa.Empleador.dto.EmpleadorRequestDTO;
import com.purrComplexity.TrabajoYa.Empleador.dto.EmpleadorResponseDTO;
import com.purrComplexity.TrabajoYa.EmpleoA.Service.EmpleoAService;
import com.purrComplexity.TrabajoYa.EmpleoA.dto.EmpleoARequestDTO;
import com.purrComplexity.TrabajoYa.EmpleoA.dto.EmpleoAResponseDTO;
import com.purrComplexity.TrabajoYa.EmpleoB.Service.EmpleoBService;
import com.purrComplexity.TrabajoYa.EmpleoB.dto.EmpleoBRequestDTO;
import com.purrComplexity.TrabajoYa.EmpleoB.dto.EmpleoBResponseDTO;
import com.purrComplexity.TrabajoYa.Persona.PersonaServiceImpl;
import com.purrComplexity.TrabajoYa.Persona.dto.AceptadoDTO;
import com.purrComplexity.TrabajoYa.Persona.dto.CreatePersonaDTO;
import com.purrComplexity.TrabajoYa.Persona.dto.PersonaDTO;
import com.purrComplexity.TrabajoYa.Service.AplicationService;
import com.purrComplexity.TrabajoYa.User.UserAccount;
import com.purrComplexity.TrabajoYa.email.EmailEvent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.Service.OfertaEmpleoService;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.dto.OfertaEmpleoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class AplicationController {

    private final EmpleadorService empleadorService;
    private final PersonaServiceImpl personaServiceImpl;
    private final EmpleoAService empleoAService;
    private final EmpleoBService empleoBService;
    private final ContratoService contratoService;
    private final CalificacionPersonaService calificacionPersonaService;
    private final CalificacionEmpresaService calificacionEmpresaService;
    private final ApplicationEventPublisher eventPublisher;
    private final AplicationService aplicationService;
    private final OfertaEmpleoService ofertaEmpleoService;

    @PreAuthorize("hasRole('USUARIO')")
    @PostMapping("/empleador")
    public ResponseEntity<EmpleadorResponseDTO> postEmpleador(@RequestBody EmpleadorRequestDTO dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userDetails = (UserAccount) authentication.getPrincipal();
        Long idUsuario = userDetails.getId();

        EmpleadorResponseDTO resp = empleadorService.crearEmpleador(idUsuario,dto);

        if(resp == null) throw new IllegalArgumentException("El cuerpo no puede estar vacio");

        String email = resp.getCorreo();
        eventPublisher.publishEvent(new EmailEvent(email, "EMPLEADOR"));
        System.out.println("Evento email publicado para empleador: " + email);

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }



    @PreAuthorize("hasRole('USUARIO')")
    @PostMapping("/persona")
    public ResponseEntity<PersonaDTO> postPersona(@RequestBody CreatePersonaDTO persona) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userDetails = (UserAccount) authentication.getPrincipal();
        Long idUsuario = userDetails.getId();

        PersonaDTO createPersonaDTO = personaServiceImpl.createPersona(idUsuario, persona);

        if (createPersonaDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Asumiendo que createPersonaDTO tiene el email
        String email = createPersonaDTO.getCorreo();

        // Publicar evento para enviar correo
        eventPublisher.publishEvent(new EmailEvent(email, "PERSONA"));
        System.out.println("Evento email publicado para persona: " + email);

        return new ResponseEntity<>(createPersonaDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @PostMapping("/crear/ofertaEmpleo/tipoA")
    public ResponseEntity<EmpleoAResponseDTO> crearEmpleoA(@RequestBody EmpleoARequestDTO empleoARequestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userDetails = (UserAccount) authentication.getPrincipal();
        Long idUsuario = userDetails.getId();

        EmpleoAResponseDTO empleoAResponseDTO=empleoAService.crearYAsignarEmpleoA(empleoARequestDTO,idUsuario);

        return new ResponseEntity<>(empleoAResponseDTO,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @PostMapping("/empleador/aceptar/{id_postulante}/{id_empleo}")
    public ResponseEntity<AceptadoDTO> aceptarEmpleador(@PathVariable Long id_postulante,@PathVariable Long id_empleo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userDetails = (UserAccount) authentication.getPrincipal();
        Long idUsuario = userDetails.getId();

        AceptadoDTO aceptadoDTO=aplicationService.aceptarPersona(idUsuario,id_empleo,id_postulante);

        return new ResponseEntity<>(aceptadoDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @PostMapping("/empleador/rechazar/{id_postulante}/{id_empleo}")
    public ResponseEntity<String> rechazarEmpleador(@PathVariable Long id_empleo,@PathVariable Long id_postulante){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userDetails = (UserAccount) authentication.getPrincipal();
        Long idUsuario = userDetails.getId();

        String message=aplicationService.rechazarPersona(idUsuario,id_empleo,id_postulante);

        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @PostMapping("/crear/ofertaEmpleo/tipoB/{rucEmpleador}")
    public ResponseEntity<EmpleoBResponseDTO> crearEmleoB(@RequestBody EmpleoBRequestDTO empleoBRequestDTO, @PathVariable String rucEmpleador){
        EmpleoBResponseDTO empleoBResponseDTO=empleoBService.crearYAsignarEmpleoB(empleoBRequestDTO,rucEmpleador);

        return new ResponseEntity<>(empleoBResponseDTO,HttpStatus.CREATED);
    }

    @PatchMapping("/actualizar/OfertaEmpleo/tipoA/{id}") //
    public ResponseEntity<EmpleoAResponseDTO> actualizarParcialmenteEmpleoA(@RequestBody EmpleoARequestDTO empleoARequestDTO, @PathVariable Long id){
        EmpleoAResponseDTO response = empleoAService.actualizarParcialmenteEmpleoA(id, empleoARequestDTO);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/actualizar/OfertaEmpleo/tipoB/{id}") //
    public ResponseEntity<EmpleoBResponseDTO> actualizarParcialmenteEmpleoB(@RequestBody EmpleoBRequestDTO empleoBRequestDTO, @PathVariable Long id){
        EmpleoBResponseDTO response = empleoBService.actualizarParcialmenteEmpleoB(id, empleoBRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/contrato/crearnuevo")
    public ResponseEntity<ContratoDTO> createContrato(
            @Valid @RequestBody CreateContratoDTO createContratoDTO) {

        ContratoDTO contratoDTO = contratoService.createContrato(createContratoDTO);

    URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contratoDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(contratoDTO);
    }

    @PostMapping("/calificar/Trabajador/{idContrato}/{idPersona}")
    public ResponseEntity<CalificacionPersonaResponseDTO> crearCalificacionPersona(@RequestBody CalificacionPersonaRequestDTO calificacionPersonaRequestDTO
            ,@PathVariable Long idContrato, @PathVariable Long idPersona){
        CalificacionPersonaResponseDTO calificacionPersonaResponseDTO = calificacionPersonaService.crearCalificacion(idContrato, idPersona, calificacionPersonaRequestDTO);

        return new ResponseEntity<>(calificacionPersonaResponseDTO,HttpStatus.CREATED);
    }

    @PostMapping("/calificar/Empresa/{idContrato}/{idPersona}")
    public ResponseEntity<CalificacionEmpresaResponseDTO> crearCalificacionEmpleador(@RequestBody CalificacionEmpresaRequestDTO calificacionEmpresaRequestDTO
            , @PathVariable Long idContrato, @PathVariable String idPersona){
        CalificacionEmpresaResponseDTO calificacionEmpresaResponseDTO=calificacionEmpresaService.crearCalificacion(idContrato,idPersona,calificacionEmpresaRequestDTO);

        return new ResponseEntity<>(calificacionEmpresaResponseDTO,HttpStatus.CREATED);
    }

    @GetMapping("/persona/{id}")
    public ResponseEntity<PersonaDTO> getPersona(@PathVariable Long id) {
        PersonaDTO persona = personaServiceImpl.getPersonaById(id);
        return ResponseEntity.ok(persona);
    }

    @GetMapping("/empleador/{ruc}")
    public ResponseEntity<EmpleadorResponseDTO> getEmpleador(@PathVariable String ruc) {
        EmpleadorResponseDTO dto = empleadorService.obtenerEmpleadorPorId(ruc);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/ofertas-empleo")
    public ResponseEntity<Page<OfertaEmpleoResponseDTO>> getOfertasEmpleoPaginadas(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<OfertaEmpleoResponseDTO> page = ofertaEmpleoService.obtenerOfertasEmpleoPaginadas(pageable);
        return ResponseEntity.ok(page);
    }


    @PostMapping("/postular/{idOfertaEmpleo}")
    public ResponseEntity<String> postular(@PathVariable Long idOfertaEmpleo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount detallesUser = (UserAccount) authentication.getPrincipal();
        Long idUsuario = detallesUser.getId();

        String message = aplicationService.postularEmpleo(idOfertaEmpleo, idUsuario);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @GetMapping("/mis/ofertasEmpleos")
    public ResponseEntity<List<OfertaEmpleo>> getEmpleos(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount detallesUser = (UserAccount) authentication.getPrincipal();
        Long idUsuario = detallesUser.getId();

        List<OfertaEmpleo> ofertaEmpleos=aplicationService.obtenerEmpleos(idUsuario);

        return new ResponseEntity<>(ofertaEmpleos,HttpStatus.OK);
    }

    @GetMapping("/mis/postulaciones")
    public ResponseEntity<List<OfertaEmpleoResponseDTO>> getPostulaciones(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount detallesUser = (UserAccount) authentication.getPrincipal();
        Long idUsuario = detallesUser.getId();

        List<OfertaEmpleoResponseDTO> ofertaEmpleoResponseDTOS=aplicationService.obtenerPostulaciones(idUsuario);

        return new ResponseEntity<>(ofertaEmpleoResponseDTOS,HttpStatus.OK);
    }




}
