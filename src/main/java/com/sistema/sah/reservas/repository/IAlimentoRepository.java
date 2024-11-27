package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.AlimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAlimentoRepository extends JpaRepository<AlimentoEntity,String> {
}
