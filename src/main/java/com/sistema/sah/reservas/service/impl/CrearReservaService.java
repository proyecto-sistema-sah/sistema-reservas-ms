package com.sistema.sah.reservas.service.impl;

import com.sistema.sah.commons.dto.*;
import com.sistema.sah.commons.dto.embeddedid.ReservaCuartoIdDto;
import com.sistema.sah.commons.dto.embeddedid.UsuarioAlimentoIdDto;
import com.sistema.sah.commons.dto.embeddedid.UsuarioServicioIdDto;
import com.sistema.sah.commons.entity.embeddedid.ReservaCuartoIdEntity;
import com.sistema.sah.commons.helper.mapper.*;
import com.sistema.sah.reservas.dto.ReservaCuartoInputDTO;
import com.sistema.sah.reservas.repository.*;
import com.sistema.sah.reservas.service.ICrearReportePdfFacturaService;
import com.sistema.sah.reservas.service.ICrearReservaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

/**
 * Servicio para gestionar la creación de reservas en el sistema.
 * <p>
 * Este servicio implementa la lógica necesaria para crear una reserva, incluyendo la asociación de cuartos,
 * alimentos y servicios al usuario que realiza la reserva.
 * </p>
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CrearReservaService implements ICrearReservaService {

    private final ReservaMapper reservaMapper;
    private final IReservaRepository reservaRepository;
    private final UsuarioAlimentoMapper usuarioAlimentoMapper;
    private final IUsuarioAlimentoRepository usuarioAlimentoRepository;
    private final UsuarioServicioMapper usuarioServicioMapper;
    private final IUsuarioServicioRepository usuarioServicioRepository;
    private final ReservaCuartoMapper reservaCuartoMapper;
    private final IReservaCuartoRepository reservaCuartoRepository;
    private final IEstadoReservaRepository estadoReservaRepository;
    private final EstadoReservaMapper estadoReservaMapper;
    private final ICuartoRepository cuartoRepository;
    private final ICuartoServicioRepository cuartoServicioRepository;
    private final CuartoServicioMapper cuartoServicioMapper;
    private final IAlimentoRepository alimentoRepository;
    private final AlimentoMapper alimentoMapper;
    private final ICrearReportePdfFacturaService iCrearReportePdfFacturaService;
    /**
     * Crea una nueva reserva en el sistema.
     *
     * @param reserva objeto {@link ReservaCuartoInputDTO} que contiene los datos necesarios para la reserva.
     * @return un objeto {@link RespuestaGeneralDto} con el resultado de la operación, incluyendo el estado y el mensaje.
     */
    @Override
    @Transactional
    public RespuestaGeneralDto crearReserva(ReservaCuartoInputDTO reserva) {
        RespuestaGeneralDto respuesta = new RespuestaGeneralDto();

        try {
            log.info("Iniciando la creación de reserva: {}", reserva);

            // Calcular el valor total de la reserva
            BigDecimal valorNoche = obtenerValorNocheCuarto(reserva.getCodigoCuarto());
            reserva.setCodigoReserva(generarCodigoReserva());
            reserva.setFechaCreacionReserva(LocalDateTime.now());
            reserva.setFechaActualizacionReserva(LocalDateTime.now());
            reserva.setEstadoReservaDtoFk(
                    estadoReservaMapper.entityToDto(estadoReservaRepository.findById(3).orElseThrow())
            );
            reserva.setValorTotalReserva(calcularValorTotalReserva(reserva, valorNoche));

            // Guardar la reserva
            reservaRepository.save(reservaMapper.dtoToEntity(reserva));
            iCrearReportePdfFacturaService.generarReporte(reserva.getCodigoUsuarioDtoFk().getCodigoUsuario());
            guardarReservaCuarto(reserva);
            asociarAlimentosAUsuario(reserva);
            asociarServiciosACuarto(reserva);

            respuesta.setStatus(HttpStatus.CREATED);
            respuesta.setMessage("Se creó correctamente la reserva");
            log.info("Reserva creada con éxito: {}", reserva);
        } catch (Exception e) {
            log.error("Error al crear la reserva: {}", e.getMessage(), e);
            respuesta.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            respuesta.setMessage("Hubo un problema en la creación de la reserva");
        }

        return respuesta;
    }

    /**
     * Obtiene el valor por noche de un cuarto a partir de su código.
     *
     * @param codigoCuarto el código único del cuarto.
     * @return el valor por noche del cuarto como {@link BigDecimal}.
     */
    private BigDecimal obtenerValorNocheCuarto(String codigoCuarto) {
        List<Object[]> resultado = cuartoRepository.findByCuartoValorNoche(codigoCuarto);
        if (resultado.isEmpty()) {
            throw new IllegalArgumentException("No se encontró el cuarto con código: " + codigoCuarto);
        }
        return BigDecimal.valueOf(Double.parseDouble(resultado.get(0)[0].toString()));
    }

    /**
     * Genera un código único para la reserva.
     *
     * @return un código de reserva aleatorio como {@link String}.
     */
    private String generarCodigoReserva() {
        return String.valueOf(10000 + new Random().nextInt(90000));
    }

    /**
     * Calcula el valor total de la reserva en función del número de días y el valor por noche.
     *
     * @param reserva    los datos de la reserva.
     * @param valorNoche el valor por noche del cuarto.
     * @return el valor total de la reserva como {@link BigDecimal}.
     */
    private BigDecimal calcularValorTotalReserva(ReservaCuartoInputDTO reserva, BigDecimal valorNoche) {
        long dias = ChronoUnit.DAYS.between(reserva.getFechaInicioReserva(), reserva.getFechaFinReserva());
        return valorNoche.multiply(BigDecimal.valueOf(dias));
    }

    /**
     * Guarda la asociación entre la reserva y el cuarto en la base de datos.
     *
     * @param reserva los datos de la reserva.
     */
    private void guardarReservaCuarto(ReservaCuartoInputDTO reserva) {
        ReservaCuartoIdDto reservaCuartoIdDto = new ReservaCuartoIdDto();
        reservaCuartoIdDto.setCodigoReservaFk(reserva.getCodigoReserva());
        reservaCuartoIdDto.setCodigoCuartoFk(reserva.getCodigoCuarto());
        ReservaCuartoDto reservaCuartoDto = ReservaCuartoDto.builder()
                .id(reservaCuartoIdDto)
                .codigoReservaDtoFk(ReservaDto.builder()
                        .codigoReserva(reserva.getCodigoReserva())
                        .build())
                .codigoCuartoDtoFk(CuartoDto.builder()
                        .codigoCuarto(reserva.getCodigoCuarto())
                        .build())
                .build();

        reservaCuartoRepository.save(reservaCuartoMapper.dtoToEntity(reservaCuartoDto));
    }

    /**
     * Asocia alimentos disponibles al usuario que realiza la reserva.
     *
     * @param reserva los datos de la reserva.
     */
    private void asociarAlimentosAUsuario(ReservaCuartoInputDTO reserva) {
        List<AlimentoDto> alimentos = alimentoMapper.listEntityTolistDto(alimentoRepository.findAll());
        for (AlimentoDto alimento : alimentos) {
            UsuarioAlimentoDto usuarioAlimento = UsuarioAlimentoDto.builder()
                    .id(UsuarioAlimentoIdDto.builder()
                            .codigoAlimentoFk(alimento.getCodigoAlimento())
                            .codigoUsuarioFk(reserva.getCodigoUsuarioDtoFk().getCodigoUsuario())
                            .build())
                    .codigoUsuarioDtoFk(UsuarioDto.builder()
                            .codigoUsuario(reserva.getCodigoUsuarioDtoFk().getCodigoUsuario())
                            .build())
                    .codigoAlimentoDtoFk(AlimentoDto.builder()
                            .codigoAlimento(alimento.getCodigoAlimento())
                            .build())
                    .estadoUsuarioAlimentoDtoFk(EstadoUsuarioAlimentoDto.builder()
                            .id(1)
                            .build())
                    .build();

            usuarioAlimentoRepository.save(usuarioAlimentoMapper.dtoToEntity(usuarioAlimento));
        }
    }

    /**
     * Asocia servicios del cuarto al usuario que realiza la reserva.
     *
     * @param reserva los datos de la reserva.
     */
    private void asociarServiciosACuarto(ReservaCuartoInputDTO reserva) {
        List<CuartoServicioDto> servicios = cuartoServicioMapper.listEntityTolistDto(
                cuartoServicioRepository.findByCodigoCuartoEntityFk_CodigoCuarto(reserva.getCodigoCuarto())
        );
        for (CuartoServicioDto servicio : servicios) {
            UsuarioServicioDto usuarioServicio = UsuarioServicioDto.builder()
                    .id(UsuarioServicioIdDto.builder()
                            .codigoUsuarioFk(reserva.getCodigoUsuarioDtoFk().getCodigoUsuario())
                            .codigoServicioFk(servicio.getCodigoServicioDtoFk().getCodigoServicio())
                            .build())
                    .codigoUsuarioDtoFk(UsuarioDto.builder()
                            .codigoUsuario(reserva.getCodigoUsuarioDtoFk().getCodigoUsuario())
                            .build())
                    .codigoServicioDtoFk(ServicioDto.builder()
                            .codigoServicio(servicio.getCodigoServicioDtoFk().getCodigoServicio())
                            .build())
                    .estadoUsuarioServicioDtoFk(EstadoUsuarioServicioDto.builder()
                            .id(1)
                            .build())
                    .build();

            usuarioServicioRepository.save(usuarioServicioMapper.dtoToEntity(usuarioServicio));
        }
    }
}
