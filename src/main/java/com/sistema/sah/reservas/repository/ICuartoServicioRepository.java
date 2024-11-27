package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.CuartoServicioEntity;
import com.sistema.sah.commons.entity.embeddedid.CuartoServicioIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICuartoServicioRepository extends JpaRepository<CuartoServicioEntity, CuartoServicioIdEntity> {

    List<CuartoServicioEntity> findByCodigoCuartoEntityFk_CodigoCuarto(String codigoCuarto);

}
