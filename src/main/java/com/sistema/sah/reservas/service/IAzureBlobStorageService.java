package com.sistema.sah.reservas.service;

import org.springframework.web.multipart.MultipartFile;

public interface IAzureBlobStorageService {

    String uploadFile(String nombreArchivo,byte[] byteArchivo);

    byte[] buscarArchivo(String nombreArchivo);

}
