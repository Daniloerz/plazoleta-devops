package com.plazoletapowerUp.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatosRequestDto {
    private Integer idRestaurante;
    private String nombre;
    private Integer precio;
    private String descripcion;
    private String urlImagen;
    private Integer idCategoria;
}

