package com.sistema.sah.reservas.service.impl;

import com.sistema.sah.commons.dto.FacturacionDto;
import com.sistema.sah.commons.dto.ReservaDto;
import com.sistema.sah.commons.dto.RespuestaGeneralDto;
import com.sistema.sah.commons.helper.enums.EstadoFacturacionEnum;
import com.sistema.sah.commons.helper.enums.EstadoReservaEnum;
import com.sistema.sah.commons.helper.mapper.EstadoReservaMapper;
import com.sistema.sah.commons.helper.mapper.FacturacionMapper;
import com.sistema.sah.commons.helper.mapper.ReservaMapper;
import com.sistema.sah.reservas.dto.CambiarEstadoReservaDTO;
import com.sistema.sah.reservas.repository.IEstadoReservaRepository;
import com.sistema.sah.reservas.repository.IFacturacionRepository;
import com.sistema.sah.reservas.repository.IReservaRepository;
import com.sistema.sah.reservas.service.ICambiarEstadoReservaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio {@link ICambiarEstadoReservaService}.
 * <p>
 * Esta clase gestiona la lógica para cambiar el estado de una reserva y su facturación
 * asociada, asegurando consistencia en las operaciones realizadas en la base de datos.
 * </p>
 * <p>
 * Utiliza repositorios para interactuar con las entidades de la base de datos y mappers
 * para convertir entre entidades y DTOs.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class CambiarEstadoReservaService implements ICambiarEstadoReservaService {

    private static final String SUCCESS_MESSAGE = "Estado cambiado correctamente.";
    private static final String ERROR_MESSAGE = "Hubo un error al cambiar el estado.";
    private static final String ERROR_LOG_MESSAGE = "Error al cambiar el estado de la reserva [{}]: {}";

    private final IReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    private final IEstadoReservaRepository estadoReservaRepository;
    private final EstadoReservaMapper estadoReservaMapper;
    private final IFacturacionRepository facturacionRepository;
    private final FacturacionMapper facturacionMapper;

    /**
     * Cambia el estado de una reserva y actualiza su facturación asociada.
     *
     * @param cambiarEstado DTO que contiene la información necesaria para cambiar el
     *                      estado de una reserva, incluyendo el código de la reserva,
     *                      el nuevo estado y el estado de la facturación.
     * @return un {@link RespuestaGeneralDto} con el mensaje y el estado de la operación.
     */
    @Override
    @Transactional
    public RespuestaGeneralDto cambiarEstado(CambiarEstadoReservaDTO cambiarEstado) {
        RespuestaGeneralDto respuestaGeneralDto = new RespuestaGeneralDto();

        try {
            // Validar existencia de la reserva
            ReservaDto reservaDto = obtenerReserva(cambiarEstado.getCodigoReserva());
            validarReserva(reservaDto, cambiarEstado);

            // Actualizar estado de reserva y facturación
            actualizarEstadoReserva(reservaDto, cambiarEstado);
            actualizarEstadoFacturacion(cambiarEstado);

            respuestaGeneralDto.setMessage(SUCCESS_MESSAGE);
            respuestaGeneralDto.setStatus(HttpStatus.OK);

        } catch (IllegalArgumentException ex) {
            log.warn(ERROR_LOG_MESSAGE, cambiarEstado.getCodigoReserva(), ex.getMessage());
            respuestaGeneralDto.setMessage(ex.getMessage());
            respuestaGeneralDto.setStatus(HttpStatus.BAD_REQUEST);

        } catch (Exception ex) {
            log.error(ERROR_LOG_MESSAGE, cambiarEstado.getCodigoReserva(), ex.getMessage(), ex);
            respuestaGeneralDto.setMessage(ERROR_MESSAGE);
            respuestaGeneralDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return respuestaGeneralDto;
    }

    /**
     * Obtiene una reserva a partir de su código.
     *
     * @param codigoReserva el código de la reserva a buscar.
     * @return un {@link ReservaDto} con la información de la reserva encontrada.
     * @throws IllegalArgumentException si no se encuentra la reserva.
     */
    private ReservaDto obtenerReserva(String codigoReserva) {
        return reservaMapper.entityToDto(
                reservaRepository.findByCodigoReserva(codigoReserva)
                        .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada: " + codigoReserva))
        );
    }

    /**
     * Valida la consistencia de la reserva y el nuevo estado proporcionado.
     *
     * @param reservaDto    la reserva a validar.
     * @param cambiarEstado DTO que contiene la información del cambio de estado.
     * @throws IllegalArgumentException si la reserva o el nuevo estado no son válidos.
     */
    private void validarReserva(ReservaDto reservaDto, CambiarEstadoReservaDTO cambiarEstado) {
        if (cambiarEstado.getEstadoNuevo() == null) {
            throw new IllegalArgumentException("El estado nuevo es requerido.");
        }
        if (reservaDto.getEstadoReservaDtoFk() == null) {
            throw new IllegalArgumentException("La reserva no tiene un estado asociado válido.");
        }
    }

    /**
     * Actualiza el estado de una reserva en la base de datos.
     *
     * @param reservaDto    la reserva a actualizar.
     * @param cambiarEstado DTO que contiene el nuevo estado de la reserva.
     */
    private void actualizarEstadoReserva(ReservaDto reservaDto, CambiarEstadoReservaDTO cambiarEstado) {
        EstadoReservaEnum estadoReservaEnum = EstadoReservaEnum.valueOf(cambiarEstado.getEstadoNuevo());
        reservaDto.setEstadoReservaDtoFk(
                estadoReservaMapper.entityToDto(
                        estadoReservaRepository.findByNombreEstadoReserva(estadoReservaEnum)
                                .orElseThrow(() -> new IllegalArgumentException("Estado de reserva no encontrado: " + cambiarEstado.getEstadoNuevo()))
                )
        );
        reservaRepository.save(reservaMapper.dtoToEntity(reservaDto));
        log.info("Estado de reserva actualizado correctamente para la reserva [{}].", reservaDto.getCodigoReserva());
    }

    /**
     * Actualiza el estado de facturación de una reserva en la base de datos.
     *
     * @param cambiarEstado DTO que contiene el nuevo estado de la facturación.
     * @throws IllegalArgumentException si no se encuentra la facturación asociada a la reserva.
     */
    private void actualizarEstadoFacturacion(CambiarEstadoReservaDTO cambiarEstado) {
        FacturacionDto facturacionDto = facturacionMapper.entityToDto(
                facturacionRepository.findByCodigoReservaEntityFk_CodigoReserva(cambiarEstado.getCodigoReserva())
                        .orElseThrow(() -> new IllegalArgumentException("Facturación no encontrada para la reserva: " + cambiarEstado.getCodigoReserva()))
        );

        facturacionDto.setEstadoFacturacion(EstadoFacturacionEnum.valueOf(cambiarEstado.getEstadoFacturacion()));
        facturacionRepository.save(facturacionMapper.dtoToEntity(facturacionDto));
        log.info("Estado de facturación actualizado correctamente para la reserva [{}].", cambiarEstado.getCodigoReserva());
    }
}
