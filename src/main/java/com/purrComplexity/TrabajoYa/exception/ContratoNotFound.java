package com.purrComplexity.TrabajoYa.exception;

public class ContratoNotFound extends RuntimeException {
    public ContratoNotFound(String message) {
        super(message);
    }

  public ContratoNotFound() {
    super("El contrato con el ID especificado no fue encontrado");
  }
}
