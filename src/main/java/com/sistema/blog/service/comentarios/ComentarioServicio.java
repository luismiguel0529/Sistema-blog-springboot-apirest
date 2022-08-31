package com.sistema.blog.service.comentarios;

import com.sistema.blog.dto.comentarios.ComentarioDto;

import java.util.List;

public interface ComentarioServicio {

    ComentarioDto crearComentario(long publicacionId, ComentarioDto comentarioDto);

    List<ComentarioDto> obtenerComentariosByPublicacionId(long publicacionId);

    ComentarioDto obtenerComentarioById(Long publicacionId, Long comentarioId);

    ComentarioDto actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDto comentarioDto);

    void eliminarComentario(Long publicacionId, Long comentarioId);
}
