package com.purrComplexity.TrabajoYa.exception;

public class PostulanteNoEncontradoEnOfertaException extends RuntimeException {
    public PostulanteNoEncontradoEnOfertaException(String message) {
        super(message);
    }
    public PostulanteNoEncontradoEnOfertaException() {
        super("La persona no ha postulado a esta oferta de empleo.");
    }
}
