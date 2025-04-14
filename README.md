# dbp-repository

```
src/main/java/com/example/demo/
├── controller/
│   └── CalculadoraController.java
├── domain/
│   └── Calculadora.java
├── exceptions/
│   ├── global/
│   │   ├── ErrorMessage.java
│   │   └── GlobalExceptionHandler.java
│   └── specific/
│       └── HuanaException.java
├── repository/
│   └── CalculadoraRepository.java
└── service/
    └── CalculadoraService.java
```

controller: Es la capa encargada de recibir las peticiones HTTP y devolver las respuestas. En este caso, la clase `CalculadoraController` maneja las operaciones de la calculadora.

domain: Contiene la clase `Calculadora`, que representa el modelo de datos de la calculadora. Esta clase contiene los atributos y métodos necesarios para realizar las operaciones matemáticas.
exceptions: Contiene las clases de excepciones personalizadas. La clase `HuanaException` es una excepción específica que se lanza cuando se produce un error en la calculadora. La clase `GlobalExceptionHandler` maneja las excepciones globales y devuelve un mensaje de error adecuado.

repository: Contiene la interfaz `CalculadoraRepository`, que define los métodos para realizar operaciones matemáticas. Esta interfaz es implementada por la clase `CalculadoraService`.

service: Contiene la clase `CalculadoraService`, que implementa la lógica de negocio de la calculadora. Esta clase utiliza la interfaz `CalculadoraRepository` para realizar las operaciones matemáticas.

utils: Contiene la clase `ValidationUtils`, que proporciona métodos de utilidad para validar los datos de entrada. Esta clase se utiliza en el controlador y en el servicio para asegurarse de que los datos sean válidos antes de realizar las operaciones.

# Plan de Trabajo

1. Definir el Alcance y los Objetivos del Proyecto

    Identificar el tipo de proyecto: ¿Es una aplicación web, un microservicio, una API REST, etc.?
    Establecer los objetivos principales: Define qué problema resolverá o qué funcionalidades básicas deberá tener el proyecto.
    Seleccionar la tecnología: Escoge tecnologías como Spring Boot (si es un backend), bases de datos, frameworks front-end, etc.

2. Organizar la Estructura del Proyecto

    Configurar el repositorio: Crea un repositorio en GitHub u otra plataforma de control de versiones.
    Definir la estructura inicial: Separa carpetas como controller, service, repository, domain, y exceptions (como en el ejemplo anterior).
    Crear un roadmap: Divide el trabajo en tareas específicas, establece hitos y fija plazos.

3. Pasos Adicionales

    Recopila requisitos detallados del sistema.
    Configura las herramientas de desarrollo (IDE, sistema de construcción como Maven o Gradle, y bases de datos).
    Comienza con una funcionalidad mínima viable para garantizar un inicio rápido y tangible.

# Pasos en la Implementación
1. Controller:
- Propósito: Contiene las clases que manejan las solicitudes HTTP(GET, POST, PUT, DELETE) en los endpoints de la API. Aquí se definen la logica de entrada y salida de datos hacia el cliente.
- Ejemplo: `<name>Controller.java` (PascalCase)
(Template)
```java
package com.example.demo.controller;
import com.example.demo.domain.Calculadora;
import com.example.demo.service.CalculadoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/calculadora")
public class CalculadoraController {
    @Autowired
    private CalculadoraService calculadoraService;
    @GetMapping("/suma")
    public ResponseEntity<Integer> suma(@RequestParam int a, @RequestParam int b) {
        return ResponseEntity.ok(calculadoraService.suma(a, b));
    }
    @GetMapping("/resta")
    public ResponseEntity<Integer> resta(@RequestParam int a, @RequestParam int b) {
        return ResponseEntity.ok(calculadoraService.resta(a, b));
    }
}
```


2. Service:
- Propósito: Contiene la lógica de negocio del proyecto. Se separa las reglas del negocio de los controladores y repositorios.
- Ejemplo: `<name>Service.java` (PascalCase)
(Template)
```java
package com.example.demo.service;
import com.example.demo.domain.Calculadora;
import com.example.demo.repository.CalculadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CalculadoraService {
    @Autowired
    private CalculadoraRepository calculadoraRepository;
    public int suma(int a, int b) {
        return a + b;
    }
    public int resta(int a, int b) {
        return a - b;
    }
}
```

3. Repository:
- Propósito: Contiene las interfaces que interactúan directamente con la vase de datos mediante JPA. 
- Ejemplo: `<name>Repository.java` (PascalCase)
(Template)
```java
package com.example.demo.repository;
import com.example.demo.domain.Calculadora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CalculadoraRepository extends JpaRepository<Calculadora, Long> {
    // Aquí puedes definir métodos personalizados si es necesario
}
```

4. Domain:
- Propósito: Contiene las clases que representan las entidades del modelo de dominio, que generalmente se mapean a tablas en la base de datos.
- Ejemplo: `<name>.java` (PascalCase)
(Template)
```java
package com.example.demo.domain;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
@Entity
@Table(name = "calculadora")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calculadora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int a;
    private int b;
}
```
5. Exceptions:
- Propósito: Contiene las clases que manejan las excepciones personalizadas y globales. También incluye clases de respuesta para informar errores al cliente.
- Ejemplo: `<name>Exception.java` (PascalCase)
(Template)(ErrorMessage.java)
```java
package com.example.demo.exceptions.global;
import com.example.demo.exceptions.specific.HuanaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(HuanaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleHuanaException(HuanaException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
```
(Template)(HuanaException.java)
```java
package com.example.demo.exceptions.specific;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HuanaException extends RuntimeException {
    public HuanaException(String message) {
        super(message);
    }
}
```


