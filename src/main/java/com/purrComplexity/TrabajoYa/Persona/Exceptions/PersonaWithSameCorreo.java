package com.purrComplexity.TrabajoYa.Persona.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PersonaWithSameCorreo extends RuntimeException {
    public PersonaWithSameCorreo(String message) {
        super(message);
    }
}
