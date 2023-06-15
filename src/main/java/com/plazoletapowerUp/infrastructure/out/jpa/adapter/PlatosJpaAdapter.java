package com.plazoletapowerUp.infrastructure.out.jpa.adapter;

import com.plazoletapowerUp.domain.model.PlatoRestauranteModel;
import com.plazoletapowerUp.domain.model.PlatosModel;
import com.plazoletapowerUp.domain.model.PlatosRestaurantePageableModel;
import com.plazoletapowerUp.domain.spi.IPlatosPersistencePort;
import com.plazoletapowerUp.infrastructure.exception.NoDataFoundException;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.PlatoEntity;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.custom.IPlatoRestaurante;
import com.plazoletapowerUp.infrastructure.out.jpa.mapper.IPlatosEntityMapper;
import com.plazoletapowerUp.infrastructure.out.jpa.repository.IPlatosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PlatosJpaAdapter implements IPlatosPersistencePort {

    private final IPlatosRepository platosRepository;
    private final IPlatosEntityMapper platosEntityMapper;

    @Override
    public PlatosModel savePlatoPP(PlatosModel platosModel) {
        PlatoEntity platoEntity = platosRepository.save(platosEntityMapper.toEntity(platosModel));
        return platosEntityMapper.toPlatosModel(platoEntity);
    }

    @Override
    public PlatosModel findPlatoById(PlatosModel platosModel) {
        Optional<PlatoEntity> platosEntityOptional = platosRepository.findById(platosModel.getId());
        if (platosEntityOptional.isPresent()){
            PlatoEntity platoEntity = platosEntityOptional.get();
            return platosEntityMapper.toPlatosModel(platoEntity);
        } else {
            throw new NoDataFoundException("Plato no encontrado");
        }
    }

    @Override
    public PlatosModel findPlatoById(Integer id) {
        Optional<PlatoEntity> platosEntityOptional = platosRepository.findById(id);
        if (platosEntityOptional.isPresent()){
            PlatoEntity platoEntity = platosEntityOptional.get();
            return platosEntityMapper.toPlatosModel(platoEntity);
        } else {
            throw new NoDataFoundException("Plato no encontrado");
        }
    }

    @Override
    public PlatosRestaurantePageableModel findPlatosByIdAndPageable(Integer id, Integer initPage, Integer numElementPage) {
        Integer initPage1 = (initPage-1);
        List<PlatoRestauranteModel> platosModelList;
        Pageable pageable = PageRequest.of(initPage1, numElementPage);
        Page<IPlatoRestaurante> iPlatoRestaurante = platosRepository.getPlatosByRestaurante(id, pageable);
        List<IPlatoRestaurante> platosEntityList = iPlatoRestaurante.getContent();
        if (!platosEntityList.isEmpty()){
            platosModelList = platosEntityMapper.toPlatoRestauranteModelList(platosEntityList);
        } else {
            throw new NoDataFoundException("No se encontraron platos para este restaurante");
        }
        return new PlatosRestaurantePageableModel(iPlatoRestaurante.getTotalPages(), platosModelList);
    }
}

