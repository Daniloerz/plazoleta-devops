package com.plazoletapowerUp.application.handler.impl;

import com.plazoletapowerUp.application.dto.request.RestauranteEmpleadoRequestDto;
import com.plazoletapowerUp.application.handler.IRestauranteEmpleadoHandler;
import com.plazoletapowerUp.application.mapper.IRestauranteEmpleadoRequestMapper;
import com.plazoletapowerUp.domain.api.IRestauranteEmpleadoServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class RestauranteEmpleadoHandler implements IRestauranteEmpleadoHandler {

    private IRestauranteEmpleadoServicePort restauranteEmpleadoServicePort;
    private IRestauranteEmpleadoRequestMapper restauranteEmpleadoRequestMapper;

    @Override
    public void saveRestauranteEmpleado(RestauranteEmpleadoRequestDto restauranteEmpleadoRequestDto) {
        restauranteEmpleadoServicePort.saveRestauranteEmpleado(
                restauranteEmpleadoRequestMapper.toRestauranteEmpleadoModel(restauranteEmpleadoRequestDto));
    }
}
