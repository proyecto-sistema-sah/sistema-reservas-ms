package com.sistema.sah.reservas.repository;

import com.sistema.sah.reservas.entity.VistaTiposCuartosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVistaTiposCuartosRepository extends JpaRepository<VistaTiposCuartosEntity, Integer> {
}
