package com.sistema.sah.reservas.controller;

import com.sistema.sah.commons.dto.RespuestaGeneralDto;
import com.sistema.sah.reservas.dto.ReservaCuartoInputDTO;
import com.sistema.sah.reservas.service.ICrearReservaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reserva")
@RequiredArgsConstructor
@Log4j2
public class ReservaController {

    private final ICrearReservaService iCrearReservaService;

    @PostMapping("/crear")
    public ResponseEntity<RespuestaGeneralDto> crearReserva(@RequestBody ReservaCuartoInputDTO reserva){
        RespuestaGeneralDto respuestaGeneralDto = iCrearReservaService.crearReserva(reserva);
        return ResponseEntity.status(respuestaGeneralDto.getStatus()).body(respuestaGeneralDto);
    }

}
