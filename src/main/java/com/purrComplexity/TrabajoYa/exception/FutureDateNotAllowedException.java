package com.purrComplexity.TrabajoYa.exception;

import java.util.Date;

public class FutureDateNotAllowedException extends RuntimeException {
    public FutureDateNotAllowedException(Date fechaCreacion) {
        super("El contrato tiene fecha futura de creación. " + fechaCreacion + ". La Fecha máxima valida es de hoy.") ;
    }
}
