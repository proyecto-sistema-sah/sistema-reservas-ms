package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.CuartoServicioEntity;
import com.sistema.sah.commons.entity.embeddedid.CuartoServicioIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar operaciones relacionadas con la entidad {@link CuartoServicioEntity}.
 * <p>
 * Proporciona métodos para realizar operaciones CRUD y consultas específicas sobre la relación entre
 * cuartos y servicios.
 * </p>
 */
@Repository
public interface ICuartoServicioRepository extends JpaRepository<CuartoServicioEntity, CuartoServicioIdEntity> {

    /**
     * Obtiene una lista de servicios asociados a un cuarto específico.
     *
     * @param codigoCuarto el código único del cuarto.
     * @return una lista de entidades {@link CuartoServicioEntity} que representan los servicios asociados al cuarto.
     */
    List<CuartoServicioEntity> findByCodigoCuartoEntityFk_CodigoCuarto(String codigoCuarto);
}
