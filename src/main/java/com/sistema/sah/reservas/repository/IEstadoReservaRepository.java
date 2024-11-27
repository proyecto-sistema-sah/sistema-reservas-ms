package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.EstadoReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar operaciones relacionadas con la entidad {@link EstadoReservaEntity}.
 * <p>
 * Proporciona métodos para realizar operaciones CRUD sobre la tabla de estados de reserva.
 * </p>
 */
@Repository
public interface IEstadoReservaRepository extends JpaRepository<EstadoReservaEntity, Integer> {

}
