package com.purrComplexity.TrabajoYa.exception;

public class UsuarioYaEsTrabajadorException extends RuntimeException {
    public UsuarioYaEsTrabajadorException(String message) {
        super(message);
    }

    public UsuarioYaEsTrabajadorException(){
        super("El usuario ya tiene asociado una cuenta de Trabajador");
    }
}
