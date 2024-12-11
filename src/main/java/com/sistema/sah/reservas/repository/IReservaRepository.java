package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar operaciones relacionadas con la entidad {@link ReservaEntity}.
 * <p>
 * Proporciona métodos para realizar operaciones CRUD sobre la tabla de reservas.
 * </p>
 */
@Repository("reserva")
public interface IReservaRepository extends JpaRepository<ReservaEntity, String> {

    @Query("""
        SELECT r FROM ReservaEntity r where r.codigoUsuarioEntityFk.codigoUsuario = :codigoUsuario
    """)
    List<ReservaEntity> buscarReservasUsuario(@Param("codigoUsuario") String codigoUsuario);

    Optional<ReservaEntity> findByCodigoReserva(String codigoReserva);


    @Procedure(procedureName = "sah.procesar_reserva")
    void procesarReserva(
            @Param("codigoReserva") String codigoReserva,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin,
            @Param("valorTotal") BigDecimal valorTotal,
            @Param("codigoUsuario") String codigoUsuario,
            @Param("estadoReserva") Integer estadoReserva,
            @Param("codigoCuarto") String codigoCuarto
    );

}
