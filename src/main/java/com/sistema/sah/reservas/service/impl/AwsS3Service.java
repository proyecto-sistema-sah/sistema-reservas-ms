package com.sistema.sah.reservas.service.impl;

import com.sistema.sah.reservas.dto.RespuestaS3DTO;
import com.sistema.sah.reservas.service.IAwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
@RequiredArgsConstructor
@Slf4j
public class AwsS3Service implements IAwsS3Service {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private final S3Client s3Client;

    /**
     * Sube un archivo a Amazon S3.
     *
     * @param nombreArchivo Nombre del archivo.
     * @param byteArchivo   Contenido del archivo en bytes.
     * @return URL del archivo subido.
     */
    public String uploadFile(String nombreArchivo, byte[] byteArchivo) {
        try {
            String archivoConExtension = nombreArchivo.endsWith(".pdf") ? nombreArchivo : nombreArchivo + ".pdf";

            // Configurar la solicitud para subir el archivo
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(archivoConExtension)
                    .build();

            // Subir el archivo
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(byteArchivo));

            String fileUrl = String.format("https://%s.s3.amazonaws.com/%s", bucketName, archivoConExtension);
            log.info("Archivo subido exitosamente: {}", fileUrl);

            return fileUrl;
        } catch (S3Exception e) {
            log.error("Error al subir el archivo a S3: {}", e.awsErrorDetails().errorMessage());
            throw new RuntimeException("Error al subir el archivo a S3: " + e.awsErrorDetails().errorMessage(), e);
        } catch (Exception e) {
            log.error("Error inesperado al subir el archivo: {}", e.getMessage());
            throw new RuntimeException("Error inesperado al subir el archivo", e);
        }
    }

    /**
     * Busca y obtiene un archivo desde Amazon S3.
     *
     * @param nombreArchivo Nombre del archivo.
     * @return InputStream del archivo descargado.
     */
    public InputStream buscarArchivo(String nombreArchivo) {
        try {
            // Configurar la solicitud para obtener el archivo
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(nombreArchivo)
                    .build();

            // Descargar el archivo
            InputStream responseInputStream = s3Client.getObject(getObjectRequest);

            // Retornar el DTO con la información del archivo y el InputStream
            return responseInputStream;
        } catch (S3Exception e) {
            log.error("Error al buscar el archivo en S3: {}", e.awsErrorDetails().errorMessage());
            throw new RuntimeException("Error al buscar el archivo en S3: " + e.awsErrorDetails().errorMessage(), e);
        } catch (Exception e) {
            log.error("Error inesperado al buscar el archivo: {}", e.getMessage());
            throw new RuntimeException("Error inesperado al buscar el archivo", e);
        }
    }
}
