package com.plazoletapowerUp.infrastructure.out.jpa.adapter;

import com.plazoletapowerUp.domain.model.RestauranteEmpleadoModel;
import com.plazoletapowerUp.infrastructure.exception.NoDataFoundException;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.RestauranteEmpleadoEntity;
import com.plazoletapowerUp.infrastructure.out.jpa.mapper.IRestauranteEmpleadoEntityMapper;
import com.plazoletapowerUp.infrastructure.out.jpa.repository.IRestauranteEmpleadoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteEmpleadoJpaAdapterTest {
    private RestauranteEmpleadoJpaAdapter restauranteEmpleadoJpaAdapter;

    @Mock
    private IRestauranteEmpleadoEntityMapper restauranteEmpleadoEntityMapper;

    @Mock
    private IRestauranteEmpleadoRepository restauranteEmpleadoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        restauranteEmpleadoJpaAdapter = new RestauranteEmpleadoJpaAdapter(restauranteEmpleadoEntityMapper, restauranteEmpleadoRepository);
    }

    @Test
    public void testSaveRestauranteEmpleado() {
        RestauranteEmpleadoModel restauranteEmpleadoModel = new RestauranteEmpleadoModel();
        RestauranteEmpleadoEntity restauranteEmpleadoEntity = new RestauranteEmpleadoEntity();

        Mockito.when(restauranteEmpleadoEntityMapper.toRestauranteEmpleadoEntity(restauranteEmpleadoModel)).thenReturn(restauranteEmpleadoEntity);

        restauranteEmpleadoJpaAdapter.saveRestauranteEmpleado(restauranteEmpleadoModel);

        Mockito.verify(restauranteEmpleadoRepository).save(restauranteEmpleadoEntity);
    }

    @Test
    public void testFindEmpleadoByIdAndIdRestaurante_Success() {
        Integer idEmpleado = 1;
        Integer idRestaurante = 1;
        RestauranteEmpleadoModel expectedModel = new RestauranteEmpleadoModel();
        RestauranteEmpleadoEntity restauranteEmpleadoEntity = new RestauranteEmpleadoEntity();

        Mockito.when(restauranteEmpleadoRepository.getByIdUsuarioAndIdRestaurante(idEmpleado, idRestaurante)).thenReturn(restauranteEmpleadoEntity);
        Mockito.when(restauranteEmpleadoEntityMapper.toRestauranteEmpleadoModel(restauranteEmpleadoEntity)).thenReturn(expectedModel);

        RestauranteEmpleadoModel result = restauranteEmpleadoJpaAdapter.findEmpleadoByIdAndIdRestaurante(idEmpleado, idRestaurante);

        Mockito.verify(restauranteEmpleadoRepository).getByIdUsuarioAndIdRestaurante(idEmpleado, idRestaurante);
        Mockito.verify(restauranteEmpleadoEntityMapper).toRestauranteEmpleadoModel(restauranteEmpleadoEntity);
        Assertions.assertEquals(expectedModel, result);
    }

    @Test
    public void testFindEmpleadoByIdAndIdRestaurante_NoDataFound() {
        Integer idEmpleado = 1;
        Integer idRestaurante = 1;

        Mockito.when(restauranteEmpleadoRepository.getByIdUsuarioAndIdRestaurante(idEmpleado, idRestaurante)).thenReturn(null);

        Assertions.assertThrows(NoDataFoundException.class, () -> {
            restauranteEmpleadoJpaAdapter.findEmpleadoByIdAndIdRestaurante(idEmpleado, idRestaurante);
        });

        Mockito.verify(restauranteEmpleadoRepository).getByIdUsuarioAndIdRestaurante(idEmpleado, idRestaurante);
    }

    @Test
    public void testFindEmpleadoById_Success() {
        Integer idEmpleado = 1;
        RestauranteEmpleadoModel expectedModel = new RestauranteEmpleadoModel();
        RestauranteEmpleadoEntity restauranteEmpleadoEntity = new RestauranteEmpleadoEntity();

        Mockito.when(restauranteEmpleadoRepository.getByIdUsuario(idEmpleado)).thenReturn(restauranteEmpleadoEntity);
        Mockito.when(restauranteEmpleadoEntityMapper.toRestauranteEmpleadoModel(restauranteEmpleadoEntity)).thenReturn(expectedModel);

        RestauranteEmpleadoModel result = restauranteEmpleadoJpaAdapter.findEmpleadoById(idEmpleado);

        Mockito.verify(restauranteEmpleadoRepository).getByIdUsuario(idEmpleado);
        Mockito.verify(restauranteEmpleadoEntityMapper).toRestauranteEmpleadoModel(restauranteEmpleadoEntity);
        Assertions.assertEquals(expectedModel, result);
    }

}