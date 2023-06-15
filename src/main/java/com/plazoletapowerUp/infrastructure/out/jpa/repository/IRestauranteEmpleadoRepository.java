package com.plazoletapowerUp.infrastructure.out.jpa.repository;

import com.plazoletapowerUp.infrastructure.out.jpa.entity.RestauranteEmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRestauranteEmpleadoRepository extends JpaRepository<RestauranteEmpleadoEntity, Integer> {
    RestauranteEmpleadoEntity getByIdUsuarioAndIdRestaurante(Integer idEmpleado, Integer idRestaurante);
    RestauranteEmpleadoEntity getByIdUsuario(Integer idEmpleado);
}

