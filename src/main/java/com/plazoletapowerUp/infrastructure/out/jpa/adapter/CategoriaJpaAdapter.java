package com.plazoletapowerUp.infrastructure.out.jpa.adapter;

import com.plazoletapowerUp.domain.model.CategoriaModel;
import com.plazoletapowerUp.domain.spi.ICategoriaPersistencePort;
import com.plazoletapowerUp.infrastructure.exception.NoDataFoundException;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.CategoriaEntity;
import com.plazoletapowerUp.infrastructure.out.jpa.mapper.ICategoriaEntityMapper;
import com.plazoletapowerUp.infrastructure.out.jpa.repository.ICategoriaRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
@RequiredArgsConstructor
public class CategoriaJpaAdapter implements ICategoriaPersistencePort {

    private final ICategoriaRepository categoriaRepository;
    private final ICategoriaEntityMapper categoriaEntityMapper;

    @Override
    public CategoriaModel findById(Integer id) {
        Optional<CategoriaEntity> categoriaEntityOptional = categoriaRepository.findById(id);
        if (categoriaEntityOptional.isPresent()){
            CategoriaEntity categoriaEntity = categoriaEntityOptional.get();
            return categoriaEntityMapper.toCategoriaModel(categoriaEntity);
        } else {
            throw new NoDataFoundException("Categoria no encontrada");
        }
    }
}
