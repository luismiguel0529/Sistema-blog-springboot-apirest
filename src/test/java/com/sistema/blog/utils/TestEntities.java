package com.sistema.blog.utils;

import com.sistema.blog.dto.comentarios.ComentarioDto;
import com.sistema.blog.dto.publicaciones.PublicacionDto;
import com.sistema.blog.dto.publicaciones.PublicacionRespuesta;
import com.sistema.blog.entitys.publicaciones.Publicacion;

public class TestEntities {

    public static PublicacionRespuesta publicacionRespuesta() {
        return new PublicacionRespuesta();
    }

    public static PublicacionDto publicacionDto() {
        PublicacionDto publicacionDto = new PublicacionDto();
        publicacionDto.setId(1L);
        publicacionDto.setTitulo("Titulo");
        publicacionDto.setDescripcion("Descripcion");
        publicacionDto.setContenido("Contenido");
        return publicacionDto;
    }

    public static Publicacion publicacion() {
        Publicacion publicacion = new Publicacion();
        publicacion.setId(1L);
        publicacion.setTitulo("Titulo");
        publicacion.setDescripcion("Descripcion");
        publicacion.setContenido("Contenido");

        return publicacion;
    }


    public static ComentarioDto comentarioDto() {
        ComentarioDto comentarioDto = new ComentarioDto();
        comentarioDto.setNombre("Nombre");
        comentarioDto.setEmail("email@email.com");
        comentarioDto.setCuerpo("este es el cuerpo del comentario");
        return comentarioDto;
    }
}
