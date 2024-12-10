package com.sistema.sah.reservas.dto;

import com.sistema.sah.commons.helper.enums.EstadoFacturacionEnum;
import com.sistema.sah.commons.helper.enums.EstadoReservaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CambiarEstadoReservaDTO {

    private String estadoNuevo;

    private String estadoFacturacion;

    private String codigoReserva;

}
