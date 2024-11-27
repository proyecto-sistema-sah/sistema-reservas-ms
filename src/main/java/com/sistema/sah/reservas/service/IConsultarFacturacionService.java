package com.sistema.sah.reservas.service;

import com.sistema.sah.commons.dto.FacturacionDto;
import com.sistema.sah.commons.dto.RespuestaGeneralDto;

import java.util.List;

public interface IConsultarFacturacionService {

    RespuestaGeneralDto consultarFacturacionUsuario(String codigoUsuario);

}
