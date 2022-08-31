package com.sistema.blog.service.comentarios;

import com.sistema.blog.dto.comentarios.ComentarioDto;
import com.sistema.blog.entitys.comentarios.Comentario;
import com.sistema.blog.entitys.publicaciones.Publicacion;
import com.sistema.blog.excepcions.BlogAppException;
import com.sistema.blog.excepcions.ResourceNotFoundException;
import com.sistema.blog.repository.ComentarioRepository;
import com.sistema.blog.repository.PublicacionRepository;
import com.sistema.blog.utils.Mappers;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.sistema.blog.utils.Mappers.mapearComentarioDtoToDAO;
import static com.sistema.blog.utils.Mappers.updateComentarioDAO;

@Service
public class ComentarioServicioImpl implements ComentarioServicio {

    private final ModelMapper modelMapper;
    private final ComentarioRepository comentarioRepository;
    private final PublicacionRepository publicacionRepository;

    public ComentarioServicioImpl(ModelMapper modelMapper, ComentarioRepository comentarioRepository, PublicacionRepository publicacionRepository) {
        this.modelMapper = modelMapper;
        this.comentarioRepository = comentarioRepository;
        this.publicacionRepository = publicacionRepository;
    }

    @Override
    public ComentarioDto crearComentario(long publicacionId, ComentarioDto comentarioDto) {

        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        Comentario comentario = mapearComentarioDtoToDAO(comentarioDto, modelMapper);
        comentario.setPublicacion(publicacion);
        Comentario nuevoComentario = comentarioRepository.save(comentario);

        return mapearComentarioToDTO(nuevoComentario);
    }

    @Override
    public List<ComentarioDto> obtenerComentariosByPublicacionId(long publicacionId) {
        return comentarioRepository.findByPublicacionId(publicacionId)
                .stream()
                .map(this::mapearComentarioToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ComentarioDto obtenerComentarioById(Long publicacionId, Long comentarioId) {
        return mapearComentarioToDTO(getComentario(publicacionId, comentarioId));
    }

    @Override
    public ComentarioDto actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDto comentarioDto) {
        return mapearComentarioToDTO(
                comentarioRepository.save(updateComentarioDAO(comentarioDto, getComentario(publicacionId, comentarioId))
                ));
    }

    @Override
    public void eliminarComentario(Long publicacionId, Long comentarioId) {
        comentarioRepository.delete(getComentario(publicacionId, comentarioId));
    }

    private Comentario getComentario(Long publicacionId, Long comentarioId) {
        return comentarioRepository.findById(comentarioId)
                .map(comentario -> {
                    Publicacion publicacion = publicacionRepository.findById(publicacionId)
                            .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
                    if (!comentario.getPublicacion().getId().equals(publicacion.getId()))
                        throw new BlogAppException(HttpStatus.BAD_REQUEST,
                                "El comentario no pertenece a la pulicacion");
                    return comentario;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));
    }

    private ComentarioDto mapearComentarioToDTO(Comentario comentario) {
        return Mappers.mapearComentarioToDTO(comentario, modelMapper);
    }
}
