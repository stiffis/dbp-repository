package com.purrComplexity.TrabajoYa.exception;

public class UsuarioNoEsEmpleadorException extends RuntimeException {
    public UsuarioNoEsEmpleadorException(String message) {
        super(message);
    }
    public UsuarioNoEsEmpleadorException() {
        super("El usuario no ha creado una cuenta como empleador");
    }
}
