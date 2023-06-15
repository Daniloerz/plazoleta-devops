package com.plazoletapowerUp.infrastructure.out.jpa.repository;

import com.plazoletapowerUp.infrastructure.out.jpa.entity.PedidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPedidosRepository extends JpaRepository<PedidoEntity, Integer> {
    List<PedidoEntity> findByIdCliente(Integer idCliente);
    Page<PedidoEntity> getByIdRestauranteAndEstado(Integer idRestaurante, String estado, Pageable page);
    PedidoEntity findByCodigoEntrega(String codigoEntrega);
}
