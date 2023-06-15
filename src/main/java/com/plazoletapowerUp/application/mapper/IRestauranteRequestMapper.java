package com.plazoletapowerUp.application.mapper;

import com.plazoletapowerUp.application.dto.request.RestauranteRequestDto;
import com.plazoletapowerUp.application.dto.response.RestauranteResponseDto;
import com.plazoletapowerUp.domain.model.RestauranteModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestauranteRequestMapper {
    RestauranteModel toRestauranteModel(RestauranteRequestDto restauranteRequestDto);
    List<RestauranteResponseDto> toRestauranteResponseDtoList(List<RestauranteModel> restauranteModelsList);

}
