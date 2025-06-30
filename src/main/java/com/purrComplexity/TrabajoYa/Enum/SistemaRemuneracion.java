package com.purrComplexity.TrabajoYa.Enum;

public enum SistemaRemuneracion {
    FIJO, VARIABLE, BASEYCOMISION, HONORARIOS, OTRO;

    public static SistemaRemuneracion fromString(String text) {
        for (SistemaRemuneracion sistema : SistemaRemuneracion.values()) {
            if (sistema.name().equalsIgnoreCase(text)) {
                return sistema;
            }
        }
        return null; // or throw an exception if needed
    }
}
