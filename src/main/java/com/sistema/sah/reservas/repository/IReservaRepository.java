package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar operaciones relacionadas con la entidad {@link ReservaEntity}.
 * <p>
 * Proporciona métodos para realizar operaciones CRUD sobre la tabla de reservas.
 * </p>
 */
@Repository("reserva")
public interface IReservaRepository extends JpaRepository<ReservaEntity, String> {
}
