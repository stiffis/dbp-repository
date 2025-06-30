package com.purrComplexity.TrabajoYa.exception;

import java.util.List;

public class RatingOutOfRangeException extends RuntimeException {
    public RatingOutOfRangeException(String message) {
        super("Las calificaciones deben estar entre 1 y 5. ");
    }
}
