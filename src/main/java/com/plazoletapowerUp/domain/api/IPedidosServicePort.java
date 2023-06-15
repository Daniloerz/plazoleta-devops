package com.plazoletapowerUp.domain.api;

import com.plazoletapowerUp.domain.model.PedidoModel;
import com.plazoletapowerUp.domain.model.PedidoPlatosModel;
import com.plazoletapowerUp.domain.model.PedidosPageableModel;

import java.util.List;

public interface IPedidosServicePort {
    void savePedidoSP(PedidoModel pedidoModel, List<PedidoPlatosModel> pedidoPlatosModelList);

    PedidosPageableModel findPedidosByEstado(Integer idEmpleado,
                                             Integer idRestaurante,
                                             String estado,
                                             Integer page,
                                             Integer numElemPage);

    void updateEmpleadoAndEstado(Integer idEmpleado, Integer idPedido, String estado);

    void updatePedidoToReady(Integer idPedido);

    void updatePedidoToDelivered(String codigoEntrega);

    void cancelarPedido(Integer idPedido);
}
