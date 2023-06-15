package com.plazoletapowerUp.infrastructure.out.jpa.mapper;

import com.plazoletapowerUp.domain.model.RestauranteModel;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.RestauranteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRestauranteEntityMapper {

    RestauranteEntity toEntity(RestauranteModel restauranteModel);
    RestauranteModel toRestauranteModel(RestauranteEntity restauranteEntity);
    List<RestauranteModel> toRestauranteModelList(List<RestauranteEntity> restauranteEntityList);

}