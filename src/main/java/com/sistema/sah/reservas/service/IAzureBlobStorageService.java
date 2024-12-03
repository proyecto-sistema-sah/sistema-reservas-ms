package com.sistema.sah.reservas.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface IAzureBlobStorageService {

    String uploadFile(String nombreArchivo,byte[] byteArchivo);

    InputStream buscarArchivo(String nombreArchivo);

}
