package com.sistema.sah.reservas.service.impl;

import com.azure.core.util.Context;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.sistema.sah.reservas.service.IAzureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


@Service
@RequiredArgsConstructor
@Slf4j
public class AzureService implements IAzureService {

    @Value("${azure.blob.sas.url}")
    private String sasUrl;

    /**
     * Sube un archivo a Azure Blob Storage.
     *
     * @param nombreArchivo Nombre del archivo.
     * @param byteArchivo   Contenido del archivo en bytes.
     * @return URL del archivo subido.
     */
    public String uploadFile(String nombreArchivo, byte[] byteArchivo) {
        try {
            String archivoConExtension = nombreArchivo.endsWith(".pdf") ? nombreArchivo : nombreArchivo + ".pdf";

            // Crear el cliente del blob
            BlobClient blobClient = new BlobClientBuilder()
                    .endpoint(sasUrl)
                    .blobName(archivoConExtension)
                    .buildClient();

            // Subir el archivo
            blobClient.upload(new ByteArrayInputStream(byteArchivo), byteArchivo.length, true);

            String fileUrl = blobClient.getBlobUrl();
            log.info("Archivo subido exitosamente: {}", fileUrl);

            return fileUrl;
        } catch (Exception e) {
            log.error("Error al subir el archivo a Blob Storage: {}", e.getMessage());
            throw new RuntimeException("Error al subir el archivo a Blob Storage", e);
        }
    }

    /**
     * Busca y obtiene un archivo desde Azure Blob Storage.
     *
     * @param nombreArchivo Nombre del archivo.
     * @return InputStream del archivo descargado.
     */
    public InputStream buscarArchivo(String nombreArchivo) {
        try {
            // Crear el cliente del blob
            BlobClient blobClient = new BlobClientBuilder()
                    .endpoint(sasUrl)
                    .blobName(nombreArchivo)
                    .buildClient();

            // Descargar el archivo como InputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            blobClient.download(outputStream);
            log.info("Archivo descargado exitosamente: {}", nombreArchivo);

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            log.error("Error al buscar el archivo en Blob Storage: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
