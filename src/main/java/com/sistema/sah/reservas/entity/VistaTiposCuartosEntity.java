package com.sistema.sah.reservas.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vista_tipos_cuarto", schema = "sah")
public class VistaTiposCuartosEntity {

    /**
     * Identificador único del tipo de cuarto.
     * Este campo es la clave primaria de la entidad y representa el identificador único del tipo de cuarto en la tabla {@code tipo_cuarto}.
     * El valor se genera automáticamente mediante una secuencia en la base de datos.
     */
    @Id
    @Column(name = "id_tipo_cuarto")
    private Integer id;

    /**
     * Nombre del tipo de cuarto.
     * Este campo almacena el nombre del tipo de cuarto, como "Simple", "Doble", "Suite", etc. Es un campo obligatorio.
     */
    @Column(name = "nombre_tipo_cuarto")
    private String nombreTipoCuarto;

}
