package com.plazoletapowerUp.infrastructure.out.jpa.mapper;

import com.plazoletapowerUp.domain.model.PedidoModel;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.PedidoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPedidosEntityMapper {
    PedidoEntity toEntity(PedidoModel pedidoModel);
    PedidoModel toModel(PedidoEntity pedidoEntity);
    List<PedidoModel> toPedidosModel(List<PedidoEntity> pedidoEntityList);
}
