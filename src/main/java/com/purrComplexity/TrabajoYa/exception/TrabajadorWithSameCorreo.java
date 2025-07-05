package com.purrComplexity.TrabajoYa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TrabajadorWithSameCorreo extends RuntimeException {
    public TrabajadorWithSameCorreo(String message) {
        super(message);
    }
    public TrabajadorWithSameCorreo(){
        super("El correo ya se encuentra registrado, pruebe con otro");
    }
}
