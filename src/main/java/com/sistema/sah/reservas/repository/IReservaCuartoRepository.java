package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.ReservaCuartoEntity;
import com.sistema.sah.commons.entity.embeddedid.ReservaCuartoIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar operaciones relacionadas con la entidad {@link ReservaCuartoEntity}.
 * <p>
 * Proporciona métodos para realizar operaciones CRUD sobre la relación entre reservas y cuartos.
 * La entidad utiliza una clave primaria compuesta representada por {@link ReservaCuartoIdEntity}.
 * </p>
 */
@Repository
public interface IReservaCuartoRepository extends JpaRepository<ReservaCuartoEntity, ReservaCuartoIdEntity> {
}
