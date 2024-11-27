package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.UsuarioServicioEntity;
import com.sistema.sah.commons.entity.embeddedid.UsuarioServicioIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar operaciones relacionadas con la entidad {@link UsuarioServicioEntity}.
 * <p>
 * Proporciona métodos para realizar operaciones CRUD sobre la relación entre usuarios y servicios.
 * Utiliza una clave primaria compuesta representada por {@link UsuarioServicioIdEntity}.
 * </p>
 */
@Repository
public interface IUsuarioServicioRepository extends JpaRepository<UsuarioServicioEntity, UsuarioServicioIdEntity> {
}
