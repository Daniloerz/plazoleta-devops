package com.plazoletapowerUp.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDto {

    private Integer idCliente;
    @JsonIgnore
    private Integer idEmpleado;
    private Integer idRestaurante;
    private List<PedidoPlatoRequestDto> pedidoPlatoRequestDtoList;
}
