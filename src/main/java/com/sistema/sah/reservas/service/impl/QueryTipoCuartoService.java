package com.sistema.sah.reservas.service.impl;

import com.sistema.sah.commons.dto.RespuestaGeneralDto;
import com.sistema.sah.commons.dto.TipoCuartoDto;
import com.sistema.sah.commons.helper.mapper.TipoCuartoMapper;
import com.sistema.sah.reservas.repository.ITipoCuartoRepository;
import com.sistema.sah.reservas.service.IQueryTipoCuartoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryTipoCuartoService implements IQueryTipoCuartoService {

    private final ITipoCuartoRepository iTipoCuartoRepository;

    private final TipoCuartoMapper tipoCuartoMapper;

    @Override
    public RespuestaGeneralDto findAllTipoCuarto() {
        return RespuestaGeneralDto.builder().data(
                tipoCuartoMapper.listEntityTolistDto(iTipoCuartoRepository.findAll())
        ).status(HttpStatus.OK).message("Se consultaron correctamente los tipos de cuarto").build();
    }
}
