package com.plazoletapowerUp.domain.client;

import com.plazoletapowerUp.domain.responseDtoModel.UsuarioResponseDtoModel;


public interface IUsuarioClientPort {

    UsuarioResponseDtoModel findUsuarioById (Integer idUsuario);

}
