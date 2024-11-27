package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.UsuarioServicioEntity;
import com.sistema.sah.commons.entity.embeddedid.UsuarioServicioIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioServicioRepository extends JpaRepository<UsuarioServicioEntity, UsuarioServicioIdEntity> {
}
