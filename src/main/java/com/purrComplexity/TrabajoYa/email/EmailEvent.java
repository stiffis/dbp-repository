package com.purrComplexity.TrabajoYa.email;


import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class EmailEvent extends ApplicationEvent {
    private final String email;
    private final String tipo;

    public EmailEvent(String email, String tipo) {
        super(email);
        this.email = email;
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public String getTipo() {
        return tipo;
    }
}
