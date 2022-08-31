package com.sistema.blog.utils;

import com.sistema.blog.dto.comentarios.ComentarioDto;
import com.sistema.blog.dto.publicaciones.PublicacionDto;
import com.sistema.blog.dto.publicaciones.PublicacionRespuesta;
import com.sistema.blog.entitys.comentarios.Comentario;
import com.sistema.blog.entitys.publicaciones.Publicacion;
import org.modelmapper.ModelMapper;

import java.util.List;

public class Mappers {

    public static PublicacionDto mapearPublicacionToDTO(Publicacion publicacion, ModelMapper modelMapper) {

        return modelMapper.map(publicacion, PublicacionDto.class);
    }

    public static Publicacion mapearPublicaionDtoToDAO(PublicacionDto publicacionDto, ModelMapper modelMapper) {
        return modelMapper.map(publicacionDto, Publicacion.class);
    }

    public static Publicacion updatePublicacionDAO(PublicacionDto publicacionDto, Publicacion publicacion) {
        publicacion.setTitulo(publicacionDto.getTitulo());
        publicacion.setDescripcion(publicacionDto.getDescripcion());
        publicacion.setContenido(publicacionDto.getContenido());
        return publicacion;
    }

    public static PublicacionRespuesta mapearTOrespuesta(List<PublicacionDto> publicacionDto, int numeroPagina, int medidaPagina, Long totalElementos, int totalPaginas, boolean ultima) {

        return PublicacionRespuesta
                .builder()
                .contenido(publicacionDto)
                .medidaPagina(medidaPagina)
                .numeroPagina(numeroPagina)
                .totalElementos(totalElementos)
                .totalPaginas(totalPaginas)
                .ultima(ultima)
                .build();
    }

    public static ComentarioDto mapearComentarioToDTO(Comentario comentario, ModelMapper modelMapper) {
        return modelMapper.map(comentario, ComentarioDto.class);
    }

    public static Comentario mapearComentarioDtoToDAO(ComentarioDto comentarioDto, ModelMapper modelMapper) {
        return modelMapper.map(comentarioDto, Comentario.class);
    }

    public static Comentario updateComentarioDAO(ComentarioDto comentarioDto, Comentario comentario) {
        comentario.setNombre(comentarioDto.getNombre());
        comentario.setEmail(comentarioDto.getEmail());
        comentario.setCuerpo(comentarioDto.getCuerpo());
        return comentario;
    }
}
