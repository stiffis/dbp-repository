package com.purrComplexity.TrabajoYa.Empleador.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmpleadorWithTheSameRUC extends RuntimeException {
    public EmpleadorWithTheSameRUC(String message) {

        super(message);
    }
}
