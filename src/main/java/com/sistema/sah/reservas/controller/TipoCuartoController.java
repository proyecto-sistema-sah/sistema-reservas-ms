package com.sistema.sah.reservas.controller;

import com.sistema.sah.commons.dto.RespuestaGeneralDto;
import com.sistema.sah.reservas.service.IQueryTipoCuartoService;
import com.sistema.sah.seguridad.service.ITokenBlackListService;
import com.sistema.sah.seguridad.service.impl.TokenBlackListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tipo-cuarto")
@RequiredArgsConstructor
@Log4j2
public class TipoCuartoController {

    private final IQueryTipoCuartoService iQueryTipoCuartoService;

    private final TokenBlackListService tokenBlackListService;


    @GetMapping("/buscar-todos")
    public ResponseEntity<RespuestaGeneralDto> listadoUsuarios(){
        RespuestaGeneralDto respuestaGeneralDto = iQueryTipoCuartoService.findAllTipoCuarto();
        return ResponseEntity.status(respuestaGeneralDto.getStatus()).body(respuestaGeneralDto);
    }



}
