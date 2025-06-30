package com.purrComplexity.TrabajoYa.email;


import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailListener {
    private final EmailService emailService;

    @EventListener
    @Async
    public void handleEmailEvent(EmailEvent event) {
        String subject;
        String body;

        if ("EMPLEADOR".equalsIgnoreCase(event.getTipo())) {
            subject = "Registro exitoso en TrabajoYa";
            body = "Estimado empleador,\n\nSu cuenta ha sido creada exitosamente en TrabajoYa.\n\nSaludos,\nEquipo TrabajoYa";
        } else if ("PERSONA".equalsIgnoreCase(event.getTipo())) {
            subject = "Registro exitoso en TrabajoYa";
            body = "Estimado usuario,\n\nSu cuenta ha sido creada exitosamente en TrabajoYa.\n\nSaludos,\nEquipo TrabajoYa";
        } else if ("CONTRATO".equalsIgnoreCase(event.getTipo())) {
            subject = "Nuevo Contrato Creado";
            body = "Estimado usuario,\n\nSe ha creado un nuevo contrato.\n\nSaludos,\nEquipo TrabajoYa";
        } else {
            subject = "Notificación TrabajoYa";
            body = "Estimado usuario,\n\nReciba un cordial saludo de TrabajoYa.\n\nSaludos,\nEquipo TrabajoYa";
        }

        emailService.sendSimpleMessage(event.getEmail(), subject, body);
    }
}
