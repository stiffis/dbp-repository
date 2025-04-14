# dbp-repository

tree
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
├── service/
│   └── CalculadoraService.java
└── utils/
    └── ValidationUtils.java

controller: Es la capa encargada de recibir las peticiones HTTP y devolver las respuestas. En este caso, la clase `CalculadoraController` maneja las operaciones de la calculadora.
domain: Contiene la clase `Calculadora`, que representa el modelo de datos de la calculadora. Esta clase contiene los atributos y métodos necesarios para realizar las operaciones matemáticas.
exceptions: Contiene las clases de excepciones personalizadas. La clase `HuanaException` es una excepción específica que se lanza cuando se produce un error en la calculadora. La clase `GlobalExceptionHandler` maneja las excepciones globales y devuelve un mensaje de error adecuado.
repository: Contiene la interfaz `CalculadoraRepository`, que define los métodos para realizar operaciones matemáticas. Esta interfaz es implementada por la clase `CalculadoraService`.
service: Contiene la clase `CalculadoraService`, que implementa la lógica de negocio de la calculadora. Esta clase utiliza la interfaz `CalculadoraRepository` para realizar las operaciones matemáticas.
utils: Contiene la clase `ValidationUtils`, que proporciona métodos de utilidad para validar los datos de entrada. Esta clase se utiliza en el controlador y en el servicio para asegurarse de que los datos sean válidos antes de realizar las operaciones.

