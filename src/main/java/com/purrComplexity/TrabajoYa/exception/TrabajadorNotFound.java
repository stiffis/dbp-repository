package com.purrComplexity.TrabajoYa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TrabajadorNotFound extends RuntimeException {
    public TrabajadorNotFound(String message) {
        super(message);
    }

    public TrabajadorNotFound(){
        super("El trabajdor con el ID especificado no existe");
    }
}
