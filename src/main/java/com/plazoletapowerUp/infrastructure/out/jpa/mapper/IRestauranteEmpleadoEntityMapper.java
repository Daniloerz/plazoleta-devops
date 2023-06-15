package com.plazoletapowerUp.infrastructure.out.jpa.mapper;

import com.plazoletapowerUp.domain.model.RestauranteEmpleadoModel;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.RestauranteEmpleadoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestauranteEmpleadoEntityMapper {
    RestauranteEmpleadoEntity toRestauranteEmpleadoEntity (RestauranteEmpleadoModel restauranteEmpleadoModel);
    RestauranteEmpleadoModel toRestauranteEmpleadoModel (RestauranteEmpleadoEntity restauranteEmpleadoEntity);
}
