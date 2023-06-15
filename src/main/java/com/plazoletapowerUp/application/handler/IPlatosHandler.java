package com.plazoletapowerUp.application.handler;

import com.plazoletapowerUp.application.dto.request.PlatosRequestDescriptionDto;
import com.plazoletapowerUp.application.dto.request.PlatosRequestDto;
import com.plazoletapowerUp.application.dto.request.PlatosRequestPriceDto;
import com.plazoletapowerUp.application.dto.response.PlatosPageResponseDto;
import com.plazoletapowerUp.domain.model.PlatosModel;

public interface IPlatosHandler {
    void savePlatos(PlatosRequestDto platosRequestDto, Integer idPropietario);
    PlatosModel updatePlatoByPrice(PlatosRequestPriceDto platosRequestPriceDto);
    PlatosModel updatePlatoByDescription(PlatosRequestDescriptionDto platosRequestDescriptionDto);
    PlatosModel updatePlatoActive(Integer idPropietario,Integer idPlato, Boolean active);
    PlatosPageResponseDto findPlatosByIdRestaurante(Integer id, Integer initPage, Integer numElementsPage);
}
