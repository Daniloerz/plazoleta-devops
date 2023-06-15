package com.plazoletapowerUp.application.handler;

import com.plazoletapowerUp.application.dto.request.PedidoRequestDto;
import com.plazoletapowerUp.application.dto.response.PedidoPageResponseDto;

public interface IPedidosHandler {
    void savePedido(PedidoRequestDto pedidoRequestDto);

    PedidoPageResponseDto findPedidosByEstado
            (Integer idEmpleado, Integer idRestaurante, String estado, Integer page, Integer numElemPage);

    void updateIdEmpleado(Integer idEmpleado, Integer idPedido, String estado);

    void updatePedidoToReady(Integer idPedido);
    void updatePedidoToDelivered(String codigoEntrega);
    void cancelarPedido(Integer idPedido);
}
