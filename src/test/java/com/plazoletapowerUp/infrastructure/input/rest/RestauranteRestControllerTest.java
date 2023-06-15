package com.plazoletapowerUp.infrastructure.input.rest;

import com.plazoletapowerUp.application.dto.request.RestauranteRequestDto;
import com.plazoletapowerUp.application.dto.response.RestaurantePageResponseDto;
import com.plazoletapowerUp.application.handler.IRestauranteHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteRestControllerTest {
    private IRestauranteHandler restauranteHandler;
    private RestauranteRestController restauranteRestController;

    @BeforeEach
    public void setUp() {
        restauranteHandler = Mockito.mock(IRestauranteHandler.class);
        restauranteRestController = new RestauranteRestController(restauranteHandler);
    }

    @Test
    public void testCreateRestaurante() {
        RestauranteRequestDto restauranteRequestDto = new RestauranteRequestDto();

        ResponseEntity<Void> response = restauranteRestController.createRestaurante(restauranteRequestDto);

        Mockito.verify(restauranteHandler).saveRestaurante(restauranteRequestDto);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testFindAllRestaurantes() {
        int page = 1;
        RestaurantePageResponseDto restaurantePageResponseDto = new RestaurantePageResponseDto();

        Mockito.when(restauranteHandler.findAllRestaurantes(page)).thenReturn(restaurantePageResponseDto);

        ResponseEntity<RestaurantePageResponseDto> response = restauranteRestController.findAllRestaurantes(page);

        Mockito.verify(restauranteHandler).findAllRestaurantes(page);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(restaurantePageResponseDto, response.getBody());
    }

    @Test
    public void testFindAllRestaurantes_NotFound() {
        int page = 1;

        Mockito.when(restauranteHandler.findAllRestaurantes(page)).thenReturn(null);

        ResponseEntity<RestaurantePageResponseDto> response = restauranteRestController.findAllRestaurantes(page);

        Mockito.verify(restauranteHandler).findAllRestaurantes(page);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }
}