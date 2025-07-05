package com.purrComplexity.TrabajoYa.exception;

public class OfertaEmpleoNoPerteneceAlEmpleadorException extends RuntimeException {
    public OfertaEmpleoNoPerteneceAlEmpleadorException(String message) {
        super(message);
    }
    public OfertaEmpleoNoPerteneceAlEmpleadorException() {
        super("No puedes realizar acciones porque usted no creó esta oferta de empleo.");
    }
}
