package com.plazoletapowerUp.infrastructure.input.rest;

import com.plazoletapowerUp.application.dto.request.RestauranteEmpleadoRequestDto;
import com.plazoletapowerUp.application.handler.IRestauranteEmpleadoHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteEmpleadoControllerTest {
    private IRestauranteEmpleadoHandler restauranteEmpleadoHandler;
    private RestauranteEmpleadoController restauranteEmpleadoController;

    @BeforeEach
    public void setUp() {
        restauranteEmpleadoHandler = Mockito.mock(IRestauranteEmpleadoHandler.class);
        restauranteEmpleadoController = new RestauranteEmpleadoController(restauranteEmpleadoHandler);
    }

    @Test
    public void testRestaurantEmployeeLink() {
        RestauranteEmpleadoRequestDto restauranteEmpleadoRequestDto = new RestauranteEmpleadoRequestDto();

        ResponseEntity<Void> response = restauranteEmpleadoController.restaurantEmployeeLink(restauranteEmpleadoRequestDto);

        Mockito.verify(restauranteEmpleadoHandler).saveRestauranteEmpleado(restauranteEmpleadoRequestDto);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}