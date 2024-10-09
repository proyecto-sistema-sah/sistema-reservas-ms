package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.TipoCuartoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoCuartoRepository extends JpaRepository<TipoCuartoEntity, Long> {
}
