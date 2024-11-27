package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.UsuarioAlimentoEntity;
import com.sistema.sah.commons.entity.UsuarioEntity;
import com.sistema.sah.commons.entity.embeddedid.UsuarioAlimentoIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioAlimentoRepository extends JpaRepository<UsuarioAlimentoEntity, UsuarioAlimentoIdEntity> {
}
