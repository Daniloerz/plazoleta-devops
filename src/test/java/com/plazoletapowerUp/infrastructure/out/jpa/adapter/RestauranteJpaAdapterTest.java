package com.plazoletapowerUp.infrastructure.out.jpa.adapter;

import com.plazoletapowerUp.domain.model.RestauranteModel;
import com.plazoletapowerUp.infrastructure.exception.NoDataFoundException;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.RestauranteEntity;
import com.plazoletapowerUp.infrastructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.plazoletapowerUp.infrastructure.out.jpa.repository.IRestauranteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteJpaAdapterTest {
    @Mock
    private IRestauranteRepository restauranteRepository;

    @Mock
    private IRestauranteEntityMapper restauranteEntityMapper;

    private RestauranteJpaAdapter restauranteJpaAdapter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        restauranteJpaAdapter = new RestauranteJpaAdapter(restauranteRepository, restauranteEntityMapper);
    }

    @Test
    public void testSaveRestaurante_Success() {
        RestauranteModel restauranteModel = new RestauranteModel();
        RestauranteEntity restauranteEntity = new RestauranteEntity();

        Mockito.when(restauranteEntityMapper.toEntity(restauranteModel)).thenReturn(restauranteEntity);
        Mockito.when(restauranteRepository.save(restauranteEntity)).thenReturn(restauranteEntity);
        Mockito.when(restauranteEntityMapper.toRestauranteModel(restauranteEntity)).thenReturn(restauranteModel);

        RestauranteModel result = restauranteJpaAdapter.saveRestaurante(restauranteModel);

        Mockito.verify(restauranteRepository).save(restauranteEntity);
        Mockito.verify(restauranteEntityMapper).toEntity(restauranteModel);
        Mockito.verify(restauranteEntityMapper).toRestauranteModel(restauranteEntity);
        Assertions.assertEquals(restauranteModel, result);
    }

    @Test
    public void testFindByIdPP_ExistingRestaurante_Success() {
        Integer id = 1;
        RestauranteModel expectedModel = new RestauranteModel();
        RestauranteEntity restauranteEntity = new RestauranteEntity();

        Mockito.when(restauranteRepository.findById(id)).thenReturn(Optional.of(restauranteEntity));
        Mockito.when(restauranteEntityMapper.toRestauranteModel(restauranteEntity)).thenReturn(expectedModel);

        RestauranteModel result = restauranteJpaAdapter.findByIdPP(id);

        Mockito.verify(restauranteRepository).findById(id);
        Mockito.verify(restauranteEntityMapper).toRestauranteModel(restauranteEntity);
        Assertions.assertEquals(expectedModel, result);
    }

    @Test
    public void testFindByIdPP_NonExistingRestaurante_ExceptionThrown() {
        Integer id = 1;

        Mockito.when(restauranteRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoDataFoundException.class, () -> restauranteJpaAdapter.findByIdPP(id));

        Mockito.verify(restauranteRepository).findById(id);
    }

}