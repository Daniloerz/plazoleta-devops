package com.plazoletapowerUp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlatoRestauranteModel {
    private Integer id;
    private String nombrePlato;
    private String nombreCategoria;
    private Integer precio;
    private String descripcionPlato;
    private String urlImagen;
    private String nombreRestaurante;
}
