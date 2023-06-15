package com.plazoletapowerUp.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDto {
    private Integer idPedido;
    private Integer idCliente;
    private Date fecha;
    private String estado;
    private Integer idEmpleado;
    private Integer idRestaurante;
}

