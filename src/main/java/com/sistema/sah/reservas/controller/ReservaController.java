package com.sistema.sah.reservas.controller;

import com.sistema.sah.commons.dto.RespuestaGeneralDto;
import com.sistema.sah.reservas.dto.CambiarEstadoReservaDTO;
import com.sistema.sah.reservas.dto.ReservaCuartoInputDTO;
import com.sistema.sah.reservas.service.ICambiarEstadoReservaService;
import com.sistema.sah.reservas.service.IConsultaReservaService;
import com.sistema.sah.reservas.service.ICrearReservaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gestionar operaciones relacionadas con reservas.
 * <p>
 * Proporciona endpoints para la creación y gestión de reservas de cuartos.
 * </p>
 */
@RestController
@RequestMapping("/reserva")
@RequiredArgsConstructor
@Log4j2
public class ReservaController {

    private final ICrearReservaService crearReservaService;

    private final IConsultaReservaService iConsultaReservaService;

    private final ICambiarEstadoReservaService cambiarEstadoReservaService;

    /**
     * Crea una nueva reserva de cuarto.
     *
     * @param reserva DTO de entrada que contiene los detalles de la reserva.
     * @return una respuesta general con el estado y mensaje del resultado de la operación.
     */
    @PostMapping("/crear")
    public ResponseEntity<RespuestaGeneralDto> crearReserva(@RequestBody ReservaCuartoInputDTO reserva) {
        log.info("Solicitud para crear reserva recibida: {}", reserva);
        RespuestaGeneralDto respuestaGeneralDto = crearReservaService.crearReserva(reserva);
        log.info("Resultado de la creación de la reserva: {}", respuestaGeneralDto);
        return ResponseEntity
                .status(respuestaGeneralDto.getStatus())
                .body(respuestaGeneralDto);
    }

    @GetMapping("/all-usuario")
    public ResponseEntity<RespuestaGeneralDto> consultarReservasUsuario(@RequestParam String codigoUsuario) {
        RespuestaGeneralDto respuestaGeneralDto = iConsultaReservaService.consultarReservasUsuario(codigoUsuario);
        return ResponseEntity
                .status(respuestaGeneralDto.getStatus())
                .body(respuestaGeneralDto);
    }

    @PostMapping("/cambiar-estado")
    public ResponseEntity<RespuestaGeneralDto> cambiarEstado(@RequestBody CambiarEstadoReservaDTO cambiarEstadoReservaDTO) {
        RespuestaGeneralDto respuestaGeneralDto = cambiarEstadoReservaService.cambiarEstado(cambiarEstadoReservaDTO);
        return ResponseEntity
                .status(respuestaGeneralDto.getStatus())
                .body(respuestaGeneralDto);
    }
}
