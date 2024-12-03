package com.sistema.sah.reservas.service.impl;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.specialized.BlobClientBase;
import com.sistema.sah.reservas.service.IAzureBlobStorageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
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
    public String uploadFile(String nombreArchivo,byte[] byteArchivo) {
        try {
            // Crear un cliente de blob para manejar el archivo
            BlobClient blobClient = blobContainerClient.getBlobClient(nombreArchivo + ".pdf");

            // Subir el contenido al blob
            blobClient.upload(BinaryData.fromBytes(byteArchivo), true);

            // Retornar la URL del archivo subido
            return blobClient.getBlobUrl();
        } catch (Exception e) {
            throw new RuntimeException("Error al subir el archivo a Azure Blob Storage", e);
        }
    }

    @Override
    public InputStream buscarArchivo(String nombreArchivo) {
        try {
            // Crear un cliente para el blob específico
            BlobClientBase blobClient = blobContainerClient.getBlobClient(nombreArchivo);
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
