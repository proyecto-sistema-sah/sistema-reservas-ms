package com.sistema.sah.reservas.service;

import java.io.InputStream;


public interface IAzureService {

    String uploadFile(String nombreArchivo,byte[] byteArchivo);

    InputStream buscarArchivo(String nombreArchivo);

}
