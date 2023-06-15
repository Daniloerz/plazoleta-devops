package com.plazoletapowerUp.infrastructure.out.feing.mapper;

import com.plazoletapowerUp.domain.responseDtoModel.UsuarioResponseDtoModel;
import com.plazoletapowerUp.infrastructure.out.feing.dto.UsuarioResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUsuarioResponseMapper {
    UsuarioResponseDtoModel toUsuarioResponseModel(UsuarioResponseDto usuarioResponseDto);
}
