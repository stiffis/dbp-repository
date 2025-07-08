package com.purrComplexity.TrabajoYa.exception;

public class EmpleadorNoPerteneceAlUsuarioException extends RuntimeException {
    public EmpleadorNoPerteneceAlUsuarioException(String message) {
        super(message);
    }

    public EmpleadorNoPerteneceAlUsuarioException(){
        super("La cuenta de empleador no le pertenece al Usuario");
    }
}
