package com.purrComplexity.TrabajoYa;

import com.purrComplexity.TrabajoYa.Empleador.Exceptions.EmpleadorNotFound;
import com.purrComplexity.TrabajoYa.Empleador.Exceptions.EmpleadorWithTheSameCorreo;
import com.purrComplexity.TrabajoYa.Empleador.Exceptions.EmpleadorWithTheSameRUC;
import com.purrComplexity.TrabajoYa.Persona.Exceptions.PersonaNotFound;
import com.purrComplexity.TrabajoYa.Persona.Exceptions.PersonaWithSameCorreo;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmpleadorNotFound.class)
    public ResponseEntity<ErrorMessage> handleEmpleadorNotFoundException(EmpleadorNotFound ex){
        ErrorMessage error=new ErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmpleadorWithTheSameRUC.class)
    public ResponseEntity<ErrorMessage> handleEmpleadorWithTheSameRucException(EmpleadorWithTheSameRUC ex){
        ErrorMessage error=new ErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(PersonaWithSameCorreo.class)
    public ResponseEntity<ErrorMessage> handelPersonaWithSameCorreo(PersonaWithSameCorreo ex){
        ErrorMessage errorMessage=new ErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    @ExceptionHandler(PersonaNotFound.class)
    public ResponseEntity<ErrorMessage> handlePersonaNotFound(PersonaNotFound ex){
        ErrorMessage errorMessage=new ErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
    @ExceptionHandler(EmpleadorWithTheSameCorreo.class)
    public ResponseEntity<ErrorMessage>handelEmpleadorWithTheSameCorreo(EmpleadorWithTheSameCorreo ex){
        ErrorMessage errorMessage=new ErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
