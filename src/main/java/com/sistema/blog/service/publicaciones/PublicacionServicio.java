package com.sistema.blog.service.publicaciones;

import com.sistema.blog.dto.publicaciones.PublicacionDto;
import com.sistema.blog.dto.publicaciones.PublicacionRespuesta;


public interface PublicacionServicio {

    PublicacionDto crearPublicacion(PublicacionDto publicacionDto);

    PublicacionRespuesta obtenerPublicaciones(int pageNo, int pageSize, String sortBy, String sortDir);

    PublicacionDto obtenerPorId(long id);

    PublicacionDto actualizarPublicacion(PublicacionDto publicacionDto, long id);

    void eliminarPublicacion(long id);
}
