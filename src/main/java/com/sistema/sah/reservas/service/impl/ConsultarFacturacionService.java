package com.sistema.sah.reservas.service.impl;

import com.sistema.sah.commons.dto.FacturacionDto;
import com.sistema.sah.commons.dto.RespuestaGeneralDto;
import com.sistema.sah.commons.helper.mapper.FacturacionMapper;
import com.sistema.sah.reservas.repository.IFacturacionRepository;
import com.sistema.sah.reservas.service.IConsultarFacturacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultarFacturacionService implements IConsultarFacturacionService {

    private final IFacturacionRepository iFacturacionRepository;

    private final FacturacionMapper facturacionMapper;

    @Override
    public RespuestaGeneralDto consultarFacturacionUsuario(String codigoUsuario) {
        RespuestaGeneralDto respuestaGeneralDto = new RespuestaGeneralDto();
        try{
            List<FacturacionDto> facturaciones = facturacionMapper.listEntityTolistDto(iFacturacionRepository.findAllByCodigoUsuarioEntityFk_CodigoUsuario(codigoUsuario));
            respuestaGeneralDto.setMessage("Se consultaron correctamente las facturaciones");
            respuestaGeneralDto.setStatus(HttpStatus.OK);
            respuestaGeneralDto.setData(facturaciones);
        }catch (Exception e){
            log.error("Error ", e);
            respuestaGeneralDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            respuestaGeneralDto.setMessage("Error en consultar las consultas");
        }
        return respuestaGeneralDto;
    }
}
