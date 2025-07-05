package com.purrComplexity.TrabajoYa.exception;

public class PostulacionDuplicadaException extends RuntimeException {
    public PostulacionDuplicadaException(String message) {
        super(message);
    }
    public PostulacionDuplicadaException() {
        super("Ya has postulado a esta oferta de empleo.");
    }
}
