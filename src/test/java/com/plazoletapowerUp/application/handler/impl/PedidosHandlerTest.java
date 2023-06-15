package com.plazoletapowerUp.application.handler.impl;

import com.plazoletapowerUp.application.dto.request.PedidoRequestDto;
import com.plazoletapowerUp.application.dto.response.PedidoPageResponseDto;
import com.plazoletapowerUp.application.dto.response.PedidoResponseDto;
import com.plazoletapowerUp.application.mapper.IPedidoRequestMapper;
import com.plazoletapowerUp.domain.api.IPedidosServicePort;
import com.plazoletapowerUp.domain.model.PedidoModel;
import com.plazoletapowerUp.domain.model.PedidoPlatosModel;
import com.plazoletapowerUp.domain.model.PedidosPageableModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PedidosHandlerTest {

    private IPedidosServicePort pedidosServicePort = Mockito.mock(IPedidosServicePort.class);
    private IPedidoRequestMapper pedidoRequestMapper = Mockito.mock(IPedidoRequestMapper.class);
    private PedidosHandler pedidosHandler = new PedidosHandler(pedidosServicePort, pedidoRequestMapper);

    @Test
    public void testSavePedido() {
        PedidoRequestDto pedidoRequestDto = new PedidoRequestDto();
        pedidoRequestDto.setIdCliente(1);
        pedidoRequestDto.setIdRestaurante(2);
        PedidoModel pedidoModel = new PedidoModel();
        pedidoModel.setIdCliente(1);
        pedidoModel.setIdRestaurante(2);
        List<PedidoPlatosModel> pedidoPlatosModelList = new ArrayList<>();
        when(pedidoRequestMapper.toPedidoPlatosModel(any())).thenReturn(pedidoPlatosModelList);
        pedidosHandler.savePedido(pedidoRequestDto);
        verify(pedidosServicePort).savePedidoSP(any(PedidoModel.class), any(List.class));
    }

    @Test
    public void testFindPedidosByEstado() {
        Integer idEmpleado = 1;
        Integer idRestaurante = 2;
        String estado = "READY";
        Integer page = 1;
        Integer numElemPage = 10;
        PedidosPageableModel pageableModel = new PedidosPageableModel();
        pageableModel.setPagesAmount(1);
        List<PedidoModel> pedidoModelList = new ArrayList<>();
        pedidoModelList.add(new PedidoModel());
        pageableModel.setPedidoModelList(pedidoModelList);
        List<PedidoResponseDto> pedidoResponseDtoList = new ArrayList<>();
        when(pedidosServicePort.findPedidosByEstado(idEmpleado, idRestaurante, estado, page, numElemPage))
                .thenReturn(pageableModel);
        when(pedidoRequestMapper.toPedidoResponseDto(any())).thenReturn(pedidoResponseDtoList);
        PedidoPageResponseDto result = pedidosHandler.findPedidosByEstado(idEmpleado, idRestaurante, estado, page, numElemPage);
        assertEquals(page, result.getPage());
        assertEquals(numElemPage, result.getPageSize());
        assertEquals(1, result.getTotalPages());
        assertEquals(idRestaurante, 2);
    }

    @Test
    public void testUpdateIdEmpleado() {
        Integer idEmpleado = 1;
        Integer idPedido = 2;
        String estado = "READY";
        pedidosHandler.updateIdEmpleado(idEmpleado, idPedido, estado);
        verify(pedidosServicePort).updateEmpleadoAndEstado(idEmpleado, idPedido, estado);
    }

    @Test
    public void testUpdatePedidoToReady() {
        Integer idPedido = 1;
        pedidosHandler.updatePedidoToReady(idPedido);
        verify(pedidosServicePort).updatePedidoToReady(idPedido);
    }

    @Test
    void testUpdatePedidoToDelivered() {
        String codigoEntrega = "ABC123";

        pedidosHandler.updatePedidoToDelivered(codigoEntrega);

        verify(pedidosServicePort).updatePedidoToDelivered(codigoEntrega);
    }

    @Test
    void testCancelarPedido() {
        Integer idPedido = 123;

        pedidosHandler.cancelarPedido(idPedido);

        verify(pedidosServicePort).cancelarPedido(idPedido);
    }
}