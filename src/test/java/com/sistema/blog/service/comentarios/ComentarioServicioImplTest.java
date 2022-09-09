package com.sistema.blog.service.comentarios;

import com.sistema.blog.dto.comentarios.ComentarioDto;
import com.sistema.blog.entitys.publicaciones.Publicacion;
import com.sistema.blog.repository.ComentarioRepository;
import com.sistema.blog.repository.PublicacionRepository;
import com.sistema.blog.utils.TestEntities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class ComentarioServicioImplTest {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    private ComentarioServicioImpl comentarioServicio;

    private Publicacion publicacion;

    private ComentarioDto comentarioDto;

    @BeforeEach
    void setUp() {
        publicacion = TestEntities.publicacion();
        comentarioDto = TestEntities.comentarioDto();
        ModelMapper modelMapper = new ModelMapper();
        comentarioServicio = new ComentarioServicioImpl(modelMapper, comentarioRepository, publicacionRepository);
    }

    @Test
    void crearComentario() {
        Publicacion publicacionSave = publicacionRepository.save(publicacion);
        ComentarioDto comentario = comentarioServicio.crearComentario(publicacionSave.getId(), comentarioDto);
        Assertions.assertEquals(comentario.getNombre(), comentarioDto.getNombre());

    }

    @Test
    void obtenerComentariosByPublicacionId() {
        Publicacion publicacionSave = publicacionRepository.save(publicacion);
        comentarioServicio.crearComentario(publicacionSave.getId(), comentarioDto);
        List<ComentarioDto> comentarios = comentarioServicio.obtenerComentariosByPublicacionId(publicacionSave.getId());
        Assertions.assertEquals(comentarios.get(0).getNombre(), "Nombre");
    }

    @Test
    void obtenerComentarioById() {
        Publicacion publicacionSave = publicacionRepository.save(publicacion);
        ComentarioDto comentario = comentarioServicio.crearComentario(publicacionSave.getId(), comentarioDto);
        ComentarioDto getComentario = comentarioServicio.obtenerComentarioById(publicacionSave.getId(), comentario.getId());
        Assertions.assertEquals(getComentario.getNombre(), "Nombre");
    }

    @Test
    void actualizarComentario() {
        Publicacion publicacionSave = publicacionRepository.save(publicacion);
        ComentarioDto comentarioSave = comentarioServicio.crearComentario(publicacionSave.getId(), comentarioDto);
        ComentarioDto comentarioUpdate = comentarioServicio.actualizarComentario(publicacionSave.getId(), comentarioSave.getId(), comentarioDto);
        Assertions.assertEquals(comentarioUpdate.getNombre(), comentarioDto.getNombre());
    }


    @Test
    void eliminarComentario() {
        Publicacion publicacionSave = publicacionRepository.save(publicacion);
        ComentarioDto comentarioSave = comentarioServicio.crearComentario(publicacionSave.getId(), comentarioDto);
        comentarioServicio.eliminarComentario(publicacionSave.getId(), comentarioSave.getId());
    }
}
