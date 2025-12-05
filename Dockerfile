# EcoVibeB/Dockerfile

# Imagen base con Java 17
FROM openjdk:17.0.2-jdk-oracle

# Zona horaria
ENV TZ=America/Lima

# El JAR que se ha generado con Maven
ARG JAR_FILE=target/*.jar

# Copiamos el JAR dentro de la imagen
COPY ${JAR_FILE} backend.jar

# Puerto interno donde escucha Spring Boot
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/backend.jar"]
