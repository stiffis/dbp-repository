package com.purrComplexity.TrabajoYa.Controller;

import com.purrComplexity.TrabajoYa.CalificacionTrabajador.dto.CalificacionTrabajadorRequestDTO;
import com.purrComplexity.TrabajoYa.CalificacionTrabajador.dto.CalificacionTrabajadorResponseDTO;
import com.purrComplexity.TrabajoYa.Contrato.ContratoService;
import com.purrComplexity.TrabajoYa.Contrato.dto.ContratoDTO;
import com.purrComplexity.TrabajoYa.Contrato.dto.CreateContratoDTO;
import com.purrComplexity.TrabajoYa.Contrato.dto.UpdateContratoDTO;
import com.purrComplexity.TrabajoYa.Empleador.dto.UpdateEmpleadorDTO;
import com.purrComplexity.TrabajoYa.Empleo.Service.EmpleoService;
import com.purrComplexity.TrabajoYa.Empleo.dto.EmpleoRequestDTO;
import com.purrComplexity.TrabajoYa.Empleo.dto.EmpleoResponseDTO;
import com.purrComplexity.TrabajoYa.OfertaEmpleo.OfertaEmpleo;
import com.purrComplexity.TrabajoYa.CalificacionEmpresa.Service.CalificacionEmpresaService;
import com.purrComplexity.TrabajoYa.CalificacionEmpresa.dto.CalificacionEmpresaRequestDTO;
import com.purrComplexity.TrabajoYa.CalificacionEmpresa.dto.CalificacionEmpresaResponseDTO;
import com.purrComplexity.TrabajoYa.CalificacionTrabajador.Service.CalificacionTrabajadorService;
import com.purrComplexity.TrabajoYa.Empleador.Service.EmpleadorService;
import com.purrComplexity.TrabajoYa.Empleador.dto.EmpleadorRequestDTO;
import com.purrComplexity.TrabajoYa.Empleador.dto.EmpleadorResponseDTO;
import com.purrComplexity.TrabajoYa.Trabajador.TrabajadorRepository;
import com.purrComplexity.TrabajoYa.Trabajador.TrabajadorServiceImpl;
import com.purrComplexity.TrabajoYa.Trabajador.dto.AceptadoDTO;
import com.purrComplexity.TrabajoYa.Trabajador.dto.CreateTrabajadorDTO;
import com.purrComplexity.TrabajoYa.Trabajador.dto.TrabajadorDTO;
import com.purrComplexity.TrabajoYa.Service.AplicationService;
import com.purrComplexity.TrabajoYa.Trabajador.dto.UpdateTrabajadorDTO;
import com.purrComplexity.TrabajoYa.User.UserAccount;
import com.purrComplexity.TrabajoYa.User.dto.ProfileDTO;
import com.purrComplexity.TrabajoYa.email.EmailEvent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final TrabajadorServiceImpl trabajadorServiceImpl;
    private final EmpleoService empleoService;
    private final ContratoService contratoService;
    private final CalificacionTrabajadorService calificacionTrabajadorService;
    private final CalificacionEmpresaService calificacionEmpresaService;
    private final ApplicationEventPublisher eventPublisher;
    private final AplicationService aplicationService;
    private final OfertaEmpleoService ofertaEmpleoService;
    private final TrabajadorRepository trabajadorRepository;

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
    @PostMapping("/trabajador")
    public ResponseEntity<TrabajadorDTO> postTrabajador(@RequestBody CreateTrabajadorDTO trabajadorDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userDetails = (UserAccount) authentication.getPrincipal();
        Long idUsuario = userDetails.getId();

        TrabajadorDTO createTrabajadorDTO = trabajadorServiceImpl.createPersona(idUsuario, trabajadorDTO);

        if (createTrabajadorDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Asumiendo que createPersonaDTO tiene el email
        String email = createTrabajadorDTO.getCorreo();

        // Publicar evento para enviar correo
        eventPublisher.publishEvent(new EmailEvent(email, "PERSONA"));
        System.out.println("Evento email publicado para persona: " + email);

        return new ResponseEntity<>(createTrabajadorDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @DeleteMapping("/eliminar/empleador/{ruc}")
    public ResponseEntity<String> deleteEmpleador(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userDetails = (UserAccount) authentication.getPrincipal();
        Long idUsuario = userDetails.getId();

        empleadorService.eliminarEmpleador(idUsuario,id);

        return new ResponseEntity<>("Se borró la cuenta de empleador",HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @DeleteMapping("/eliminar/trabajador/{id}")
    public ResponseEntity<String> deleteTrabajador(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userDetails = (UserAccount) authentication.getPrincipal();
        Long idUsuario = userDetails.getId();

        trabajadorServiceImpl.deleteTrabajador(idUsuario,id);

        return new ResponseEntity<>("Se borró la cuenta de Trabajador",HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @PatchMapping("/actualizar/trabajador/{id}")
    public ResponseEntity<TrabajadorDTO> patchTrabajador(@PathVariable Long id, @RequestBody UpdateTrabajadorDTO updateTrabajadorDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userDetails = (UserAccount) authentication.getPrincipal();
        Long idUsuario = userDetails.getId();

        TrabajadorDTO trabajadorDTO=trabajadorServiceImpl.updateTrabajador(idUsuario,id,updateTrabajadorDTO);

        return new ResponseEntity<>(trabajadorDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @PatchMapping("/actualizar/empleador/{id}")
    public ResponseEntity<EmpleadorResponseDTO> patchEmpleador(@PathVariable String id, @Valid @RequestBody UpdateEmpleadorDTO updateEmpleadorDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userDetails = (UserAccount) authentication.getPrincipal();
        Long idUsuario = userDetails.getId();

        EmpleadorResponseDTO empleadorResponseDTO=empleadorService.actualizarParcialmenteEmpleador(idUsuario,updateEmpleadorDTO,id);

        return new ResponseEntity<>(empleadorResponseDTO,HttpStatus.OK);

    }

    @PreAuthorize("hasRole('USUARIO')")
    @PostMapping("/crear/ofertaEmpleo")
    public ResponseEntity<EmpleoResponseDTO> postOfertaEmpleo(@RequestBody EmpleoRequestDTO empleoRequestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userDetails = (UserAccount) authentication.getPrincipal();
        Long idUsuario = userDetails.getId();

        EmpleoResponseDTO empleoAResponseDTO=empleoService.crearYAsignarEmpleo(empleoRequestDTO,idUsuario);

        return new ResponseEntity<>(empleoAResponseDTO,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @PostMapping("/empleador/aceptar/{id_postulante}/{id_empleo}")
    public ResponseEntity<AceptadoDTO> aceptarEmpleador(@PathVariable Long id_postulante,@PathVariable Long id_empleo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userDetails = (UserAccount) authentication.getPrincipal();
        Long idUsuario = userDetails.getId();

        AceptadoDTO aceptadoDTO=aplicationService.aceptarTrabajador(idUsuario,id_empleo,id_postulante);

        return new ResponseEntity<>(aceptadoDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @PostMapping("/empleador/rechazar/{id_postulante}/{id_empleo}")
    public ResponseEntity<String> rechazarEmpleador(@PathVariable Long id_empleo,@PathVariable Long id_postulante){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userDetails = (UserAccount) authentication.getPrincipal();
        Long idUsuario = userDetails.getId();

        String message=aplicationService.rechazarTrabajador(idUsuario,id_empleo,id_postulante);

        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @PatchMapping("/actualizar/OfertaEmpleo/{id}") //
    public ResponseEntity<EmpleoResponseDTO> patchOfertaEmpleo(@RequestBody EmpleoRequestDTO empleoRequestDTO, @PathVariable Long id){
        EmpleoResponseDTO response = empleoService.actualizarParcialmenteEmpleo(id, empleoRequestDTO);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @DeleteMapping("/eliminar/OfertaEmpleo/{id}")
    public ResponseEntity<String> deleteOfertaEmpleo(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userDetails = (UserAccount) authentication.getPrincipal();
        Long idUsuario = userDetails.getId();

        empleoService.eliminarEmpleo(idUsuario,id);


        return new ResponseEntity<>("Se elimino correctamente",HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @PostMapping("/contrato/crearnuevo/{id_usuario}/{id_oferta_empleo}/{id_trabajador}")
    public ResponseEntity<ContratoDTO> postContrato(
            @Valid @RequestBody CreateContratoDTO createContratoDTO, @PathVariable Long id_usuario, @PathVariable Long id_trabajador,
            @PathVariable Long id_oferta_empleo) {

        ContratoDTO contratoDTO = contratoService.createContrato(id_usuario,id_oferta_empleo,id_trabajador,createContratoDTO);

    URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contratoDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(contratoDTO);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @PostMapping("/calificar/Trabajador/{idContrato}/{idPersona}")
    public ResponseEntity<CalificacionTrabajadorResponseDTO> postCalificacionTrabajador(@RequestBody CalificacionTrabajadorRequestDTO calificacionPersonaRequestDTO
            , @PathVariable Long idContrato, @PathVariable Long idPersona){
        CalificacionTrabajadorResponseDTO calificacionPersonaResponseDTO = calificacionTrabajadorService.crearCalificacion(idContrato, idPersona, calificacionPersonaRequestDTO);

        return new ResponseEntity<>(calificacionPersonaResponseDTO,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @PostMapping("/calificar/Empresa/{idContrato}/{idPersona}")
    public ResponseEntity<CalificacionEmpresaResponseDTO> postCalificacionEmpleador(@RequestBody CalificacionEmpresaRequestDTO calificacionEmpresaRequestDTO
            , @PathVariable Long idContrato, @PathVariable String idPersona){
        CalificacionEmpresaResponseDTO calificacionEmpresaResponseDTO=calificacionEmpresaService.crearCalificacion(idContrato,idPersona,calificacionEmpresaRequestDTO);

        return new ResponseEntity<>(calificacionEmpresaResponseDTO,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @GetMapping("/trabajador/{id}")
    public ResponseEntity<TrabajadorDTO> getTrabajadorById(@PathVariable Long id) {
        TrabajadorDTO persona = trabajadorServiceImpl.getPersonaById(id);
        return ResponseEntity.ok(persona);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @GetMapping("/empleador/{ruc}")
    public ResponseEntity<EmpleadorResponseDTO> getEmpleadorById(@PathVariable String ruc) {
        EmpleadorResponseDTO dto = empleadorService.obtenerEmpleadorPorId(ruc);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @GetMapping("/ofertas-empleo")
    public ResponseEntity<Page<OfertaEmpleoResponseDTO>> getOfertasEmpleoPaginadas(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<OfertaEmpleoResponseDTO> page = ofertaEmpleoService.obtenerOfertasEmpleoPaginadas(pageable);
        return ResponseEntity.ok(page);
    }

    @PreAuthorize("hasRole('USUARIO')")
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

    @PreAuthorize("hasRole('USUARIO')")
    @GetMapping("/mis/postulaciones")
    public ResponseEntity<List<OfertaEmpleoResponseDTO>> getPostulaciones(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount detallesUser = (UserAccount) authentication.getPrincipal();
        Long idUsuario = detallesUser.getId();

        List<OfertaEmpleoResponseDTO> ofertaEmpleoResponseDTOS=aplicationService.obtenerPostulaciones(idUsuario);

        return new ResponseEntity<>(ofertaEmpleoResponseDTOS,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @GetMapping("/mis/postulantes/oferta/{id_ofertaEmpleo}")
    public ResponseEntity<List<TrabajadorDTO>> getPostulantesOferta(@PathVariable Long id_ofertaEmpleo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount detallesUser = (UserAccount) authentication.getPrincipal();
        Long idUsuario = detallesUser.getId();

        List<TrabajadorDTO> trabajadorDTOS =aplicationService.obtenerPostulantes(idUsuario,id_ofertaEmpleo);

        return new ResponseEntity<>(trabajadorDTOS,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @GetMapping("/ofertas/cercanas/{radio_metros}")
    public ResponseEntity<List<OfertaEmpleoResponseDTO>> getOfertasCercanas(@PathVariable Double radio_metros){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount detallesUser = (UserAccount) authentication.getPrincipal();
        Long idUsuario = detallesUser.getId();

        List<OfertaEmpleoResponseDTO> ofertaEmpleoResponseDTOS=aplicationService.getOfertasEmpleoCercanos(idUsuario,radio_metros);

        return new ResponseEntity<>(ofertaEmpleoResponseDTOS,HttpStatus.OK);

    }

    @PreAuthorize("hasRole('USUARIO')")
    @GetMapping("auth/profiles")
    public ResponseEntity<ProfileDTO> getProfiles(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount detallesUser = (UserAccount) authentication.getPrincipal();
        Long idUsuario = detallesUser.getId();

        ProfileDTO profileDTO=aplicationService.getProfile(idUsuario);

        return new ResponseEntity<>(profileDTO,HttpStatus.OK);


    }
}
