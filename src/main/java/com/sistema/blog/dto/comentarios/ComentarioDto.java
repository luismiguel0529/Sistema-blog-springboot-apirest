package com.sistema.blog.dto.comentarios;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ComentarioDto {

    public static final String regexEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private long id;

    @NotEmpty(message = "El nombre no debe ser vacio o nulo")
    private String nombre;

    @Email(regexp = regexEmail)
    @NotEmpty(message = "El email no debe ser vacio o nulo")
    private String email;

    @NotEmpty(message = "El cuerpo no debe ser vacio o nulo")
    @Size(min = 10, message = "El cuerpo del comentario debe tener minimo 10 caracteres")
    private String cuerpo;
}
