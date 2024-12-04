# Usa una imagen base ligera de OpenJDK 17
FROM openjdk:17-jdk-slim

# Establece argumentos no interactivos para evitar prompts durante la instalación
ARG DEBIAN_FRONTEND=noninteractive

# Instala las dependencias necesarias y configura la zona horaria
RUN apt-get update && apt-get install -y \
    libfreetype6 libfreetype6-dev tzdata && \
    ln -snf /usr/share/zoneinfo/America/Bogota /etc/localtime && \
    echo "America/Bogota" > /etc/timezone && \
    apt-get clean

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado al contenedor
COPY target/reservas-0.0.1-SNAPSHOT.jar reservas-ms.jar

# Expone el puerto 8002 (o el puerto que usará tu aplicación)
EXPOSE 8002

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "reservas-ms.jar"]
