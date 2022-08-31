package com.sistema.blog.controller.comentarios;

import com.sistema.blog.dto.comentarios.ComentarioDto;
import com.sistema.blog.service.comentarios.ComentarioServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ComentarioController {

    private final ComentarioServicio comentarioServicio;

    public ComentarioController(ComentarioServicio comentarioServicio) {
        this.comentarioServicio = comentarioServicio;
    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<List<ComentarioDto>> listComentariosByPublicacionId(@PathVariable(value = "publicacionId") Long publicacionId) {
        return new ResponseEntity<>(comentarioServicio.obtenerComentariosByPublicacionId(publicacionId), HttpStatus.OK);
    }

    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDto> saveComentario(
            @PathVariable(value = "publicacionId") Long publicacionId,
            @Valid @RequestBody ComentarioDto comentarioDto) {
        return new ResponseEntity<>(comentarioServicio.crearComentario(publicacionId, comentarioDto), HttpStatus.CREATED);
    }


    @GetMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDto> obtenerComentarioPorId(
            @PathVariable(value = "publicacionId") Long publicacionId,
            @PathVariable(value = "id") long comentarioId) {
        return new ResponseEntity<>(comentarioServicio.obtenerComentarioById(publicacionId, comentarioId), HttpStatus.OK);
    }

    @PutMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDto> actualizarComentario(
            @PathVariable(value = "publicacionId") Long publicacionId,
            @PathVariable(value = "id") Long comentarioId,
            @Valid @RequestBody ComentarioDto comentarioDto) {
        return new ResponseEntity<>(comentarioServicio.actualizarComentario(publicacionId, comentarioId, comentarioDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<String> eliminarCOmentario(
            @PathVariable Long publicacionId,
            @PathVariable Long id) {
        comentarioServicio.eliminarComentario(publicacionId, id);
        return new ResponseEntity<>("Comentario Eliminado", HttpStatus.OK);
    }
}
