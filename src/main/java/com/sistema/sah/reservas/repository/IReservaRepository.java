package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("reserva")
public interface IReservaRepository extends JpaRepository<ReservaEntity, String> {
}
