package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.CuartoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface ICuartoRepository extends JpaRepository<CuartoEntity, String> {

    @Query("""
        SELECT c.valorNocheCuarto, c.tipoCuartoEntityFk.id FROM CuartoEntity c where c.codigoCuarto = :codigoCuarto
    """)
    List<Object[]> findByCuartoValorNoche(@Param("codigoCuarto") String codigoCuarto);

}
