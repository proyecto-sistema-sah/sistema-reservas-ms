package com.sistema.sah.reservas.utils.map;

import com.sistema.sah.commons.dto.EstadoReservaDto;
import com.sistema.sah.commons.dto.ReservaDto;
import com.sistema.sah.commons.dto.UsuarioDto;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Mapper(componentModel = "spring")
public interface ReservaObjectMapper {

    /**
     * Convierte un arreglo de objetos (fila de la consulta nativa) en un objeto {@link ReservaDto}.
     *
     * @param row Arreglo de objetos que representa una fila de la consulta nativa.
     * @return Objeto {@link ReservaDto} mapeado.
     */
    default ReservaDto mapToReservaDto(Object[] row) {
        if (row == null) {
            return null;
        }
        return ReservaDto.builder()
                .codigoReserva(mapCodigoReserva(row))
                .fechaInicioReserva(mapFechaInicioReserva(row))
                .fechaFinReserva(mapFechaFinReserva(row))
                .fechaCreacionReserva(mapFechaCreacionReserva(row))
                .fechaActualizacionReserva(mapFechaActualizacionReserva(row))
                .valorTotalReserva(mapValorTotalReserva(row))
                .codigoUsuarioDtoFk(mapCodigoUsuarioDtoFk(row))
                .estadoReservaDtoFk(mapEstadoReservaDtoFk(row))
                .build();
    }

    // Métodos auxiliares para mapear campos específicos

    default String mapCodigoReserva(Object[] row) {
        return row[0] != null ? (String) row[0] : null;
    }

    default LocalDate mapFechaInicioReserva(Object[] row) {
        return row[1] != null ? (LocalDate) row[1] : null;
    }

    default LocalDate mapFechaFinReserva(Object[] row) {
        return row[2] != null ? (LocalDate) row[2] : null;
    }

    default LocalDateTime mapFechaCreacionReserva(Object[] row) {
        return row[3] != null ? (LocalDateTime) row[3] : null;
    }

    default LocalDateTime mapFechaActualizacionReserva(Object[] row) {
        return row[4] != null ? (LocalDateTime) row[4] : null;
    }

    default BigDecimal mapValorTotalReserva(Object[] row) {
        return row[5] != null ? (BigDecimal) row[5] : null;
    }

    default UsuarioDto mapCodigoUsuarioDtoFk(Object[] row) {
        if (row[6] == null) {
            return null;
        }
        return UsuarioDto.builder()
                .codigoUsuario((String) row[6])
                .build();
    }

    default EstadoReservaDto mapEstadoReservaDtoFk(Object[] row) {
        if (row[7] == null) {
            return null;
        }
        return EstadoReservaDto.builder()
                .id((Integer) row[7])
                .build();
    }

}
