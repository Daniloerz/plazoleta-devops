package com.plazoletapowerUp.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Platos", schema = "food_court")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlatoEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id_plato")
    private Integer id;
    private String nombre;
    private Integer precio;
    private String descripcion;
    private String urlImagen;
    private Integer idCategoria;
    private boolean activo;
    private Integer idRestaurante;
}

