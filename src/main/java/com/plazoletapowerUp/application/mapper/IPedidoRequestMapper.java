package com.plazoletapowerUp.application.mapper;

import com.plazoletapowerUp.application.dto.request.PedidoPlatoRequestDto;
import com.plazoletapowerUp.application.dto.request.PedidoRequestDto;
import com.plazoletapowerUp.application.dto.response.PedidoResponseDto;
import com.plazoletapowerUp.domain.model.PedidoModel;
import com.plazoletapowerUp.domain.model.PedidoPlatosModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPedidoRequestMapper {
    PedidoModel toPedidoModel(PedidoRequestDto pedidoRequestDto);
    List<PedidoPlatosModel> toPedidoPlatosModel(List<PedidoPlatoRequestDto> pedidoPlatoRequestDto);
    List<PedidoResponseDto> toPedidoResponseDto(List<PedidoModel> pedidoModelList);

}
