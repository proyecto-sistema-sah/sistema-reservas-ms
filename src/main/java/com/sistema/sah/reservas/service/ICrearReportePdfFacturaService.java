package com.sistema.sah.reservas.service;

import net.sf.jasperreports.engine.JRException;

public interface ICrearReportePdfFacturaService {

     void generarReporte(String codigoUsuario) throws JRException;

}
