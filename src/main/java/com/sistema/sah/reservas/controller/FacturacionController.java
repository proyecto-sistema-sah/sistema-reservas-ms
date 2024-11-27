package com.sistema.sah.reservas.controller;

import com.sistema.sah.commons.dto.RespuestaGeneralDto;
import com.sistema.sah.reservas.service.IConsultarFacturacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/facturacion")
@RequiredArgsConstructor
@Log4j2
public class FacturacionController {

    private final IConsultarFacturacionService iConsultarFacturacionService;

    @GetMapping("/all-facturaciones")
    public ResponseEntity<RespuestaGeneralDto> consultarFacturacionesUsuario(@RequestParam String codigoUsuario) {
        RespuestaGeneralDto respuestaGeneralDto = iConsultarFacturacionService.consultarFacturacionUsuario(codigoUsuario);
        return ResponseEntity
                .status(respuestaGeneralDto.getStatus())
                .body(respuestaGeneralDto);
    }

}
