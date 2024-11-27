package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.FacturacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFacturacionRepository extends JpaRepository<FacturacionEntity, String> {

    List<FacturacionEntity> findAllByCodigoUsuarioEntityFk_CodigoUsuario(String codigoUsuario);

}
