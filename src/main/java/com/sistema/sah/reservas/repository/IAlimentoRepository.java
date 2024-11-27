package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.AlimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar operaciones relacionadas con la entidad {@link AlimentoEntity}.
 * <p>
 * Proporciona métodos para realizar operaciones CRUD sobre la tabla de alimentos en la base de datos.
 * </p>
 */
@Repository
public interface IAlimentoRepository extends JpaRepository<AlimentoEntity, String> {
}
