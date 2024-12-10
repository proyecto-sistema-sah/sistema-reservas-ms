package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.EstadoReservaEntity;
import com.sistema.sah.commons.helper.enums.EstadoReservaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para gestionar operaciones relacionadas con la entidad {@link EstadoReservaEntity}.
 * <p>
 * Proporciona métodos para realizar operaciones CRUD sobre la tabla de estados de reserva.
 * </p>
 */
@Repository
public interface IEstadoReservaRepository extends JpaRepository<EstadoReservaEntity, Integer> {

    @Query("""
        SELECT er FROM EstadoReservaEntity er where er.nombreEstadoReserva = :nombre
        """)
    Optional<EstadoReservaEntity> findByNombreEstadoReserva(@Param("nombre") EstadoReservaEnum nombreEstadoReserva);

}
