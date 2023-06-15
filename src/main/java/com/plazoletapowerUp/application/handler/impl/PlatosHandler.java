package com.plazoletapowerUp.application.handler.impl;

import com.plazoletapowerUp.application.dto.request.PlatosRequestDescriptionDto;
import com.plazoletapowerUp.application.dto.request.PlatosRequestDto;
import com.plazoletapowerUp.application.dto.request.PlatosRequestPriceDto;
import com.plazoletapowerUp.application.dto.response.PlatosPageResponseDto;
import com.plazoletapowerUp.application.dto.response.PlatosResponseDto;
import com.plazoletapowerUp.application.handler.IPlatosHandler;
import com.plazoletapowerUp.application.mapper.IPlatosRequestMapper;
import com.plazoletapowerUp.domain.api.IPlatosServicePort;
import com.plazoletapowerUp.domain.model.PlatosModel;
import com.plazoletapowerUp.domain.model.PlatosRestaurantePageableModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PlatosHandler implements IPlatosHandler {

    private final IPlatosServicePort platosServicePort;
    private final IPlatosRequestMapper platosRequestMapper;

    @Override
    public void savePlatos(PlatosRequestDto platosRequestDto, Integer idPropietario) {
        platosServicePort.savePlatosSP(platosRequestMapper.toPlatosModel(platosRequestDto), idPropietario);
    }

    @Override
    public PlatosModel updatePlatoByPrice(PlatosRequestPriceDto platosRequestPriceDto) {
        return platosServicePort
                .updatePlatoByPrice(platosRequestMapper.toPlatosModel(platosRequestPriceDto));
    }

    @Override
    public PlatosModel updatePlatoByDescription(PlatosRequestDescriptionDto platosRequestDescriptionDto) {
        return platosServicePort
                .updatePlatoByDescription(platosRequestMapper.toPlatosModel(platosRequestDescriptionDto));
    }

    @Override
    public PlatosModel updatePlatoActive(Integer idPropietario,Integer idPlato, Boolean isActive) {
        return platosServicePort.updatePlatoActiveSP(idPropietario, idPlato, isActive);
    }

    @Override
    public PlatosPageResponseDto findPlatosByIdRestaurante(Integer id, Integer initPage, Integer numElementsPage) {
        PlatosRestaurantePageableModel platosRestaurantePageableModel = platosServicePort
                .findPlatosByRestaurante(id, initPage, numElementsPage);
        PlatosPageResponseDto platosPageResponseDto = new PlatosPageResponseDto();
        platosPageResponseDto.setPage(initPage);
        platosPageResponseDto.setPageSize(numElementsPage);
        platosPageResponseDto.setTotalPages(platosRestaurantePageableModel.getPagesAmount());
        platosPageResponseDto.setNombreRestaurante(platosRestaurantePageableModel
                .getPlatoRestauranteModelList().get(0).getNombreRestaurante());

        final Map<String, List<PlatosResponseDto>> stringPlatosResponseDtoMap = platosRestaurantePageableModel
                .getPlatoRestauranteModelList()
                .stream()
                .map(platoRestauranteModel -> new PlatosResponseDto(platoRestauranteModel.getId(), platoRestauranteModel.getNombrePlato(),
                        platoRestauranteModel.getPrecio(),
                        platoRestauranteModel.getDescripcionPlato(),
                        platoRestauranteModel.getUrlImagen(),
                        platoRestauranteModel.getNombreCategoria()))
                .collect(Collectors.groupingBy(PlatosResponseDto::getNombreCategoria));

        platosPageResponseDto.setPlatosCategoriaMap(stringPlatosResponseDtoMap);
        return platosPageResponseDto;
    }

}

