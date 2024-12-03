# Usa una imagen base de JDK para ejecutar el JAR
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado al contenedor
COPY target/reservas-0.0.1-SNAPSHOT.jar reservas-ms.jar

# Crea el directorio para la configuración de AWS CLI
RUN mkdir -p /root/.aws

# Copia los archivos de configuración de AWS al contenedor
# Asegúrate de tener estos archivos en el mismo nivel que tu Dockerfile
COPY aws_credentials /root/.aws/credentials
COPY aws_config /root/.aws/config

# Asegúrate de que los permisos sean correctos
RUN chmod 600 /root/.aws/credentials && chmod 600 /root/.aws/config

# Expone el puerto 8002
EXPOSE 8002

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "reservas-ms.jar"]
