package com.plazoletapowerUp.domain.spi;

import com.plazoletapowerUp.domain.model.RestauranteEmpleadoModel;

public interface IRestauranteEmpleadoPersistencePort {

    void saveRestauranteEmpleado (RestauranteEmpleadoModel restauranteEmpleadoModel);
    RestauranteEmpleadoModel findEmpleadoByIdAndIdRestaurante (Integer idEmpleado, Integer idRestaurante);
    RestauranteEmpleadoModel findEmpleadoById (Integer idEmpleado);
}
