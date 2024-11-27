package com.sistema.sah.reservas.service;

import com.sistema.sah.commons.dto.RespuestaGeneralDto;

public interface IConsultaReservaService {

    RespuestaGeneralDto consultarReservasUsuario(String codigoUsuario);

}
