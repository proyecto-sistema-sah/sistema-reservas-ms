package com.sistema.sah.reservas.dto;

import com.sistema.sah.commons.dto.EstadoReservaDto;
import com.sistema.sah.commons.dto.ReservaDto;
import com.sistema.sah.commons.dto.UsuarioDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaPdfDTO extends ReservaDto {

    private String estadoReserva;

    public ReservaPdfDTO(ReservaDto reservaDto, String estadoReserva) {
        super(
                reservaDto.getCodigoReserva(),
                reservaDto.getFechaInicioReserva(),
                reservaDto.getFechaFinReserva(),
                reservaDto.getFechaCreacionReserva(),
                reservaDto.getFechaActualizacionReserva(),
                reservaDto.getValorTotalReserva(),
                reservaDto.getCodigoUsuarioDtoFk(),
                reservaDto.getEstadoReservaDtoFk()
        );
        this.estadoReserva = estadoReserva;
    }
}
