package com.plazoletapowerUp.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PedidosPlatos", schema = "food_court")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PedidoPlatoEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_pedido_plato")
    private Integer idPedidoPlato;
    private Integer idPedido;
    private Integer idPlato;
    private Integer cantidad;

}

