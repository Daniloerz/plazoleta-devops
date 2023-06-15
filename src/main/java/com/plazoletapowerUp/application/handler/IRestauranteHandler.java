package com.plazoletapowerUp.application.handler;


import com.plazoletapowerUp.application.dto.request.RestauranteRequestDto;
import com.plazoletapowerUp.application.dto.response.RestaurantePageResponseDto;

public interface IRestauranteHandler {
    void saveRestaurante(RestauranteRequestDto restauranteRequestDto);
    RestaurantePageResponseDto findAllRestaurantes(Integer page);
}
