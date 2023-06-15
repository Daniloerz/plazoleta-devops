package com.plazoletapowerUp.domain.spi;

import com.plazoletapowerUp.domain.model.PlatosModel;
import com.plazoletapowerUp.domain.model.PlatosRestaurantePageableModel;

public interface IPlatosPersistencePort {
    PlatosModel savePlatoPP(PlatosModel platosModel);
    PlatosModel findPlatoById(PlatosModel platosModel);
    PlatosModel findPlatoById(Integer id);
    PlatosRestaurantePageableModel findPlatosByIdAndPageable(Integer id, Integer initPage, Integer numElementPage);
}
