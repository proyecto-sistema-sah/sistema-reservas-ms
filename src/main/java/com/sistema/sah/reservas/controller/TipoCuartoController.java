package com.sistema.sah.reservas.controller;

import com.sistema.sah.commons.dto.RespuestaGeneralDto;
import com.sistema.sah.reservas.service.IQueryTipoCuartoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para gestionar operaciones relacionadas con los tipos de cuartos.
 * <p>
 * Proporciona endpoints para la consulta de tipos de cuartos disponibles.
 * </p>
 */
@RestController
@RequestMapping("/tipo-cuarto")
@RequiredArgsConstructor
@Log4j2
public class TipoCuartoController {

    private final IQueryTipoCuartoService queryTipoCuartoService;

    /**
     * Obtiene el listado de todos los tipos de cuartos.
     *
     * @return una respuesta general con el estado, mensaje y datos de los tipos de cuartos.
     */
    @GetMapping("/buscar-todos")
    public ResponseEntity<RespuestaGeneralDto> listadoTiposCuarto() {
        log.info("Solicitud para obtener el listado de tipos de cuartos recibida");
        RespuestaGeneralDto respuesta = queryTipoCuartoService.findAllTipoCuarto();
        log.info("Resultado de la consulta de tipos de cuartos: {}", respuesta);
        return ResponseEntity
                .status(respuesta.getStatus())
                .body(respuesta);
    }
}
