package com.sistema.blog.controller.comentarios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.blog.dto.comentarios.ComentarioDto;
import com.sistema.blog.dto.publicaciones.PublicacionDto;
import com.sistema.blog.service.comentarios.ComentarioServicio;
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

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ComentarioController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ComentarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComentarioServicio comentarioServicio;

    private static ComentarioDto comentarioDto;

    private static final String COMENTARIOS = "/api/publicaciones/1/comentarios";

    @BeforeAll
    static void setUp() {
        comentarioDto = TestEntities.comentarioDto();
    }

    @Test
    void testVerComentarios() throws Exception {
        given(comentarioServicio.obtenerComentariosByPublicacionId(1)).willReturn(Collections.singletonList(comentarioDto));
        mockMvc.perform(get(COMENTARIOS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testVerPublicacion() throws Exception {
        given(comentarioServicio.obtenerComentarioById(1L, 1L)).willReturn(comentarioDto);
        String json = new ObjectMapper().writeValueAsString(comentarioDto);
        mockMvc.perform(get(COMENTARIOS + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(content().json(json))
                .andExpect(jsonPath("$.nombre").value("Nombre"));
    }

    @Test
    void testGuardarComentario() throws Exception {
        String json = new ObjectMapper().writeValueAsString(comentarioDto);
        mockMvc.perform(post(COMENTARIOS)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void testActualizarComentario() throws Exception {
        //given(publicacionServicio.actualizarPublicacion(publicacionDto, 1)).willReturn(publicacionDto);
        String json = new ObjectMapper().writeValueAsString(comentarioDto);
        mockMvc.perform(put(COMENTARIOS + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void testEliminarComentario() throws Exception {
        mockMvc.perform(delete(COMENTARIOS + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
