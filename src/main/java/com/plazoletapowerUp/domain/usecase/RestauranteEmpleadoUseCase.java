package com.plazoletapowerUp.domain.usecase;

import com.plazoletapowerUp.domain.api.IRestauranteEmpleadoServicePort;
import com.plazoletapowerUp.domain.client.IUsuarioClientPort;
import com.plazoletapowerUp.domain.exception.ValidationException;
import com.plazoletapowerUp.domain.model.RestauranteEmpleadoModel;
import com.plazoletapowerUp.domain.model.RestauranteModel;
import com.plazoletapowerUp.domain.responseDtoModel.UsuarioResponseDtoModel;
import com.plazoletapowerUp.domain.spi.IRestauranteEmpleadoPersistencePort;
import com.plazoletapowerUp.domain.spi.IRestaurantePersistencePort;
import com.plazoletapowerUp.infrastructure.enums.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestauranteEmpleadoUseCase implements IRestauranteEmpleadoServicePort {

    public final Logger log = LoggerFactory.getLogger(RestauranteEmpleadoUseCase.class);

    private final IRestauranteEmpleadoPersistencePort restauranteEmpleadoPersistencePort;
    private final IUsuarioClientPort usuarioClientPort;
    private final IRestaurantePersistencePort restaurantePersistencePort;

    public RestauranteEmpleadoUseCase(IRestauranteEmpleadoPersistencePort restauranteEmpleadoPersistencePort,
                                      IUsuarioClientPort usuarioClientPort,
                                      IRestaurantePersistencePort restaurantePersistencePort) {
        this.restauranteEmpleadoPersistencePort = restauranteEmpleadoPersistencePort;
        this.usuarioClientPort = usuarioClientPort;
        this.restaurantePersistencePort = restaurantePersistencePort;
    }

    @Override
    public void saveRestauranteEmpleado(RestauranteEmpleadoModel restauranteEmpleadoModel) {
        validateRestauranteEmpleado(restauranteEmpleadoModel);
        restauranteEmpleadoPersistencePort.saveRestauranteEmpleado(restauranteEmpleadoModel);
    }

    private void validateRestauranteEmpleado(RestauranteEmpleadoModel restauranteEmpleadoModel) {
        this.validateUsuario(restauranteEmpleadoModel);
        this.validateRestaurante(restauranteEmpleadoModel);
        this.validateRestauranteEmpleadoPreInsert
                (restauranteEmpleadoModel.getIdUsuario(), restauranteEmpleadoModel.getIdRestaurante());
    }

    private void validateUsuario(RestauranteEmpleadoModel restauranteEmpleadoModel){
        final UsuarioResponseDtoModel usuarioById = usuarioClientPort
                .findUsuarioById(restauranteEmpleadoModel.getIdUsuario());
        if(usuarioById == null){
            log.error("Id de usuario invalido");
            throw new ValidationException("Id de usuario invalido");
        }

        if((!usuarioById.getRole().getNombre().equalsIgnoreCase(RoleEnum.EMPLEADO.getDbName()))){
            log.error("Role invalido {}", usuarioById.getRole().getNombre());
            throw new ValidationException("Role no valido para guardar usuario");
        }
    }

    private void validateRestaurante(RestauranteEmpleadoModel restauranteEmpleadoModel){
        RestauranteModel restauranteModel = restaurantePersistencePort
                .findByIdPP(restauranteEmpleadoModel.getIdRestaurante());
        if(restauranteModel == null){
            log.error("No se encontro el restaurante");
            throw new ValidationException("No se encontro el restaurante");
        }
    }

    private void validateRestauranteEmpleadoPreInsert (Integer idEmpleado, Integer idRestaurante){
        RestauranteEmpleadoModel restauranteEmpleadoModel;
        try{
            restauranteEmpleadoModel= restauranteEmpleadoPersistencePort.findEmpleadoByIdAndIdRestaurante(idEmpleado, idRestaurante);
        } catch (Exception e){
            return;
        }
        if(restauranteEmpleadoModel != null){
            throw new ValidationException("El empleado ya existe para restaurante");
        }
    }
}

