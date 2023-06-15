package com.plazoletapowerUp.infrastructure.out.feing.restClient;

import com.plazoletapowerUp.infrastructure.out.feing.dto.UsuarioResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-powerup", url = "http://f68ws5vdf4.execute-api.us-east-1.amazonaws.com/plazoleta/usuario")
public interface IUsuarioRestClient {

    @GetMapping("/api/v1/usuario/{idUsuario}")
    public UsuarioResponseDto findUsuarioById (@PathVariable Integer idUsuario);

}
