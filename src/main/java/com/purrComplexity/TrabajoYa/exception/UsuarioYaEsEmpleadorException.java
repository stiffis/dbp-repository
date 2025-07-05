package com.purrComplexity.TrabajoYa.exception;

public class UsuarioYaEsEmpleadorException extends RuntimeException {
    public UsuarioYaEsEmpleadorException(String message) {
        super(message);
    }
    public UsuarioYaEsEmpleadorException(){
        super("El usuario ya tiene asociado una cuenta de Empleador");
    }
}
