package com.plazoletapowerUp.domain.usecase;

import com.plazoletapowerUp.domain.api.IRestauranteServicePort;
import com.plazoletapowerUp.domain.client.IUsuarioClientPort;
import com.plazoletapowerUp.domain.exception.ValidationException;
import com.plazoletapowerUp.domain.model.RestauranteModel;
import com.plazoletapowerUp.domain.model.RestaurantePageableModel;
import com.plazoletapowerUp.domain.responseDtoModel.UsuarioResponseDtoModel;
import com.plazoletapowerUp.domain.spi.IRestaurantePersistencePort;
import com.plazoletapowerUp.infrastructure.enums.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class RestauranteUseCase implements IRestauranteServicePort {

    public final Logger log = LoggerFactory.getLogger(RestauranteUseCase.class);


    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IUsuarioClientPort usuarioClientPort;

    public RestauranteUseCase(IRestaurantePersistencePort restaurantePersistencePort, IUsuarioClientPort usuarioClientPort) {
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.usuarioClientPort = usuarioClientPort;
    }

    @Override
    public void saveRestauranteSP(RestauranteModel restauranteModel) {
        this.validateRestaurante(restauranteModel);
        restaurantePersistencePort.saveRestaurante(restauranteModel);
    }

    @Override
    public RestaurantePageableModel findAllRestaurantesSP(Integer page) {
        return  restaurantePersistencePort.findAllRestaurantesPP(page);

    }

    private void validateRestaurante(RestauranteModel restauranteModel) {
        this.validateNombreRestaurante(restauranteModel.getNombre());
        this.validateTelefonoRestaurante(restauranteModel.getTelefono());
        this.validateNit(restauranteModel.getNit());
        this.validatePropietarioRole(restauranteModel.getIdPropietario());
    }

    private void validateNombreRestaurante(String nombreRestaurante){
        String regex = "^.*\\D+.*$";

        Pattern pattern = Pattern.compile(regex);
        if(!pattern.matcher(nombreRestaurante).matches()){
            log.error("Nombre inválido invalido");

            throw new ValidationException("Nombre inválido invalido");
        }
    }


    private void validateTelefonoRestaurante(String telefonoRestaurante){
        String regex = "[+]?[\\d]{1,13}";

        Pattern pattern = Pattern.compile(regex);
        if(!pattern.matcher(telefonoRestaurante).matches()){
            log.error("Telefono invalido");
            throw new ValidationException("Telefono invalido");
        }
    }

    private void validateNit(String nit){
        String regex = "[\\d]*";

        Pattern pattern = Pattern.compile(regex);

        if(!pattern.matcher(nit).matches()){
            log.error("Nit invalido");
            throw new ValidationException("Nit invalido");
        }
    }

    private void validatePropietarioRole(Integer idUsuario){
        final UsuarioResponseDtoModel usuarioById = usuarioClientPort.findUsuarioById(idUsuario);
        if(!usuarioById.getRole().getNombre().equalsIgnoreCase(RoleEnum.PROPIETARIO.getDbName())){
            log.error("Role invalido {}", usuarioById.getRole().getNombre());
            throw new ValidationException("Role no valido para crear restaurante");
        }
    }
}