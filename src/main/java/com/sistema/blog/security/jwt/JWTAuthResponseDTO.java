package com.sistema.blog.security.jwt;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JWTAuthResponseDTO {

    private String tokenAcceso;
    private String tipoDeToken = "Bearer";

    public JWTAuthResponseDTO(String tokenAcceso) {
        this.tokenAcceso = tokenAcceso;
    }
}
