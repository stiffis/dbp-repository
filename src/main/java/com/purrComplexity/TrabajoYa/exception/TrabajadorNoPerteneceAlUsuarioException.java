package com.purrComplexity.TrabajoYa.exception;

public class TrabajadorNoPerteneceAlUsuarioException extends RuntimeException {
    public TrabajadorNoPerteneceAlUsuarioException(String message) {
        super(message);
    }

    public TrabajadorNoPerteneceAlUsuarioException(){
        super("La cuenta de trabajador no le pertenece al Usuario");
    }
}
