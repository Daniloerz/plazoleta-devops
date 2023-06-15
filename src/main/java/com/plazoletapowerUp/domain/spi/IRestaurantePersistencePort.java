package com.plazoletapowerUp.domain.spi;


import com.plazoletapowerUp.domain.model.RestauranteModel;
import com.plazoletapowerUp.domain.model.RestaurantePageableModel;

public interface IRestaurantePersistencePort {
    RestauranteModel saveRestaurante(RestauranteModel restauranteModel);
    RestauranteModel findByIdPP(Integer id);
    RestaurantePageableModel findAllRestaurantesPP(Integer page);

}