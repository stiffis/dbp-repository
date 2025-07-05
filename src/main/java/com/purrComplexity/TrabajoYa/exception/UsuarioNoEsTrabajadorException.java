package com.purrComplexity.TrabajoYa.exception;

public class UsuarioNoEsTrabajadorException extends RuntimeException {
    public UsuarioNoEsTrabajadorException(String message) {
        super(message);
    }
    public UsuarioNoEsTrabajadorException(){
        super("El usuario no tiene asociado una cuenta de trabajdor");
    }
}
