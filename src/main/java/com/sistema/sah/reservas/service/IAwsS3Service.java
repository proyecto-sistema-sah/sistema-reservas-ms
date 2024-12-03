package com.sistema.sah.reservas.service;

import com.sistema.sah.reservas.dto.RespuestaS3DTO;

import java.io.InputStream;


public interface IAwsS3Service {

    String uploadFile(String nombreArchivo,byte[] byteArchivo);

    InputStream buscarArchivo(String nombreArchivo);

}
