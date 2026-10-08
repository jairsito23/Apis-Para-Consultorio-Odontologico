FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiamos el archivo JAR compilado por Maven dentro del contenedor
COPY target/odontologo-service-1.0.0.jar app.jar

# Exponemos el puerto que usa tu API
EXPOSE 8083

# Comando para ejecutar la aplicación cuando el contenedor inicie
ENTRYPOINT ["java", "-jar", "app.jar"]
