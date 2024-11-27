package com.sistema.sah.reservas.service.impl;

import com.sistema.sah.commons.dto.ReservaDto;
import com.sistema.sah.commons.dto.RespuestaGeneralDto;
import com.sistema.sah.commons.helper.mapper.ReservaMapper;
import com.sistema.sah.reservas.repository.IReservaRepository;
import com.sistema.sah.reservas.service.IConsultaReservaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultaReservaService implements IConsultaReservaService {

    private final IReservaRepository iReservaRepository;

    private final ReservaMapper reservaMapper;

    @Override
    public RespuestaGeneralDto consultarReservasUsuario(String codigoUsuario) {
        RespuestaGeneralDto respuestaGeneralDto = new RespuestaGeneralDto();
        try{
            List<ReservaDto> reservas =  reservaMapper.listEntityTolistDto(iReservaRepository.buscarReservasUsuario(codigoUsuario));
            respuestaGeneralDto.setMessage("Se encontro correctamente las reservas");
            respuestaGeneralDto.setStatus(HttpStatus.OK);
            respuestaGeneralDto.setData(reservas);
        }catch (Exception e){
            log.error("Error ", e);
            respuestaGeneralDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            respuestaGeneralDto.setMessage(e.getMessage());
        }
        return respuestaGeneralDto;
    }
}
