package com.plazoletapowerUp.infrastructure.input.rest;

import com.plazoletapowerUp.application.dto.request.PedidoRequestDto;
import com.plazoletapowerUp.application.dto.response.PedidoPageResponseDto;
import com.plazoletapowerUp.application.handler.IPedidosHandler;
import com.plazoletapowerUp.infrastructure.enums.PedidoEstadoEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class PedidosRestControllerTests {

    private IPedidosHandler pedidosHandler;
    private PedidosRestController pedidosRestController;

    @BeforeEach
    public void setUp() {
        pedidosHandler = Mockito.mock(IPedidosHandler.class);
        pedidosRestController = new PedidosRestController(pedidosHandler);
    }

    @Test
    public void testCreatePedido() {
        PedidoRequestDto pedidoRequestDto = new PedidoRequestDto();

        ResponseEntity<Void> response = pedidosRestController.createPedido(pedidoRequestDto);

        Mockito.verify(pedidosHandler).savePedido(pedidoRequestDto);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetPedidosByEstado_OrderFound() {
        Integer idRestaurante = 1;
        Integer idEmpleado = 2;
        String estado = "pendiente";
        Integer page = 1;
        Integer numElementsPage = 10;

        PedidoPageResponseDto pedidoPageResponseDto = new PedidoPageResponseDto();

        Mockito.when(pedidosHandler.findPedidosByEstado(idEmpleado, idRestaurante, estado, page, numElementsPage))
                .thenReturn(pedidoPageResponseDto);

        ResponseEntity<PedidoPageResponseDto> response = pedidosRestController.getPedidosByEstado(idRestaurante,
                idEmpleado, estado, page, numElementsPage);

        Mockito.verify(pedidosHandler).findPedidosByEstado(idEmpleado, idRestaurante, estado, page, numElementsPage);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(pedidoPageResponseDto, response.getBody());
    }

    @Test
    public void testGetPedidosByEstado_OrdersNotFound() {
        Integer idRestaurante = 1;
        Integer idEmpleado = 2;
        String estado = "pendiente";
        Integer page = 1;
        Integer numElementsPage = 10;

        Mockito.when(pedidosHandler.findPedidosByEstado(idEmpleado, idRestaurante, estado, page, numElementsPage))
                .thenReturn(null);

        ResponseEntity<PedidoPageResponseDto> response = pedidosRestController.getPedidosByEstado(idRestaurante,
                idEmpleado, estado, page, numElementsPage);

        Mockito.verify(pedidosHandler).findPedidosByEstado(idEmpleado, idRestaurante, estado, page, numElementsPage);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void testUpdateEmpleadoAndEstado() {
        Integer idPedido = 1;
        Integer idChef = 2;

        ResponseEntity<Void> response = pedidosRestController.updateEmpleadoAndEstado(idPedido, idChef);

        Mockito.verify(pedidosHandler).updateIdEmpleado(idChef, idPedido, PedidoEstadoEnum.EN_PREPARACION.getDbValue());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdatePedidoToReady() {
        Integer idPedido = 1;

        ResponseEntity<Void> response = pedidosRestController.updatePedidoToReady(idPedido);

        Mockito.verify(pedidosHandler).updatePedidoToReady(idPedido);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdatePedidoToDelivered() {
        String codigoEntrega = "ABC123";

        ResponseEntity<Void> response = pedidosRestController.updatePedidoToDelivered(codigoEntrega);

        Mockito.verify(pedidosHandler).updatePedidoToDelivered(codigoEntrega);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCancelarPedido() {
        Integer idPedido = 1;

        ResponseEntity<Void> response = pedidosRestController.cancelarPedido(idPedido);

        Mockito.verify(pedidosHandler).cancelarPedido(idPedido);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}