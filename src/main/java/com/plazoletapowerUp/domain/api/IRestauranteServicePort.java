package com.plazoletapowerUp.domain.api;


import com.plazoletapowerUp.domain.model.RestauranteModel;
import com.plazoletapowerUp.domain.model.RestaurantePageableModel;

public interface IRestauranteServicePort {

    void saveRestauranteSP(RestauranteModel restauranteModel);
    RestaurantePageableModel findAllRestaurantesSP(Integer page);
}