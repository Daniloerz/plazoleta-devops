package com.plazoletapowerUp.application.mapper;


import com.plazoletapowerUp.application.dto.request.PlatosRequestDescriptionDto;
import com.plazoletapowerUp.application.dto.request.PlatosRequestDto;
import com.plazoletapowerUp.application.dto.request.PlatosRequestPriceDto;
import com.plazoletapowerUp.application.dto.response.PlatosResponseDto;
import com.plazoletapowerUp.domain.model.PlatosModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPlatosRequestMapper {
    PlatosModel toPlatosModel(PlatosRequestDto platosRequestDto);
    PlatosModel toPlatosModel(PlatosRequestPriceDto platosRequestPriceDto);
    PlatosModel toPlatosModel(PlatosRequestDescriptionDto platosRequestDescriptionDto);
    List<PlatosResponseDto> toPlatosResponseDtoList(List<PlatosModel> platosModelList);
}
