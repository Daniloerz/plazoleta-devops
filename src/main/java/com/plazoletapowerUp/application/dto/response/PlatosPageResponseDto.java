package com.plazoletapowerUp.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PlatosPageResponseDto {

    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private String nombreRestaurante;
    private Map<String, List<PlatosResponseDto>> platosCategoriaMap;

}
