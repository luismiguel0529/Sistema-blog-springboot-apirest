package com.sistema.blog.excepcions;

import org.springframework.http.HttpStatus;

public class BlogAppException extends RuntimeException {

    private HttpStatus status;
    private String mensaje;

    public BlogAppException(HttpStatus status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
    }

    public BlogAppException(String message, HttpStatus status, String mensaje) {
        super(message);
        this.status = status;
        this.mensaje = mensaje;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
