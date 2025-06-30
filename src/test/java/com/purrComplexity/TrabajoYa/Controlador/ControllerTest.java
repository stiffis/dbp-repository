package com.purrComplexity.TrabajoYa.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.purrComplexity.TrabajoYa.Empleador.Empleador;
import com.purrComplexity.TrabajoYa.Empleador.Repository.EmpleadorRepository;
import com.purrComplexity.TrabajoYa.Empleador.dto.EmpleadorResponseDTO;
import com.purrComplexity.TrabajoYa.EmpleoA.dto.EmpleoAResponseDTO;
import com.purrComplexity.TrabajoYa.EmpleoB.dto.EmpleoBResponseDTO;
import com.purrComplexity.TrabajoYa.Persona.dto.PersonaDTO;
import com.purrComplexity.TrabajoYa.utils.Reader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    Reader reader;

    @Test
    public void crearEmpleador() throws Exception{

        String jsoncontent= Reader.readJsonFile("/Empleador/post.json");

        String responseContent = mockMvc.perform(post("/empleador")
                        .content(jsoncontent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        EmpleadorResponseDTO responseDTO = objectMapper.readValue(responseContent, EmpleadorResponseDTO.class);

        assertThat(responseDTO.getTelefonoPrincipal()).isEqualTo(987654321);
        assertThat(responseDTO.getRazonSocial()).isEqualTo("Empresa Ejemplo S.A.C.");
        assertThat(responseDTO.getCorreo()).isEqualTo("contacto@empresa.com");

    }

    @Test
    public void crearPersona() throws Exception{
        String jscontent=Reader.readJsonFile("Persona/post.json");

        String responseContent=mockMvc.perform(post("/persona")
                .content(jscontent)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin123".getBytes())))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper=new ObjectMapper();
        PersonaDTO personaDTO=objectMapper.readValue(responseContent,PersonaDTO.class);

        assertThat(personaDTO.getCorreo()).isEqualTo("juan.perez@example.com");
        assertThat(personaDTO.getDni()).isEqualTo("12345678");
        assertThat(personaDTO.getNombresCompletos()).isEqualTo("Juan Pérez García");
    }

    @Test
    public void crearEmpleoA() throws Exception{

        String jsoncontent= Reader.readJsonFile("/Empleador/post1.json");

        String responseContent = mockMvc.perform(post("/empleador")
                        .content(jsoncontent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        EmpleadorResponseDTO responseDTO = objectMapper.readValue(responseContent, EmpleadorResponseDTO.class);
        String rucResponse=responseDTO.getRuc();


        String jscontent1=Reader.readJsonFile("EmpleoA/post.json");
        String responseContent1=mockMvc.perform(post("/crear/ofertaEmpleo/tipoA/"+rucResponse)
                .content(jscontent1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        EmpleoAResponseDTO empleoAResponseDTO=objectMapper.readValue(responseContent1,EmpleoAResponseDTO.class);

        assertThat(empleoAResponseDTO.getRazonSocial()).isEqualTo(responseDTO.getRazonSocial());
        assertThat(empleoAResponseDTO.getPuesto()).isEqualTo("Mesero");
        assertThat(empleoAResponseDTO.getPeriodoPago()).isEqualTo("Mensual");
    }

    @Test
    public void crearEmpleoB() throws Exception{
        String jsoncontent= Reader.readJsonFile("/Empleador/post2.json");

        String responseContent = mockMvc.perform(post("/empleador")
                        .content(jsoncontent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        EmpleadorResponseDTO responseDTO = objectMapper.readValue(responseContent, EmpleadorResponseDTO.class);
        String rucResponse=responseDTO.getRuc();

        String jscontent1=Reader.readJsonFile("/EmpleoB/post.json");
        String responseContent1=mockMvc.perform(post("/crear/ofertaEmpleo/tipoB/"+rucResponse)
                        .content(jscontent1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        EmpleoBResponseDTO empleoBResponseDTO=objectMapper.readValue(responseContent1,EmpleoBResponseDTO.class);

        assertThat(empleoBResponseDTO.getRazonSocial()).isEqualTo(responseDTO.getRazonSocial());
        assertThat(empleoBResponseDTO.getDescripcion()).isEqualTo("Limpiar mi casa mientas me voy de viaje");

    }

}

