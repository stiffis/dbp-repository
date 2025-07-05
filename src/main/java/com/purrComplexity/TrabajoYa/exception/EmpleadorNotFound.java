package com.purrComplexity.TrabajoYa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (HttpStatus.NOT_FOUND)
public class EmpleadorNotFound extends RuntimeException {
    public EmpleadorNotFound(String message) {
        super(message);
    }

    public EmpleadorNotFound(){
        super("El empleador con el ID especificado no existe");
    }
}
