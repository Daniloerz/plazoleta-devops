package com.plazoletapowerUp.domain.usecase;

import com.plazoletapowerUp.domain.client.IUsuarioClientPort;
import com.plazoletapowerUp.domain.exception.ValidationException;
import com.plazoletapowerUp.domain.model.RestauranteModel;
import com.plazoletapowerUp.domain.model.RestaurantePageableModel;
import com.plazoletapowerUp.domain.responseDtoModel.RoleResponseDtoModel;
import com.plazoletapowerUp.domain.responseDtoModel.UsuarioResponseDtoModel;
import com.plazoletapowerUp.domain.spi.IRestaurantePersistencePort;
import com.plazoletapowerUp.infrastructure.enums.RoleEnum;
import com.plazoletapowerUp.infrastructure.out.feing.dto.UsuarioResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;

class RestauranteUseCaseTest {
    @Mock
    private IRestaurantePersistencePort restaurantePersistencePort;

    @Mock
    private IUsuarioClientPort usuarioClientPort;

    private RestauranteUseCase restauranteUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        restauranteUseCase = new RestauranteUseCase(restaurantePersistencePort, usuarioClientPort);
    }

    @Test
    public void testSaveRestauranteSP_InvalidNombre_ExceptionThrown() {
        RestauranteModel restauranteModel = new RestauranteModel();
        restauranteModel.setNombre("12345");
        restauranteModel.setTelefono("+123456789");
        restauranteModel.setNit("123456789");
        restauranteModel.setIdPropietario(1);

        Assertions.assertThrows(ValidationException.class, () -> restauranteUseCase.saveRestauranteSP(restauranteModel));

        Mockito.verifyNoInteractions(usuarioClientPort);
        Mockito.verifyNoInteractions(restaurantePersistencePort);
    }

    @Test
    public void testSaveRestauranteSP_InvalidTelefono_ExceptionThrown() {
        RestauranteModel restauranteModel = new RestauranteModel();
        restauranteModel.setNombre("Restaurante ABC");
        restauranteModel.setTelefono("abc");
        restauranteModel.setNit("123456789");
        restauranteModel.setIdPropietario(1);

        Assertions.assertThrows(ValidationException.class, () -> restauranteUseCase.saveRestauranteSP(restauranteModel));

        Mockito.verifyNoInteractions(usuarioClientPort);
        Mockito.verifyNoInteractions(restaurantePersistencePort);
    }

    @Test
    public void testSaveRestauranteSP_InvalidNit_ExceptionThrown() {
        RestauranteModel restauranteModel = new RestauranteModel();
        restauranteModel.setNombre("Restaurante ABC");
        restauranteModel.setTelefono("+123456789");
        restauranteModel.setNit("abc");
        restauranteModel.setIdPropietario(1);

        Assertions.assertThrows(ValidationException.class, () -> restauranteUseCase.saveRestauranteSP(restauranteModel));

        Mockito.verifyNoInteractions(usuarioClientPort);
        Mockito.verifyNoInteractions(restaurantePersistencePort);
    }

    @Test
    public void testSaveRestauranteSP_InvalidPropietarioRole_ExceptionThrown() {
        RestauranteModel restauranteModel = new RestauranteModel();
        restauranteModel.setNombre("Restaurante ABC");
        restauranteModel.setTelefono("+123456789");
        restauranteModel.setNit("123456789");
        restauranteModel.setIdPropietario(1);

        Mockito.when(usuarioClientPort.findUsuarioById(1)).thenReturn(createUsuarioResponseDtoModel("ADMIN"));

        Assertions.assertThrows(ValidationException.class, () -> restauranteUseCase.saveRestauranteSP(restauranteModel));

        Mockito.verify(usuarioClientPort).findUsuarioById(1);
        Mockito.verifyNoInteractions(restaurantePersistencePort);
    }

    @Test
    public void testFindAllRestaurantesSP_ValidPage_Success() {
        Integer page = 1;
        RestaurantePageableModel expectedModel = new RestaurantePageableModel();

        Mockito.when(restaurantePersistencePort.findAllRestaurantesPP(page)).thenReturn(expectedModel);

        RestaurantePageableModel result = restauranteUseCase.findAllRestaurantesSP(page);

        Mockito.verify(restaurantePersistencePort).findAllRestaurantesPP(page);
        Assertions.assertEquals(expectedModel, result);
    }

    private UsuarioResponseDtoModel createUsuarioResponseDtoModel(String roleName) {
        UsuarioResponseDtoModel usuarioResponseDtoModel = new UsuarioResponseDtoModel();
        RoleResponseDtoModel roleDtoModel = new RoleResponseDtoModel();
        roleDtoModel.setNombre(roleName);
        usuarioResponseDtoModel.setRole(roleDtoModel);
        return usuarioResponseDtoModel;
    }
}