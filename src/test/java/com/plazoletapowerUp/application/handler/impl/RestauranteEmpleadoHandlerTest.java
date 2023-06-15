package com.plazoletapowerUp.application.handler.impl;

import com.plazoletapowerUp.application.dto.request.RestauranteEmpleadoRequestDto;
import com.plazoletapowerUp.application.mapper.IRestauranteEmpleadoRequestMapper;
import com.plazoletapowerUp.domain.api.IRestauranteEmpleadoServicePort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

class RestauranteEmpleadoHandlerTest {

    private IRestauranteEmpleadoServicePort restauranteEmpleadoServicePort =
            Mockito.mock(IRestauranteEmpleadoServicePort .class);

    private IRestauranteEmpleadoRequestMapper restauranteEmpleadoRequestMapper =
            Mockito.mock(IRestauranteEmpleadoRequestMapper .class);

    private RestauranteEmpleadoHandler restauranteEmpleadoHandler =
            new RestauranteEmpleadoHandler(restauranteEmpleadoServicePort, restauranteEmpleadoRequestMapper);


    @Test
    void testSaveRestauranteEmpleado() {
        RestauranteEmpleadoRequestDto requestDto = new RestauranteEmpleadoRequestDto();

        restauranteEmpleadoHandler.saveRestauranteEmpleado(requestDto);

        verify(restauranteEmpleadoServicePort).saveRestauranteEmpleado(
                restauranteEmpleadoRequestMapper.toRestauranteEmpleadoModel(requestDto));
    }
}