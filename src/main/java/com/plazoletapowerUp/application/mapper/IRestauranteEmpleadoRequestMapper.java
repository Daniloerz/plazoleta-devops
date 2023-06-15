package com.plazoletapowerUp.application.mapper;

import com.plazoletapowerUp.application.dto.request.RestauranteEmpleadoRequestDto;
import com.plazoletapowerUp.domain.model.RestauranteEmpleadoModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestauranteEmpleadoRequestMapper {
    RestauranteEmpleadoModel toRestauranteEmpleadoModel (RestauranteEmpleadoRequestDto restauranteEmpleadoRequestDto);
}
