package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.EstadoReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstadoReservaRepository extends JpaRepository<EstadoReservaEntity, Integer> {

}
