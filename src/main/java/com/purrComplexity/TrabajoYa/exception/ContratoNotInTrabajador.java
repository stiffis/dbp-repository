package com.purrComplexity.TrabajoYa.exception;

public class ContratoNotInTrabajador extends RuntimeException {
    public ContratoNotInTrabajador(String message) {
        super(message);
    }
    public ContratoNotInTrabajador(){
        super("El contrato no le pertenece al trabajador con el que se autetica");
    }
}
