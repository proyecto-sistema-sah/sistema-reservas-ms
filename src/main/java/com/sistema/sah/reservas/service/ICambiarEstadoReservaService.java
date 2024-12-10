package com.sistema.sah.reservas.service;


import com.sistema.sah.commons.dto.RespuestaGeneralDto;
import com.sistema.sah.commons.helper.enums.EstadoReservaEnum;
import com.sistema.sah.reservas.dto.CambiarEstadoReservaDTO;

public interface ICambiarEstadoReservaService {

    RespuestaGeneralDto cambiarEstado(CambiarEstadoReservaDTO cambiarEstado);

}
