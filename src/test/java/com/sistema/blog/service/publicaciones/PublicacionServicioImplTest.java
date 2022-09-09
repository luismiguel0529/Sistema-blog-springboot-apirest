package com.sistema.blog.service.publicaciones;

import com.sistema.blog.dto.publicaciones.PublicacionDto;
import com.sistema.blog.dto.publicaciones.PublicacionRespuesta;
import com.sistema.blog.entitys.publicaciones.Publicacion;
import com.sistema.blog.repository.PublicacionRepository;
import com.sistema.blog.utils.TestEntities;
import org.assertj.core.api.BDDAssumptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sun.jvm.hotspot.utilities.Assert;

import static org.assertj.core.api.BDDAssumptions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DataJpaTest
@ActiveProfiles("test")
class PublicacionServicioImplTest {

    @Autowired
    private PublicacionRepository publicacionRepository;

    private PublicacionServicioImpl publicacionServicio;

    private static PublicacionDto publicacionDto;

    @BeforeEach
    void setUp(){
        ModelMapper modelMapper = new ModelMapper();
        publicacionServicio = new PublicacionServicioImpl(modelMapper, publicacionRepository);
        publicacionDto = TestEntities.publicacionDto();
    }

    @Test
    void crearPublicacion() {
        PublicacionDto publicacionGuardado = publicacionServicio.crearPublicacion(publicacionDto);
        Assertions.assertEquals(publicacionDto.getTitulo(),publicacionGuardado.getTitulo());
    }

    @Test
    void obtenerPublicacionesAsc() {
        publicacionServicio.crearPublicacion(publicacionDto);
        PublicacionRespuesta publicaciones = publicacionServicio.obtenerPublicaciones(0, 10, "id", "asc");
        Assertions.assertEquals(publicaciones.getContenido().get(0).getTitulo(),"Titulo");
    }

    @Test
    void obtenerPublicacionesDesc() {
        publicacionServicio.crearPublicacion(publicacionDto);
        PublicacionRespuesta publicaciones = publicacionServicio.obtenerPublicaciones(0, 10, "id", "desc");
        Assertions.assertEquals(publicaciones.getContenido().get(0).getTitulo(),"Titulo");
    }

    @Test
    void obtenerPorId() {
        PublicacionDto publicacion = publicacionServicio.crearPublicacion(publicacionDto);
        PublicacionDto dato = publicacionServicio.obtenerPorId(publicacion.getId());
        Assertions.assertEquals(dato.getTitulo(), publicacionDto.getTitulo());
    }

    @Test
    void actualizarPublicacion() {
        PublicacionDto publicacionSave = publicacionServicio.crearPublicacion(publicacionDto);
        PublicacionDto publicacionUpdate = publicacionServicio.actualizarPublicacion(publicacionDto, publicacionSave.getId());
        Assertions.assertEquals(publicacionUpdate.getTitulo(), publicacionDto.getTitulo());
    }

    @Test
    void eliminarPublicacion() {
        PublicacionDto publicacion = publicacionServicio.crearPublicacion(publicacionDto);
        publicacionServicio.eliminarPublicacion(publicacion.getId());
    }
}
