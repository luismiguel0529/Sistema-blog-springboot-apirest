package com.sistema.blog.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroDto {

    private String nombre;
    private String username;
    private String email;
    private String password;
}
