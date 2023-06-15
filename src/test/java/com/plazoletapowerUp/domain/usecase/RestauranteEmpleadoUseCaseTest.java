package com.plazoletapowerUp.domain.usecase;

import com.plazoletapowerUp.domain.client.IUsuarioClientPort;
import com.plazoletapowerUp.domain.exception.ValidationException;
import com.plazoletapowerUp.domain.model.RestauranteEmpleadoModel;
import com.plazoletapowerUp.domain.model.RestauranteModel;
import com.plazoletapowerUp.domain.responseDtoModel.RoleResponseDtoModel;
import com.plazoletapowerUp.domain.responseDtoModel.UsuarioResponseDtoModel;
import com.plazoletapowerUp.domain.spi.IRestauranteEmpleadoPersistencePort;
import com.plazoletapowerUp.domain.spi.IRestaurantePersistencePort;
import com.plazoletapowerUp.infrastructure.enums.RoleEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteEmpleadoUseCaseTest {

    private RestauranteEmpleadoUseCase restauranteEmpleadoUseCase;
    private IRestauranteEmpleadoPersistencePort restauranteEmpleadoPersistencePort;
    private IUsuarioClientPort usuarioClientPort;
    private IRestaurantePersistencePort restaurantePersistencePort;

    @BeforeEach
    public void setUp() {
        restauranteEmpleadoPersistencePort = Mockito.mock(IRestauranteEmpleadoPersistencePort.class);
        usuarioClientPort = Mockito.mock(IUsuarioClientPort.class);
        restaurantePersistencePort = Mockito.mock(IRestaurantePersistencePort.class);
        restauranteEmpleadoUseCase = new RestauranteEmpleadoUseCase(
                restauranteEmpleadoPersistencePort,
                usuarioClientPort,
                restaurantePersistencePort
        );
    }

    @Test
    public void testSaveRestauranteEmpleado_InvalidUsuario_ValidationExceptionThrown() {
        RestauranteEmpleadoModel restauranteEmpleadoModel = new RestauranteEmpleadoModel();
        restauranteEmpleadoModel.setIdUsuario(1);
        restauranteEmpleadoModel.setIdRestaurante(1);

        Mockito.when(usuarioClientPort.findUsuarioById(1)).thenReturn(null);

        Assertions.assertThrows(ValidationException.class, () -> restauranteEmpleadoUseCase.saveRestauranteEmpleado(restauranteEmpleadoModel));

        Mockito.verify(usuarioClientPort).findUsuarioById(1);
        Mockito.verifyNoInteractions(restaurantePersistencePort, restauranteEmpleadoPersistencePort);
    }

    @Test
    public void testSaveRestauranteEmpleado_EmpleadoAlreadyExists_ValidationExceptionThrown() {
        RestauranteEmpleadoModel restauranteEmpleadoModel = new RestauranteEmpleadoModel();
        restauranteEmpleadoModel.setIdUsuario(1);
        restauranteEmpleadoModel.setIdRestaurante(1);

        Mockito.when(usuarioClientPort.findUsuarioById(1)).thenReturn(createUsuarioResponseDtoModel(RoleEnum.EMPLEADO.getDbName()));
        Mockito.when(restaurantePersistencePort.findByIdPP(1)).thenReturn(new RestauranteModel());
        Mockito.when(restauranteEmpleadoPersistencePort.findEmpleadoByIdAndIdRestaurante(1, 1)).thenReturn(restauranteEmpleadoModel);

        Assertions.assertThrows(ValidationException.class, () -> restauranteEmpleadoUseCase.saveRestauranteEmpleado(restauranteEmpleadoModel));

        Mockito.verify(usuarioClientPort).findUsuarioById(1);
        Mockito.verify(restaurantePersistencePort).findByIdPP(1);
        Mockito.verify(restauranteEmpleadoPersistencePort).findEmpleadoByIdAndIdRestaurante(1, 1);
        Mockito.verifyNoMoreInteractions(restauranteEmpleadoPersistencePort);
    }

    @Test
    public void testSaveRestauranteEmpleado_ValidRestauranteEmpleado_SaveSuccessful() {
        RestauranteEmpleadoModel restauranteEmpleadoModel = new RestauranteEmpleadoModel();
        restauranteEmpleadoModel.setIdUsuario(1);
        restauranteEmpleadoModel.setIdRestaurante(1);

        Mockito.when(usuarioClientPort.findUsuarioById(1)).thenReturn(createUsuarioResponseDtoModel(RoleEnum.EMPLEADO.getDbName()));
        Mockito.when(restaurantePersistencePort.findByIdPP(1)).thenReturn(new RestauranteModel());
        Mockito.when(restauranteEmpleadoPersistencePort.findEmpleadoByIdAndIdRestaurante(1, 1)).thenReturn(null);

        Assertions.assertDoesNotThrow(() -> restauranteEmpleadoUseCase.saveRestauranteEmpleado(restauranteEmpleadoModel));

        Mockito.verify(usuarioClientPort).findUsuarioById(1);
        Mockito.verify(restaurantePersistencePort).findByIdPP(1);
        Mockito.verify(restauranteEmpleadoPersistencePort).findEmpleadoByIdAndIdRestaurante(1, 1);
        Mockito.verify(restauranteEmpleadoPersistencePort).saveRestauranteEmpleado(restauranteEmpleadoModel);
    }

    private UsuarioResponseDtoModel createUsuarioResponseDtoModel(String role) {
        UsuarioResponseDtoModel usuarioResponseDtoModel = new UsuarioResponseDtoModel();
        RoleResponseDtoModel roleDtoModel = new RoleResponseDtoModel();
        roleDtoModel.setNombre(role);
        usuarioResponseDtoModel.setRole(roleDtoModel);
        return usuarioResponseDtoModel;
    }

}