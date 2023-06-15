package com.plazoletapowerUp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteEmpleadoModel {
    private Integer id;
    private Integer idRestaurante;
    private Integer idUsuario;
}
