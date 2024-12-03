package com.sistema.sah.reservas.service;

import com.sistema.sah.commons.dto.ReservaDto;
import net.sf.jasperreports.engine.JRException;

public interface ICrearReportePdfFacturaService {

     void generarReporte(ReservaDto reservaDto) throws JRException;

}
