package com.sistema.sah.reservas.service;

import com.sistema.sah.commons.dto.ReservaDto;
import com.sistema.sah.commons.dto.RespuestaGeneralDto;
import com.sistema.sah.reservas.dto.ReservaCuartoInputDTO;

/**
 * Servicio para gestionar la creación de reservas.
 * <p>
 * Define el contrato para la creación de reservas de cuartos en el sistema.
 * </p>
 */
public interface ICrearReservaService {

    /**
     * Crea una nueva reserva basada en los datos proporcionados.
     *
     * @param reservaDto objeto {@link ReservaCuartoInputDTO} que contiene los detalles de la reserva.
     * @return un objeto {@link RespuestaGeneralDto} que incluye el estado, el mensaje, y los datos
     * del resultado de la operación.
     */
    RespuestaGeneralDto crearReserva(ReservaCuartoInputDTO reservaDto);

}
