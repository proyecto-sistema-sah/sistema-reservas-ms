package com.sistema.sah.reservas.service;

import com.sistema.sah.commons.dto.ReservaDto;
import com.sistema.sah.commons.dto.RespuestaGeneralDto;
import com.sistema.sah.reservas.dto.ReservaCuartoInputDTO;

public interface ICrearReservaService {

    RespuestaGeneralDto crearReserva(ReservaCuartoInputDTO reservaDto);

}
