package com.sistema.blog.controller.publicaciones;

import com.sistema.blog.dto.publicaciones.PublicacionDto;
import com.sistema.blog.dto.publicaciones.PublicacionRespuesta;
import com.sistema.blog.service.publicaciones.PublicacionServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.sistema.blog.utils.AppConstantes.*;

@RestController
@RequestMapping("/api")
public class PublicacionController {

    private static final Logger logger = LoggerFactory.getLogger(PublicacionController.class);

    private final PublicacionServicio publicacionServicio;

    public PublicacionController(PublicacionServicio publicacionServicio) {
        this.publicacionServicio = publicacionServicio;
    }

    @GetMapping("/publicaciones")
    public ResponseEntity<PublicacionRespuesta> listPublicaciones(
            @RequestParam(value = "pageNo", defaultValue = PAGENO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PAGESIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = SORTBY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = SORTDIR, required = false) String sortDir) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        logger.info("Datos del Usuario= {} ", auth.getPrincipal());
//        logger.info("Datos de los permisos = {} ", auth.getAuthorities());
//        logger.info("Estas autenticado = {} ", auth.isAuthenticated());
        return new ResponseEntity<>(publicacionServicio.obtenerPublicaciones(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/publicaciones/{id}")
    public ResponseEntity<PublicacionDto> obtenerPorId(@PathVariable long id) {
        return new ResponseEntity<>(publicacionServicio.obtenerPorId(id), HttpStatus.OK);
    }

    @PostMapping("/publicaciones")
    public ResponseEntity<PublicacionDto> savePublicacion(@Valid @RequestBody PublicacionDto publicacionDto) {
        return new ResponseEntity<>(publicacionServicio.crearPublicacion(publicacionDto), HttpStatus.CREATED);
    }

    @PutMapping("/publicaciones/{id}")
    public ResponseEntity<PublicacionDto> actualizarPublicacion(@Valid @RequestBody PublicacionDto publicacionDto, @PathVariable Long id) {
        return new ResponseEntity<>(publicacionServicio.actualizarPublicacion(publicacionDto, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/publicaciones/{id}")
    public ResponseEntity<String> deletePublicacion(@PathVariable long id) {
        publicacionServicio.eliminarPublicacion(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
