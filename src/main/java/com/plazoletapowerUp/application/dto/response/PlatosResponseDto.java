package com.plazoletapowerUp.application.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlatosResponseDto {

    private Integer id;
    private String nombrePlato;
    private Integer precio;
    private String descripcionPlato;
    private String urlImagen;
    @JsonIgnore
    private String nombreCategoria;
}
