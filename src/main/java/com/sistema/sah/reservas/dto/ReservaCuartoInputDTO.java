package com.sistema.sah.reservas.dto;

import com.sistema.sah.commons.dto.ReservaDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaCuartoInputDTO extends ReservaDto {

    private String codigoCuarto;

}
