package com.plazoletapowerUp.infrastructure.input.rest;

import com.plazoletapowerUp.application.dto.request.PlatosRequestDescriptionDto;
import com.plazoletapowerUp.application.dto.request.PlatosRequestDto;
import com.plazoletapowerUp.application.dto.request.PlatosRequestPriceDto;
import com.plazoletapowerUp.application.dto.response.PlatosPageResponseDto;
import com.plazoletapowerUp.application.handler.IPlatosHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class PlatosRestControllerTest {

    private IPlatosHandler platosHandler;
    private PlatosRestController platosRestController;

    @BeforeEach
    public void setUp() {
        platosHandler = Mockito.mock(IPlatosHandler.class);
        platosRestController = new PlatosRestController(platosHandler);
    }

    @Test
    public void testCreatePlato() {
        Integer idPropietario = 1;
        PlatosRequestDto platosRequestDto = new PlatosRequestDto();

        ResponseEntity<Void> response = platosRestController.createPlato(platosRequestDto, idPropietario);

        Mockito.verify(platosHandler).savePlatos(platosRequestDto, idPropietario);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateDishPrice() {
        Integer idPlato = 1;
        PlatosRequestPriceDto platosRequestPriceDto = new PlatosRequestPriceDto();

        ResponseEntity<Void> response = platosRestController.updateDishPrice(idPlato, platosRequestPriceDto);

        platosRequestPriceDto.setId(idPlato);
        Mockito.verify(platosHandler).updatePlatoByPrice(platosRequestPriceDto);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateDishDescription() {
        Integer idPlato = 1;
        PlatosRequestDescriptionDto platosRequestDescriptionDto = new PlatosRequestDescriptionDto();

        ResponseEntity<Void> response = platosRestController.updateDishDescription(idPlato, platosRequestDescriptionDto);

        platosRequestDescriptionDto.setId(idPlato);
        Mockito.verify(platosHandler).updatePlatoByDescription(platosRequestDescriptionDto);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateDishActive() {
        Integer idPropietario = 1;
        Integer idPlato = 2;
        Boolean isActive = true;

        ResponseEntity<Void> response = platosRestController.updateDishActive(idPropietario, idPlato, isActive);

        Mockito.verify(platosHandler).updatePlatoActive(idPropietario, idPlato, isActive);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindAllByRestaurante() {
        Integer idRestaurante = 1;
        Integer initPage = 1;
        Integer numElementsPage = 10;

        PlatosPageResponseDto platosPageResponseDto = new PlatosPageResponseDto();
        Mockito.when(platosHandler.findPlatosByIdRestaurante(idRestaurante, initPage, numElementsPage))
                .thenReturn(platosPageResponseDto);

        ResponseEntity<PlatosPageResponseDto> response = platosRestController.findAllByRestaurante(idRestaurante, initPage, numElementsPage);

        Mockito.verify(platosHandler).findPlatosByIdRestaurante(idRestaurante, initPage, numElementsPage);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(platosPageResponseDto, response.getBody());
    }

}