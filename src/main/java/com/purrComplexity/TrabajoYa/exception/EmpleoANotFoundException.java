package com.purrComplexity.TrabajoYa.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmpleoANotFoundException extends RuntimeException {
    public EmpleoANotFoundException(Long id) {
        super( "No se encontró el empleo con ID: " + id);
    }
}
