package com.plazoletapowerUp.infrastructure.out.jpa.mapper;

import com.plazoletapowerUp.domain.model.PlatoRestauranteModel;
import com.plazoletapowerUp.domain.model.PlatosModel;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.PlatoEntity;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.custom.IPlatoRestaurante;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPlatosEntityMapper {
    PlatoEntity toEntity(PlatosModel platosModel);
    PlatosModel toPlatosModel(PlatoEntity platoEntity);
    List<PlatosModel> toPlatosModelList(List<PlatoEntity> platoEntityList);
    List<PlatoRestauranteModel> toPlatoRestauranteModelList(List<IPlatoRestaurante> iPlatoRestauranteList);
}
