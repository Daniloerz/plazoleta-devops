package com.plazoletapowerUp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoPlatosModel {
    private Integer idPedidoPlato;
    private Integer idPedido;
    private Integer idPlato;
    private Integer cantidad;
}
