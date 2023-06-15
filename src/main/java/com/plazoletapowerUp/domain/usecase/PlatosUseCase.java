package com.plazoletapowerUp.domain.usecase;

import com.plazoletapowerUp.domain.api.IPlatosServicePort;
import com.plazoletapowerUp.domain.client.IUsuarioClientPort;
import com.plazoletapowerUp.domain.exception.ValidationException;
import com.plazoletapowerUp.domain.model.CategoriaModel;
import com.plazoletapowerUp.domain.model.PlatosModel;
import com.plazoletapowerUp.domain.model.PlatosRestaurantePageableModel;
import com.plazoletapowerUp.domain.model.RestauranteModel;
import com.plazoletapowerUp.domain.responseDtoModel.UsuarioResponseDtoModel;
import com.plazoletapowerUp.domain.spi.ICategoriaPersistencePort;
import com.plazoletapowerUp.domain.spi.IPlatosPersistencePort;
import com.plazoletapowerUp.domain.spi.IRestaurantePersistencePort;
import com.plazoletapowerUp.infrastructure.enums.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class PlatosUseCase implements IPlatosServicePort {

    public final Logger log = LoggerFactory.getLogger(PlatosUseCase.class);
    private final IPlatosPersistencePort platosPersistencePort;
    private final ICategoriaPersistencePort categoriaPersistencePort;
    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IUsuarioClientPort usuarioClientPort;

    public PlatosUseCase(IPlatosPersistencePort platosPersistencePort,
                         ICategoriaPersistencePort categoriaPersistencePort,
                         IRestaurantePersistencePort restaurantePersistencePort,
                         IUsuarioClientPort usuarioClientPort) {
        this.platosPersistencePort = platosPersistencePort;
        this.categoriaPersistencePort = categoriaPersistencePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.usuarioClientPort = usuarioClientPort;
    }


    @Override
    public void savePlatosSP(PlatosModel platosModel, Integer idPropietario) {
        this.validateRole(idPropietario);
        platosModel.setActivo(true);

        CategoriaModel categoriaModel = categoriaPersistencePort.findById(platosModel.getIdCategoria());
        RestauranteModel restauranteModel = restaurantePersistencePort.findByIdPP(platosModel.getIdRestaurante());
        this.validatePrecio(platosModel.getPrecio());

        platosModel.setIdCategoria(categoriaModel.getId());
        platosModel.setIdRestaurante(restauranteModel.getId());
        platosPersistencePort.savePlatoPP(platosModel);
    }

    @Override
    public PlatosModel updatePlatoByPrice(PlatosModel platosModel) {
        PlatosModel platosModelPrice = platosPersistencePort.findPlatoById(platosModel);
        platosModelPrice.setPrecio(platosModel.getPrecio());
        return platosPersistencePort.savePlatoPP(platosModelPrice);
    }

    @Override
    public PlatosModel updatePlatoByDescription(PlatosModel platosModel) {
        PlatosModel platosModelDescription = platosPersistencePort.findPlatoById(platosModel);
        platosModelDescription.setDescripcion(platosModel.getDescripcion());
        return platosPersistencePort.savePlatoPP(platosModelDescription);
    }


    @Override
    public PlatosModel updatePlatoActiveSP(Integer idPropietario,Integer idPlato, Boolean isActive) {
        this.validateRole(idPropietario);
        PlatosModel platosModel1 = platosPersistencePort.findPlatoById(idPlato);
        this.validateRestaurantePropietario(idPropietario, platosModel1);
        platosModel1.setActivo(isActive);
        return platosPersistencePort.savePlatoPP(platosModel1);
    }

    @Override
    public PlatosRestaurantePageableModel findPlatosByRestaurante(Integer id, Integer initPage, Integer numElementsPage) {
        return platosPersistencePort.findPlatosByIdAndPageable(id, initPage, numElementsPage);
    }

    private void validatePrecio(Integer precio) {
        if (precio <= 0) {
            log.error("El precio no debe ser menor o igual a $0");
            throw new ValidationException("El precio no debe ser menor o igual a $0");
        }
    }

    private void validateRole(Integer idPropietario) {
        final UsuarioResponseDtoModel usuarioById = usuarioClientPort.findUsuarioById(idPropietario);
        if (!usuarioById.getRole().getNombre().equalsIgnoreCase(RoleEnum.PROPIETARIO.getDbName())) {
            log.error("Role invalido {}", usuarioById.getRole().getNombre());
            throw new ValidationException("Role no valido para la acción requerida");
        }
    }

    private void validateRestaurantePropietario(Integer idPropietario, PlatosModel platosModel) {
        RestauranteModel restauranteModel = restaurantePersistencePort.findByIdPP(platosModel.getIdRestaurante());
        if (!Objects.equals(restauranteModel.getIdPropietario(), idPropietario)){
            log.error("Acceso no autorizado");
            throw new ValidationException("Acceso no autorizado");
        }
    }
}

