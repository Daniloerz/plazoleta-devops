package com.plazoletapowerUp.application.handler.impl;

import com.plazoletapowerUp.application.dto.request.RestauranteRequestDto;
import com.plazoletapowerUp.application.dto.response.RestaurantePageResponseDto;
import com.plazoletapowerUp.application.mapper.IRestauranteRequestMapper;
import com.plazoletapowerUp.domain.api.IRestauranteServicePort;
import com.plazoletapowerUp.domain.model.RestauranteModel;
import com.plazoletapowerUp.domain.model.RestaurantePageableModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestauranteHandlerTest {


    private IRestauranteServicePort restauranteServicePort = Mockito.mock(IRestauranteServicePort .class);

    private IRestauranteRequestMapper restauranteRequestMapper = Mockito.mock(IRestauranteRequestMapper.class);

    private RestauranteHandler restauranteHandler =
            new RestauranteHandler(restauranteServicePort, restauranteRequestMapper);



    @Test
    void testSaveRestaurante() {
        RestauranteRequestDto requestDto = new RestauranteRequestDto();
        RestauranteModel restauranteModel = new RestauranteModel();

        when(restauranteRequestMapper.toRestauranteModel(requestDto)).thenReturn(restauranteModel);

        restauranteHandler.saveRestaurante(requestDto);

        verify(restauranteServicePort).saveRestauranteSP(restauranteModel);
    }

    @Test
    void testFindAllRestaurantes() {
        Integer page = 1;
        RestaurantePageableModel pageableModel = new RestaurantePageableModel();
        RestaurantePageResponseDto expectedResponse = new RestaurantePageResponseDto();

        when(restauranteServicePort.findAllRestaurantesSP(page)).thenReturn(pageableModel);
        when(restauranteRequestMapper.toRestauranteResponseDtoList(pageableModel.getRestauranteModelList()))
                .thenReturn(expectedResponse.getRestauranteResponseDtoList());

        RestaurantePageResponseDto response = restauranteHandler.findAllRestaurantes(page);

        verify(restauranteServicePort).findAllRestaurantesSP(page);
        verify(restauranteRequestMapper).toRestauranteResponseDtoList(pageableModel.getRestauranteModelList());

    }
}