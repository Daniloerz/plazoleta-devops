package com.plazoletapowerUp.infrastructure.input.rest;

import com.plazoletapowerUp.application.dto.request.RestauranteEmpleadoRequestDto;
import com.plazoletapowerUp.application.handler.IRestauranteEmpleadoHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurante-empleado")
@RequiredArgsConstructor
public class RestauranteEmpleadoController {

    private final IRestauranteEmpleadoHandler restauranteEmpleado;

    @Operation(summary = "Add a new restaurant employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant employee created", content = @Content)
    })

    @PostMapping("/")
    public ResponseEntity<Void> restaurantEmployeeLink (
            @RequestBody RestauranteEmpleadoRequestDto restauranteEmpleadoRequestDto) {
        restauranteEmpleado.saveRestauranteEmpleado(restauranteEmpleadoRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

