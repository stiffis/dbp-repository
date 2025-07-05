package com.purrComplexity.TrabajoYa.exception;

public class ContratoNotInEmpleador extends RuntimeException {
    public ContratoNotInEmpleador(String message) {
        super(message);
    }
    public ContratoNotInEmpleador(){
        super("El contrato no fue publicado por el empleador con el que se autentica");
    }
}
