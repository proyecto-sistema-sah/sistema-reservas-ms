package com.sistema.sah.reservas.service;

import com.sistema.sah.commons.dto.RespuestaGeneralDto;
import com.sistema.sah.commons.dto.TipoCuartoDto;

import java.util.List;

/**
 * Servicio para gestionar consultas relacionadas con tipos de cuartos.
 * <p>
 * Define el contrato para obtener información sobre los diferentes tipos de cuartos disponibles en el sistema.
 * </p>
 */
public interface IQueryTipoCuartoService {

    /**
     * Obtiene un listado de todos los tipos de cuartos disponibles.
     *
     * @return un objeto {@link RespuestaGeneralDto} que incluye el estado, el mensaje, y la lista
     * de tipos de cuartos ({@link TipoCuartoDto}) disponibles.
     */
    RespuestaGeneralDto findAllTipoCuarto();

}
