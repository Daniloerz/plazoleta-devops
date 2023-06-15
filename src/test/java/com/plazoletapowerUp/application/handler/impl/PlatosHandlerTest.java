package com.plazoletapowerUp.application.handler.impl;

import com.plazoletapowerUp.application.dto.request.PlatosRequestDescriptionDto;
import com.plazoletapowerUp.application.dto.request.PlatosRequestDto;
import com.plazoletapowerUp.application.dto.request.PlatosRequestPriceDto;
import com.plazoletapowerUp.application.dto.response.PlatosPageResponseDto;
import com.plazoletapowerUp.application.dto.response.PlatosResponseDto;
import com.plazoletapowerUp.application.mapper.IPlatosRequestMapper;
import com.plazoletapowerUp.domain.api.IPlatosServicePort;
import com.plazoletapowerUp.domain.model.PlatoRestauranteModel;
import com.plazoletapowerUp.domain.model.PlatosModel;
import com.plazoletapowerUp.domain.model.PlatosRestaurantePageableModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PlatosHandlerTest {

    private IPlatosServicePort platosServicePort = Mockito.mock(IPlatosServicePort.class);

    private IPlatosRequestMapper platosRequestMapper = Mockito.mock(IPlatosRequestMapper.class);

    private PlatosHandler platosHandler = new PlatosHandler(platosServicePort, platosRequestMapper);


    @Test
    public void testSavePlatos() {
        PlatosRequestDto platosRequestDto = new PlatosRequestDto();
        PlatosModel platosModel = new PlatosModel();
        Integer idPropietario = 123;
        when(platosRequestMapper.toPlatosModel(platosRequestDto)).thenReturn(platosModel);
        platosHandler.savePlatos(platosRequestDto, idPropietario);

        verify(platosServicePort).savePlatosSP(platosModel, idPropietario);
    }

    @Test
    public void testUpdatePlatoByPrice() {
        PlatosRequestPriceDto platosRequestPriceDto = new PlatosRequestPriceDto();
        PlatosModel expectedPlatosModel = new PlatosModel();
        when(platosRequestMapper.toPlatosModel(platosRequestPriceDto)).thenReturn(expectedPlatosModel);
        when(platosServicePort.updatePlatoByPrice(any(PlatosModel.class))).thenReturn(expectedPlatosModel);

        PlatosModel result = platosHandler.updatePlatoByPrice(platosRequestPriceDto);

        verify(platosServicePort).updatePlatoByPrice(any(PlatosModel.class));
        assertEquals(expectedPlatosModel, result);
    }

    @Test
    public void testUpdatePlatoByDescription() {
        PlatosRequestDescriptionDto platosRequestDescriptionDto = new PlatosRequestDescriptionDto();
        PlatosModel expectedPlatosModel = new PlatosModel();
        when(platosRequestMapper.toPlatosModel(platosRequestDescriptionDto)).thenReturn(expectedPlatosModel);
        when(platosServicePort.updatePlatoByDescription(any(PlatosModel.class))).thenReturn(expectedPlatosModel);
        PlatosModel result = platosHandler.updatePlatoByDescription(platosRequestDescriptionDto);
        verify(platosServicePort).updatePlatoByDescription(any(PlatosModel.class));
        assertEquals(expectedPlatosModel, result);
    }

    @Test
    public void testUpdatePlatoActive() {
        Integer idPropietario = 123;
        Integer idPlato = 456;
        Boolean isActive = true;
        PlatosModel expectedPlatosModel = new PlatosModel();
        when(platosServicePort.updatePlatoActiveSP(eq(idPropietario), eq(idPlato), eq(isActive))).thenReturn(expectedPlatosModel);

        PlatosModel result = platosHandler.updatePlatoActive(idPropietario, idPlato, isActive);

        verify(platosServicePort).updatePlatoActiveSP(eq(idPropietario), eq(idPlato), eq(isActive));
        assertEquals(expectedPlatosModel, result);
    }

    @Test
    void testFindPlatosByIdRestaurante() {
        Integer id = 1;
        Integer initPage = 0;
        Integer numElementsPage = 10;

        PlatoRestauranteModel platoRestauranteModel = new PlatoRestauranteModel();
        platoRestauranteModel.setId(1);
        platoRestauranteModel.setNombrePlato("Plato 1");
        platoRestauranteModel.setPrecio(10);
        platoRestauranteModel.setDescripcionPlato("Descripción del plato 1");
        platoRestauranteModel.setUrlImagen("imagen.jpg");
        platoRestauranteModel.setNombreCategoria("Categoría 1");

        List<PlatoRestauranteModel> platosRestauranteModelList = Collections.singletonList(platoRestauranteModel);

        PlatosRestaurantePageableModel platosRestaurantePageableModel = new PlatosRestaurantePageableModel();
        platosRestaurantePageableModel.setPagesAmount(1);
        platosRestaurantePageableModel.setPlatoRestauranteModelList(platosRestauranteModelList);

        when(platosServicePort.findPlatosByRestaurante(id, initPage, numElementsPage))
                .thenReturn(platosRestaurantePageableModel);

        PlatosPageResponseDto result = platosHandler.findPlatosByIdRestaurante(id, initPage, numElementsPage);

        assertEquals(initPage, result.getPage());
        assertEquals(numElementsPage, result.getPageSize());
        assertEquals(1, result.getTotalPages());

        Map<String, List<PlatosResponseDto>> platosCategoriaMap = result.getPlatosCategoriaMap();
        assertEquals(1, platosCategoriaMap.size());

        List<PlatosResponseDto> platosResponseDtoList = platosCategoriaMap.get("Categoría 1");
        assertEquals(1, platosResponseDtoList.size());

        PlatosResponseDto platosResponseDto = platosResponseDtoList.get(0);
        assertEquals(1, platosResponseDto.getId());
        assertEquals("Plato 1", platosResponseDto.getNombrePlato());
        assertEquals(10, platosResponseDto.getPrecio());
        assertEquals("Descripción del plato 1", platosResponseDto.getDescripcionPlato());
        assertEquals("imagen.jpg", platosResponseDto.getUrlImagen());
    }
}