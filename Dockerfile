# Usa una imagen base ligera de OpenJDK 17
FROM openjdk:17-jdk-slim

# Instala las dependencias necesarias
RUN apt-get update && apt-get install -y libfreetype6 libfreetype6-dev

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado al contenedor
COPY target/reservas-0.0.1-SNAPSHOT.jar reservas-ms.jar

# Expone el puerto 8002 (o el puerto que usará tu aplicación)
EXPOSE 8002

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "reservas-ms.jar"]
