package com.plazoletapowerUp.infrastructure.input.rest;

import com.plazoletapowerUp.application.dto.request.RestauranteRequestDto;
import com.plazoletapowerUp.application.dto.response.RestaurantePageResponseDto;
import com.plazoletapowerUp.application.handler.IRestauranteHandler;
import com.plazoletapowerUp.domain.model.RestauranteModel;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/restaurante")
@RequiredArgsConstructor
@Validated
public class RestauranteRestController {

    private final IRestauranteHandler restauranteHandler;

    @Operation(summary = "Add a new restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant created",
                    content = @Content(schema = @Schema(implementation = RestauranteModel.class)))
    })

    @PostMapping("/")
    public ResponseEntity<Void> createRestaurante(@RequestBody RestauranteRequestDto restauranteRequestDto) {
        restauranteHandler.saveRestaurante(restauranteRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurants found",
                    content = @Content(schema = @Schema(implementation = RestaurantePageResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content)
    })

    @GetMapping
    public ResponseEntity<RestaurantePageResponseDto> findAllRestaurantes(@RequestParam @Min(1) Integer page){
        RestaurantePageResponseDto restaurantePageResponseDto = restauranteHandler.findAllRestaurantes(page);
       if (restaurantePageResponseDto != null){
           return ResponseEntity.ok(restaurantePageResponseDto);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }
}