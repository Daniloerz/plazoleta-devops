package com.plazoletapowerUp.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "Pedidos", schema = "food_court")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PedidoEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_pedido")
    private Integer idPedido;
    private Integer idCliente;
    private Date fecha;
    private String estado;
    private Integer idEmpleado;
    private Integer idRestaurante;
    private String codigoEntrega;
}
