
# Guía para desplegar una aplicación Spring Boot en AWS EC2

Esta guía te explica paso a paso cómo subir y ejecutar tu aplicación Spring Boot en una instancia EC2 de AWS.

---

## 1. Empaquetar la aplicación

En tu proyecto local, ejecuta:

```bash
./mvnw clean package
```

o si usas Maven directamente:

```bash
mvn clean package
```

Esto generará un archivo JAR ejecutable en la carpeta `target/`, por ejemplo: `target/tu-app.jar`.

---

## 2. Crear una instancia EC2 en AWS

1. Ingresa a la consola AWS y ve a **EC2** → **Launch Instance**.
2. Selecciona una imagen (AMI) como **Amazon Linux 2** o **Ubuntu Server 20.04 LTS**.
3. Elige un tipo de instancia, por ejemplo, **t2.micro** (gratis dentro del Free Tier).
4. Configura el almacenamiento predeterminado o ajusta según tus necesidades.
5. En el grupo de seguridad (Security Group):
   - Abre el puerto **22** para SSH.
   - Abre el puerto **8080** (o el puerto que use tu app) para acceder a la aplicación.
6. Crea o usa una **Key Pair** para acceso SSH y descárgala (archivo `.pem`).

---

## 3. Conectarse a la instancia vía SSH

Desde tu terminal local, ejecuta:

```bash
ssh -i /ruta/a/tu-key.pem ec2-user@ip-publica-ec2
```

> Nota: Si usas Ubuntu, el usuario puede ser `ubuntu` en lugar de `ec2-user`.

---

## 4. Instalar Java en la instancia

### Para Amazon Linux 2:

```bash
sudo yum update -y
sudo yum install java-17-amazon-corretto -y
```

### Para Ubuntu:

```bash
sudo apt update
sudo apt install openjdk-17-jdk -y
```

Verifica la instalación:

```bash
java -version
```

---

## 5. Subir el archivo JAR a la instancia

Desde tu terminal local, ejecuta:

```bash
scp -i /ruta/a/tu-key.pem target/tu-app.jar ec2-user@ip-publica-ec2:~
```

---

## 6. Ejecutar la aplicación en la instancia

Conéctate por SSH a la instancia (si no estás conectado) y ejecuta:

```bash
java -jar tu-app.jar
```

La aplicación debería iniciar y escuchar en el puerto 8080 (o el que configures).

---

## 7. Acceder a la aplicación

En tu navegador, abre:

```
http://ip-publica-ec2:8080
```

Deberías ver tu aplicación funcionando.

---

## Recomendaciones adicionales

- Para ejecutar la app en segundo plano, puedes usar `nohup` o `screen`:

```bash
nohup java -jar tu-app.jar &
```

- Considera configurar un proxy reverso (nginx) y HTTPS para producción.
- Para despliegues más profesionales, revisa AWS Elastic Beanstalk o contenedores Docker con ECS.

---
