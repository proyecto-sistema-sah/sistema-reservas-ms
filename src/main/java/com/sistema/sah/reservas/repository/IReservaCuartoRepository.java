package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.ReservaCuartoEntity;
import com.sistema.sah.commons.entity.embeddedid.ReservaCuartoIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Repositorio para gestionar operaciones relacionadas con la entidad {@link ReservaCuartoEntity}.
 * <p>
 * Proporciona métodos para realizar operaciones CRUD sobre la relación entre reservas y cuartos.
 * La entidad utiliza una clave primaria compuesta representada por {@link ReservaCuartoIdEntity}.
 * </p>
 */
@Repository
public interface IReservaCuartoRepository extends JpaRepository<ReservaCuartoEntity, ReservaCuartoIdEntity> {

    @Procedure(procedureName = "sah.registrar_reserva")
    void registrarReserva(
            @Param("p_codigo_reserva") String codigoReserva,
            @Param("p_fecha_inicio") LocalDate fechaInicio,
            @Param("p_fecha_fin") LocalDate fechaFin,
            @Param("p_valor_total") BigDecimal valorTotal,
            @Param("p_codigo_usuario") String codigoUsuario,
            @Param("p_estado_reserva") Integer estadoReserva
    );

}
