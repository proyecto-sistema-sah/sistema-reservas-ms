package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.ReservaCuartoEntity;
import com.sistema.sah.commons.entity.embeddedid.ReservaCuartoIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservaCuartoRepository extends JpaRepository<ReservaCuartoEntity, ReservaCuartoIdEntity> {
}
