package com.purrComplexity.TrabajoYa.exception;

public class EmpleoBNotFoundException extends RuntimeException {
    public EmpleoBNotFoundException(Long id) {
        super("No se encontró el empleo con ID : " + id);
    }
}
