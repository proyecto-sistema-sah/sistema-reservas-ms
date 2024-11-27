package com.sistema.sah.reservas.service.impl;

import com.sistema.sah.commons.dto.*;
import com.sistema.sah.commons.dto.embeddedid.UsuarioAlimentoIdDto;
import com.sistema.sah.commons.dto.embeddedid.UsuarioServicioIdDto;
import com.sistema.sah.commons.entity.embeddedid.ReservaCuartoIdEntity;
import com.sistema.sah.commons.helper.enums.TipoUsuarioEnum;
import com.sistema.sah.commons.helper.mapper.*;
import com.sistema.sah.commons.helper.util.Utilidades;
import com.sistema.sah.reservas.dto.ReservaCuartoInputDTO;
import com.sistema.sah.reservas.repository.*;
import com.sistema.sah.reservas.service.ICrearReservaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.Utilities;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class CrearReservaService implements ICrearReservaService {

    private final ReservaMapper reservaMapper;
    private final IReservaRepository iReservaRepository;
    private final UsuarioAlimentoMapper usuarioAlimentoMapper;
    private final IUsuarioAlimentoRepository usuarioAlimentoRepository;
    private final UsuarioServicioMapper usuarioServicioMapper;
    private final IUsuarioServicioRepository iUsuarioServicioRepository;
    private final ReservaCuartoMapper reservaCuartoMapper;
    private final IReservaCuartoRepository iReservaCuartoRepository;
    private final IEstadoReservaRepository iEstadoReservaRepository;
    private final EstadoReservaMapper estadoReservaMapper;
    private final ICuartoRepository iCuartoRepository;
    private final ICuartoServicioRepository iCuartoServicioRepository;
    private final CuartoServicioMapper cuartoServicioMapper;
    private final IAlimentoRepository iAlimentoRepository;
    private final AlimentoMapper alimentoMapper;

    public CrearReservaService(@Qualifier("reserva") IReservaRepository iReservaRepository,
                               IUsuarioAlimentoRepository usuarioAlimentoRepository,
                               IUsuarioServicioRepository iUsuarioServicioRepository,
                               IReservaCuartoRepository iReservaCuartoRepository,
                               ReservaMapper reservaMapper,
                               UsuarioAlimentoMapper usuarioAlimentoMapper,
                               UsuarioServicioMapper usuarioServicioMapper,
                               ReservaCuartoMapper reservaCuartoMapper,
                               IEstadoReservaRepository iEstadoReservaRepository,
                               EstadoReservaMapper estadoReservaMapper,
                               ICuartoRepository iCuartoRepository,
                               ICuartoServicioRepository iCuartoServicioRepository,
                               CuartoServicioMapper cuartoServicioMapper,
                               IAlimentoRepository iAlimentoRepository,
                               AlimentoMapper alimentoMapper) {
        this.iReservaRepository = iReservaRepository;
        this.usuarioAlimentoRepository = usuarioAlimentoRepository;
        this.iUsuarioServicioRepository = iUsuarioServicioRepository;
        this.iReservaCuartoRepository = iReservaCuartoRepository;
        this.reservaMapper = reservaMapper;
        this.usuarioAlimentoMapper = usuarioAlimentoMapper;
        this.usuarioServicioMapper = usuarioServicioMapper;
        this.reservaCuartoMapper = reservaCuartoMapper;
        this.iEstadoReservaRepository = iEstadoReservaRepository;
        this.estadoReservaMapper = estadoReservaMapper;
        this.iCuartoRepository = iCuartoRepository;
        this.iCuartoServicioRepository = iCuartoServicioRepository;
        this.cuartoServicioMapper = cuartoServicioMapper;
        this.iAlimentoRepository = iAlimentoRepository;
        this.alimentoMapper = alimentoMapper;
    }

    @Override
    @Transactional
    public RespuestaGeneralDto crearReserva(ReservaCuartoInputDTO reserva) {
        RespuestaGeneralDto respuestaGeneralDto = new RespuestaGeneralDto();
        try{
            List<Object[]> objetos = iCuartoRepository.findByCuartoValorNoche(reserva.getCodigoCuarto());
            BigDecimal valorNoche =  BigDecimal.valueOf(Double.valueOf(objetos.get(0)[0].toString()));
            reserva.setCodigoReserva(new StringBuilder().append(10000 + new Random().nextInt(90000)).toString());
            reserva.setFechaCreacionReserva(LocalDateTime.now());
            reserva.setFechaActualizacionReserva(LocalDateTime.now());
            reserva.setEstadoReservaDtoFk(estadoReservaMapper.entityToDto(iEstadoReservaRepository.findById(3).get()));
            reserva.setValorTotalReserva(valorNoche.multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(reserva.getFechaInicioReserva(), reserva.getFechaFinReserva()))));
            iReservaRepository.save(reservaMapper.dtoToEntity(reserva));
            ReservaCuartoDto reservaCuartoDto = new ReservaCuartoDto();
            ReservaCuartoIdEntity reservaCuartoIdEntity = new ReservaCuartoIdEntity();
            reservaCuartoIdEntity.setCodigoReservaFk(reserva.getCodigoReserva());
            reservaCuartoIdEntity.setCodigoCuartoFk(reserva.getCodigoCuarto());
            reservaCuartoDto.setId(reservaCuartoIdEntity);
            reservaCuartoDto.setCodigoReservaDtoFk(ReservaDto.builder().codigoReserva(reserva.getCodigoReserva()).build());
            reservaCuartoDto.setCodigoCuartoDtoFk(CuartoDto.builder().codigoCuarto(reserva.getCodigoCuarto()).build());
            iReservaCuartoRepository.save(reservaCuartoMapper.dtoToEntity(reservaCuartoDto));
            List<AlimentoDto> alimentos = alimentoMapper.listEntityTolistDto(iAlimentoRepository.findAll());
            for(AlimentoDto alimentoDto : alimentos){
                UsuarioAlimentoDto usuarioAlimentoDto = new UsuarioAlimentoDto();
                UsuarioAlimentoIdDto usuarioAlimentoIdDto = new UsuarioAlimentoIdDto();
                usuarioAlimentoIdDto.setCodigoAlimentoFk(alimentoDto.getCodigoAlimento());
                usuarioAlimentoIdDto.setCodigoUsuarioFk(reserva.getCodigoUsuarioDtoFk().getCodigoUsuario());
                usuarioAlimentoDto.setId(usuarioAlimentoIdDto);
                usuarioAlimentoDto.setCodigoUsuarioDtoFk(UsuarioDto.builder().codigoUsuario(reserva.getCodigoUsuarioDtoFk().getCodigoUsuario()).build());
                usuarioAlimentoDto.setCodigoAlimentoDtoFk(AlimentoDto.builder().codigoAlimento(alimentoDto.getCodigoAlimento()).build());
                usuarioAlimentoDto.setEstadoUsuarioAlimentoDtoFk(EstadoUsuarioAlimentoDto.builder().id(1).build());
                usuarioAlimentoRepository.save(usuarioAlimentoMapper.dtoToEntity(usuarioAlimentoDto));
            }
            List<CuartoServicioDto> listCuartoServicioDto = cuartoServicioMapper.listEntityTolistDto(iCuartoServicioRepository.findByCodigoCuartoEntityFk_CodigoCuarto(reserva.getCodigoCuarto()));
            for(CuartoServicioDto cuartoServicioDto : listCuartoServicioDto){
                UsuarioServicioDto usuarioServicioDto = new UsuarioServicioDto();
                UsuarioServicioIdDto usuarioServicioIdDto = new UsuarioServicioIdDto();
                usuarioServicioIdDto.setCodigoUsuarioFk(reserva.getCodigoUsuarioDtoFk().getCodigoUsuario());
                usuarioServicioIdDto.setCodigoServicioFk(cuartoServicioDto.getCodigoServicioDtoFk().getCodigoServicio());
                usuarioServicioDto.setId(usuarioServicioIdDto);
                usuarioServicioDto.setCodigoServicioDtoFk(ServicioDto.builder().codigoServicio(cuartoServicioDto.getCodigoServicioDtoFk().getCodigoServicio()).build());
                usuarioServicioDto.setCodigoUsuarioDtoFk(UsuarioDto.builder().codigoUsuario(reserva.getCodigoUsuarioDtoFk().getCodigoUsuario()).build());
                usuarioServicioDto.setEstadoUsuarioServicioDtoFk(EstadoUsuarioServicioDto.builder().id(1).build());
                iUsuarioServicioRepository.save(usuarioServicioMapper.dtoToEntity(usuarioServicioDto));
            }
            respuestaGeneralDto.setStatus(HttpStatus.CREATED);
            respuestaGeneralDto.setMessage("Se creo correctamente la reserva");
        }catch (Exception e){
            log.error(e.getMessage());
            respuestaGeneralDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            respuestaGeneralDto.setMessage("Hubo un problema en la creacion");
        }
        return respuestaGeneralDto;
    }
}
