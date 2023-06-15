package com.plazoletapowerUp.domain.spi;

import com.plazoletapowerUp.domain.model.CategoriaModel;

public interface ICategoriaPersistencePort {
    CategoriaModel findById(Integer id);
}
