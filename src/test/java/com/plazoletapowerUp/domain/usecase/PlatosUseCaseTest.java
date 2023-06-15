package com.plazoletapowerUp.domain.usecase;

import com.plazoletapowerUp.domain.client.IUsuarioClientPort;
import com.plazoletapowerUp.domain.exception.ValidationException;
import com.plazoletapowerUp.domain.model.CategoriaModel;
import com.plazoletapowerUp.domain.model.PlatosModel;
import com.plazoletapowerUp.domain.model.PlatosRestaurantePageableModel;
import com.plazoletapowerUp.domain.model.RestauranteModel;
import com.plazoletapowerUp.domain.responseDtoModel.RoleResponseDtoModel;
import com.plazoletapowerUp.domain.responseDtoModel.UsuarioResponseDtoModel;
import com.plazoletapowerUp.domain.spi.ICategoriaPersistencePort;
import com.plazoletapowerUp.domain.spi.IPlatosPersistencePort;
import com.plazoletapowerUp.domain.spi.IRestaurantePersistencePort;
import com.plazoletapowerUp.infrastructure.enums.RoleEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlatosUseCaseTest {

    private IPlatosPersistencePort platosPersistencePort;
    private ICategoriaPersistencePort categoriaPersistencePort;
    private IRestaurantePersistencePort restaurantePersistencePort;
    private IUsuarioClientPort usuarioClientPort;
    private PlatosUseCase platosUseCase;

    @BeforeEach
    public void setup() {
        platosPersistencePort = Mockito.mock(IPlatosPersistencePort.class);
        categoriaPersistencePort = Mockito.mock(ICategoriaPersistencePort.class);
        restaurantePersistencePort = Mockito.mock(IRestaurantePersistencePort.class);
        usuarioClientPort = Mockito.mock(IUsuarioClientPort.class);

        platosUseCase = new PlatosUseCase(platosPersistencePort, categoriaPersistencePort, restaurantePersistencePort, usuarioClientPort);
    }

    @Test
    public void testFindPlatosByRestaurante_ValidParameters_ReturnPlatosRestaurantePageableModel() {
        Integer id = 1;
        Integer initPage = 0;
        Integer numElementsPage = 10;

        PlatosRestaurantePageableModel pageableModel = new PlatosRestaurantePageableModel();

        when(platosPersistencePort.findPlatosByIdAndPageable(id, initPage, numElementsPage)).thenReturn(pageableModel);

        PlatosRestaurantePageableModel result = platosUseCase.findPlatosByRestaurante(id, initPage, numElementsPage);

        Assertions.assertEquals(pageableModel, result);

        verify(platosPersistencePort).findPlatosByIdAndPageable(id, initPage, numElementsPage);
    }

}