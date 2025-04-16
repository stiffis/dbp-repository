# PostgreSQL
PostgreSQL es un sistema de gestión de bases de datos relacional y objeto-relacional, que utiliza el lenguaje SQL (Structured Query Language) para la gestión de datos. Es un sistema de código abierto, lo que significa que su código fuente es accesible y puede ser modificado por cualquier persona. PostgreSQL es conocido por su robustez, escalabilidad y soporte para una amplia variedad de tipos de datos y funciones avanzadas.

# Comandos basicos de PostgreSQL
Comandos en la terminal para interactuar con PostgreSQL:

## Conexión e Identificación

| Comando   | Descripción    |
|--------------- | --------------- |
| psql -h localhost -p 5432 -U username -d database_name   | Usado para conectarse a una base de datos especifica.   |
| psql -U postgres   | Usado para conectarse a la base de datos por defecto.   |
| Item1.3   | Item2.3   |
| Item1.4   | Item2.4   |

```bash
psql -U postgres
```


```bash
psql "postgresql://username:password@localhost:5432/database_name"
```
Usado para conectarse a una base de datos especifica con un URI.

```bash
\c database_name
```
Usado para cambiar de base de datos.

```bash
\conninfo
```
Usado para mostrar información de conexión actual.

```bash
\q
```
Salir de PostgreSQL.

## Navegación
```bash
\l
```
Listar todas las bases de datos.

```bash
\du
```
Listar todos los roles de usuario.

```bash
\dt
```
Listar todas las tablas de la base de datos actual.

```bash
\d table_name
```
Mostrar la estructura de una tabla específica.

```bash


