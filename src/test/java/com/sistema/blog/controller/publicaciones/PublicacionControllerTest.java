package com.sistema.blog.controller.publicaciones;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.blog.dto.publicaciones.PublicacionDto;
import com.sistema.blog.dto.publicaciones.PublicacionRespuesta;
import com.sistema.blog.service.publicaciones.PublicacionServicio;
import com.sistema.blog.utils.TestEntities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PublicacionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class PublicacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicacionServicio publicacionServicio;

    private static PublicacionRespuesta publicacionRespuesta;
    private static PublicacionDto publicacionDto;

    private static final String PUBLICACIONES = "/api/publicaciones";

    @BeforeAll
    static void setUp() {
        publicacionRespuesta = TestEntities.publicacionRespuesta();
        publicacionDto = TestEntities.publicacionDto();
    }

    @Test
    void testVerPublicaciones() throws Exception {
        given(publicacionServicio.obtenerPublicaciones(0, 10, "id", "asc")).willReturn(publicacionRespuesta);
        mockMvc.perform(get(PUBLICACIONES)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testVerPublicacion() throws Exception {
        given(publicacionServicio.obtenerPorId(1)).willReturn(publicacionDto);
        String json = new ObjectMapper().writeValueAsString(publicacionDto);
        mockMvc.perform(get(PUBLICACIONES + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(content().json(json))
                .andExpect(jsonPath("$.titulo").value("Titulo"));
    }

    @Test
    void testGuardarPublicacion() throws Exception {
        String json = new ObjectMapper().writeValueAsString(publicacionDto);
        mockMvc.perform(post(PUBLICACIONES)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void testActualizarPublicacion() throws Exception {
        String json = new ObjectMapper().writeValueAsString(publicacionDto);
        mockMvc.perform(put(PUBLICACIONES + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void testEliminarPublicacion() throws Exception {
        mockMvc.perform(delete(PUBLICACIONES + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
