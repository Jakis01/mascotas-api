# Usamos una imagen de Java 17 (o la versión que uses)
FROM eclipse-temurin:17-jdk-alpine

# Creamos una carpeta para la app
WORKDIR /app

# Copiamos el archivo .jar que genera Maven
COPY target/*.jar app.jar

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]