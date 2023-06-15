package com.plazoletapowerUp.infrastructure.input.rest;

import com.plazoletapowerUp.application.dto.request.PlatosRequestDescriptionDto;
import com.plazoletapowerUp.application.dto.request.PlatosRequestDto;
import com.plazoletapowerUp.application.dto.request.PlatosRequestPriceDto;
import com.plazoletapowerUp.application.dto.response.PlatosPageResponseDto;
import com.plazoletapowerUp.application.handler.IPlatosHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/platos")
@RequiredArgsConstructor
@Validated
public class PlatosRestController {

    private final IPlatosHandler platosHandler;

    @Operation(summary = "Add a new dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish created", content = @Content)
    })
    @PostMapping("/{idPropietario}")
    public ResponseEntity<Void> createPlato(@RequestBody PlatosRequestDto platosRequestDto,
                                            @Parameter(description = "Id propietario restaurante")
                                            @PathVariable Integer idPropietario) {
        platosHandler.savePlatos(platosRequestDto, idPropietario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Update dish price")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", description = "Dish price updated correctly",
                    content = @Content)
    })
    @PatchMapping("/actualizar-precio/{id}")
    public ResponseEntity<Void> updateDishPrice(@PathVariable (name = "id") Integer idPlato,
                                                @RequestBody PlatosRequestPriceDto platosRequestPriceDto){

        platosRequestPriceDto.setId(idPlato);
        platosHandler.updatePlatoByPrice(platosRequestPriceDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Update dish description")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", description = "Dish description updated correctly",
                    content = @Content)
    })
    @PatchMapping("/actualizar-descripcion/{id}")
    public ResponseEntity<Void> updateDishDescription(@PathVariable (name = "id") Integer idPlato,
                                                      @RequestBody PlatosRequestDescriptionDto platosRequestDescriptionDto){

        platosRequestDescriptionDto.setId(idPlato);
        platosHandler.updatePlatoByDescription(platosRequestDescriptionDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Update dish active")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", description = "Dish active updated correctly",
                    content = @Content)
    })
    @PatchMapping("/active/{id}/{idPlato}")
    public ResponseEntity<Void> updateDishActive(@Parameter(description = "Id propietario restaurante")
                                                 @PathVariable(name = "id") Integer idPropietario,
                                                 @PathVariable Integer idPlato,
                                                 @Parameter(description = "Habilitar/inhabilitar plato")
                                                 @RequestParam Boolean isActive){
        platosHandler.updatePlatoActive(idPropietario, idPlato, isActive);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get all dishes from a restaurant with pagination")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", description = "Dishes from a specific restaurant found",
                    content = @Content(schema = @Schema(implementation = PlatosPageResponseDto.class)))
    })
    @GetMapping("/{id_restaurante}")
    public ResponseEntity<PlatosPageResponseDto> findAllByRestaurante(@PathVariable Integer id_restaurante,
                                                                      @RequestParam (defaultValue = "1", required = false)
                                                                      @Min(1) Integer initPage,
                                                                      @RequestParam (defaultValue = "10", required = false)
                                                                      @Min(1) Integer numElementsPage){
        PlatosPageResponseDto platosByIdRestaurante = platosHandler.findPlatosByIdRestaurante(id_restaurante, initPage, numElementsPage);
        if (platosByIdRestaurante != null){
            return ResponseEntity.ok(platosByIdRestaurante);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
