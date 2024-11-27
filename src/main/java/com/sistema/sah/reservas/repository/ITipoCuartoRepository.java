package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.TipoCuartoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar operaciones relacionadas con la entidad {@link TipoCuartoEntity}.
 * <p>
 * Proporciona métodos para realizar operaciones CRUD sobre la tabla de tipos de cuartos.
 * </p>
 */
@Repository
public interface ITipoCuartoRepository extends JpaRepository<TipoCuartoEntity, Long> {
}
