package com.purrComplexity.TrabajoYa.exception;

public class EmpleadorWithTheSameCorreo extends RuntimeException {
    public EmpleadorWithTheSameCorreo(String message) {
        super(message);
    }

    public EmpleadorWithTheSameCorreo(){
        super("El correo ya se encuentra registado, pruebe otro");
    }
}
