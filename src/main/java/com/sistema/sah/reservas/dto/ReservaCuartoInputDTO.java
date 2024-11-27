package com.sistema.sah.reservas.dto;

import com.sistema.sah.commons.dto.ReservaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para la creación de reservas de cuartos.
 * <p>
 * Hereda los atributos de {@link ReservaDto} y agrega el código del cuarto asociado
 * a la reserva.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaCuartoInputDTO extends ReservaDto {

    /**
     * Código único que identifica el cuarto reservado.
     */
    private String codigoCuarto;
}
