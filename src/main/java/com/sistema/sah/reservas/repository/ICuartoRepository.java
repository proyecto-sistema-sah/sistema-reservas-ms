package com.sistema.sah.reservas.repository;

import com.sistema.sah.commons.entity.CuartoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar operaciones relacionadas con la entidad {@link CuartoEntity}.
 * <p>
 * Proporciona métodos para realizar operaciones CRUD y consultas personalizadas sobre la tabla de cuartos.
 * </p>
 */
@Repository
public interface ICuartoRepository extends JpaRepository<CuartoEntity, String> {

    /**
     * Consulta el valor por noche y el identificador del tipo de cuarto asociado a un cuarto específico.
     *
     * @param codigoCuarto el código único del cuarto.
     * @return una lista de arreglos de objetos, donde cada arreglo contiene:
     * <ul>
     *     <li>El valor por noche del cuarto (como {@code Double}).</li>
     *     <li>El identificador del tipo de cuarto (como {@code Integer}).</li>
     * </ul>
     */
    @Query("""
        SELECT c.valorNocheCuarto, c.tipoCuartoEntityFk.id FROM CuartoEntity c WHERE c.codigoCuarto = :codigoCuarto
    """)
    List<Object[]> findByCuartoValorNoche(@Param("codigoCuarto") String codigoCuarto);

}
