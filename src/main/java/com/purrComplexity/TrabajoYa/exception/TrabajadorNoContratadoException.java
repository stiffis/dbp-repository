package com.purrComplexity.TrabajoYa.exception;

public class TrabajadorNoContratadoException extends RuntimeException {
    public TrabajadorNoContratadoException(String message) {
        super(message);
    }

  public TrabajadorNoContratadoException() {
    super("El trabajador no se encuentra entre los contratados.");
  }
}
