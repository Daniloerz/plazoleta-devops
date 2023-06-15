package com.plazoletapowerUp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidosPageableModel {
    private Integer pagesAmount;
    private List<PedidoModel> pedidoModelList;
}
