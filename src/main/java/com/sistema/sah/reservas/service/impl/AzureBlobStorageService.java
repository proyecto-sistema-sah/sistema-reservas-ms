package com.sistema.sah.reservas.service.impl;

import com.azure.core.util.BinaryData;
import com.azure.core.util.Context;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlobStorageException;
import com.azure.storage.blob.specialized.BlobClientBase;
import com.sistema.sah.reservas.service.IAzureBlobStorageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class AzureBlobStorageService implements IAzureBlobStorageService {

    @Value("${azure.urlcompleta}")
    private String containerUrl;

    private BlobContainerClient blobContainerClient;

    @PostConstruct
    private void initializeBlobClient() {
        this.blobContainerClient = new BlobContainerClientBuilder()
                .endpoint(containerUrl)
                .buildClient();
    }

    @Override
    public String uploadFile(String nombreArchivo, byte[] byteArchivo) {
        try {
            // Crear un cliente de blob
            String archivoConExtension = nombreArchivo.endsWith(".pdf") ? nombreArchivo : nombreArchivo + ".pdf";
            BlobClient blobClient = blobContainerClient.getBlobClient(archivoConExtension);

            // Subir el archivo
            blobClient.upload(BinaryData.fromBytes(byteArchivo), true);

            log.info("Archivo subido exitosamente: {}", blobClient.getBlobUrl());
            return blobClient.getBlobUrl();
        } catch (BlobStorageException e) {
            log.error("Error de Azure Blob Storage al subir el archivo: {}", e.getMessage());
            throw new RuntimeException("Error de Azure Blob Storage: " + e.getErrorCode(), e);
        } catch (Exception e) {
            log.error("Error inesperado al subir el archivo: {}", e.getMessage());
            throw new RuntimeException("Error inesperado al subir el archivo", e);
        }
    }

    @Override
    public InputStream buscarArchivo(String nombreArchivo) {
        try {
            // Contexto para depuración de firma (deshabilitar en producción)
            Context context = new Context("Azure-Storage-Log-String-To-Sign", true);
            // Crear un cliente para el blob específico
            BlobClientBase blobClient = blobContainerClient.getBlobClient(nombreArchivo);
            // Comprobar existencia del archivo
            blobClient.existsWithResponse(null, context);
            // Verificar si el archivo existe antes de intentar descargarlo
            if (!blobClient.exists()) {
                throw new RuntimeException("El archivo " + nombreArchivo + " no existe en Azure Blob Storage.");
            }
            // Retornar el InputStream del archivo
            return blobClient.openInputStream();
        } catch (Exception e) {
            // Manejo genérico de errores
            throw new RuntimeException("Error inesperado al buscar el archivo en Azure Blob Storage", e);
        }
    }
}
