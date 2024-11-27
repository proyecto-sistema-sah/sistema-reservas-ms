package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.UsuarioAlimentoEntity;
import com.sistema.sah.commons.entity.embeddedid.UsuarioAlimentoIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar operaciones relacionadas con la entidad {@link UsuarioAlimentoEntity}.
 * <p>
 * Proporciona métodos para realizar operaciones CRUD sobre la relación entre usuarios y alimentos.
 * Utiliza una clave primaria compuesta representada por {@link UsuarioAlimentoIdEntity}.
 * </p>
 */
@Repository
public interface IUsuarioAlimentoRepository extends JpaRepository<UsuarioAlimentoEntity, UsuarioAlimentoIdEntity> {
}
