package com.sistema.blog.service.publicaciones;

import com.sistema.blog.dto.publicaciones.PublicacionDto;
import com.sistema.blog.dto.publicaciones.PublicacionRespuesta;
import com.sistema.blog.entitys.publicaciones.Publicacion;
import com.sistema.blog.excepcions.ResourceNotFoundException;
import com.sistema.blog.repository.PublicacionRepository;
import com.sistema.blog.utils.Mappers;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.sistema.blog.utils.Mappers.*;

@Service
public class PublicacionServicioImpl implements PublicacionServicio {

    private final ModelMapper modelMapper;
    private final PublicacionRepository publicacionRepository;

    public PublicacionServicioImpl(ModelMapper modelMapper, PublicacionRepository publicacionRepository) {
        this.modelMapper = modelMapper;
        this.publicacionRepository = publicacionRepository;
    }

    @Override
    public PublicacionDto crearPublicacion(PublicacionDto publicacionDto) {

        //Convertir de DTO a DAO
        Publicacion publicacion = mapearPublicaionDtoToDAO(publicacionDto, modelMapper);

        Publicacion nuevaPublicacion = publicacionRepository.save(publicacion);

        //Converir de DAO a DTO
        return mapearPublicacionToDTO(nuevaPublicacion);
    }

    @Override
    public PublicacionRespuesta obtenerPublicaciones(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Publicacion> datos = publicacionRepository.findAll(pageable);

        List<PublicacionDto> publicacionDtos = datos.stream()
                .map(this::mapearPublicacionToDTO)
                .collect(Collectors.toList());

        return mapearTOrespuesta(
                publicacionDtos,
                datos.getNumber(),
                datos.getSize(),
                datos.getTotalElements(),
                datos.getTotalPages(),
                datos.isLast()
        );
    }

    @Override
    public PublicacionDto obtenerPorId(long id) {
        return publicacionRepository.findById(id)
                .map(publicacion -> Mappers.mapearPublicacionToDTO(publicacion, modelMapper))
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
    }

    @Override
    public PublicacionDto actualizarPublicacion(PublicacionDto publicacionDto, long id) {
        return publicacionRepository.findById(id)
                .map(publicacion -> updatePublicacionDAO(publicacionDto, publicacion))
                .map(publicacionRepository::save)
                .map(this::mapearPublicacionToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
    }

    @Override
    public void eliminarPublicacion(long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
        publicacionRepository.delete(publicacion);
    }

    private PublicacionDto mapearPublicacionToDTO(Publicacion publicacion) {
        return Mappers.mapearPublicacionToDTO(publicacion, modelMapper);
    }
}
