package com.purrComplexity.TrabajoYa.exception;

public class OfertaEmpleoNotFound extends RuntimeException {
    public OfertaEmpleoNotFound(String message) {
        super(message);
    }
    public OfertaEmpleoNotFound(){
        super("La oferta de empleo no fue encontrada");
    }
}
