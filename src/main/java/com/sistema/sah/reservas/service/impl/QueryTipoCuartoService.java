package com.sistema.sah.reservas.service.impl;

import com.sistema.sah.commons.dto.RespuestaGeneralDto;
import com.sistema.sah.commons.dto.TipoCuartoDto;
import com.sistema.sah.commons.helper.mapper.TipoCuartoMapper;
import com.sistema.sah.reservas.repository.ITipoCuartoRepository;
import com.sistema.sah.reservas.repository.IVistaTiposCuartosRepository;
import com.sistema.sah.reservas.service.IQueryTipoCuartoService;
import com.sistema.sah.reservas.utils.map.VistaTipoCuartoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar consultas sobre los tipos de cuartos disponibles.
 * <p>
 * Este servicio proporciona métodos para obtener información sobre los tipos de cuartos
 * disponibles en el sistema.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class QueryTipoCuartoService implements IQueryTipoCuartoService {

    private final IVistaTiposCuartosRepository iVistaTiposCuartosRepository;
    private final VistaTipoCuartoMapper vistaTipoCuartoMapper;

    /**
     * Recupera todos los tipos de cuartos disponibles.
     *
     * @return un objeto {@link RespuestaGeneralDto} que incluye el estado, el mensaje y los tipos de cuartos.
     */
    @Override
    public RespuestaGeneralDto findAllTipoCuarto() {
        List<TipoCuartoDto> tipoCuartos = vistaTipoCuartoMapper.listVistaTipoCuartoToListTipoCuarto(iVistaTiposCuartosRepository.findAll());

        if (tipoCuartos.isEmpty()) {
            return buildRespuesta(HttpStatus.NOT_FOUND, "No se encontraron tipos de cuartos disponibles.");
        }

        return buildRespuesta(HttpStatus.OK, "Se consultaron correctamente los tipos de cuarto", tipoCuartos);
    }

    /**
     * Construye una respuesta general con el estado y mensaje proporcionado.
     *
     * @param status el estado HTTP de la respuesta.
     * @param message el mensaje de la respuesta.
     * @param data los datos asociados a la respuesta.
     * @return una instancia de {@link RespuestaGeneralDto}.
     */
    private RespuestaGeneralDto buildRespuesta(HttpStatus status, String message, Object data) {
        return RespuestaGeneralDto.builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Sobrecarga del método {@link #buildRespuesta(HttpStatus, String, Object)} para usar solo el mensaje y el estado.
     *
     * @param status el estado HTTP de la respuesta.
     * @param message el mensaje de la respuesta.
     * @return una instancia de {@link RespuestaGeneralDto}.
     */
    private RespuestaGeneralDto buildRespuesta(HttpStatus status, String message) {
        return buildRespuesta(status, message, null);
    }
}
