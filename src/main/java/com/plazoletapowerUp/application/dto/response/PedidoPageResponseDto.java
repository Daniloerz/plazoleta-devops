package com.plazoletapowerUp.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoPageResponseDto {
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private Integer idRestaurante;
    private List<PedidoResponseDto> pedidoResponseDtoList;
}
