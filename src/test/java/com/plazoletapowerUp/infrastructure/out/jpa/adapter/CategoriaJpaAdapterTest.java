package com.plazoletapowerUp.infrastructure.out.jpa.adapter;

import com.plazoletapowerUp.domain.model.CategoriaModel;
import com.plazoletapowerUp.infrastructure.exception.NoDataFoundException;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.CategoriaEntity;
import com.plazoletapowerUp.infrastructure.out.jpa.mapper.ICategoriaEntityMapper;
import com.plazoletapowerUp.infrastructure.out.jpa.repository.ICategoriaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaJpaAdapterTest {

    private ICategoriaRepository categoriaRepository;
    private ICategoriaEntityMapper categoriaEntityMapper;
    private CategoriaJpaAdapter categoriaJpaAdapter;

    @BeforeEach
    public void setUp() {
        categoriaRepository = Mockito.mock(ICategoriaRepository.class);
        categoriaEntityMapper = Mockito.mock(ICategoriaEntityMapper.class);
        categoriaJpaAdapter = new CategoriaJpaAdapter(categoriaRepository, categoriaEntityMapper);
    }

    @Test
    public void testFindById_Success() {
        Integer id = 1;
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        CategoriaModel categoriaModel = new CategoriaModel();

        Mockito.when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoriaEntity));
        Mockito.when(categoriaEntityMapper.toCategoriaModel(categoriaEntity)).thenReturn(categoriaModel);

        CategoriaModel result = categoriaJpaAdapter.findById(id);

        Mockito.verify(categoriaRepository).findById(id);
        Mockito.verify(categoriaEntityMapper).toCategoriaModel(categoriaEntity);
        Assertions.assertEquals(categoriaModel, result);
    }

    @Test
    public void testFindById_NotFound() {
        Integer id = 1;

        Mockito.when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoDataFoundException.class, () -> {
            categoriaJpaAdapter.findById(id);
        });

        Mockito.verify(categoriaRepository).findById(id);
        Mockito.verifyNoInteractions(categoriaEntityMapper);
    }
}