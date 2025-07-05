package com.purrComplexity.TrabajoYa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmpleadorWithTheSameRUC extends RuntimeException {
    public EmpleadorWithTheSameRUC(String message) {

        super(message);
    }

    public EmpleadorWithTheSameRUC(){
        super("El RUC ingresado ya se encuenta registrado, pruebe con otro");
    }
}
