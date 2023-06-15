package com.plazoletapowerUp.infrastructure.out.feing.adapter;

import com.plazoletapowerUp.domain.client.IUsuarioClientPort;
import com.plazoletapowerUp.domain.responseDtoModel.UsuarioResponseDtoModel;
import com.plazoletapowerUp.infrastructure.exception.NoDataFoundException;
import com.plazoletapowerUp.infrastructure.out.feing.mapper.IUsuarioResponseMapper;
import com.plazoletapowerUp.infrastructure.out.feing.restClient.IUsuarioRestClient;

public class UsuarioClientAdapter implements IUsuarioClientPort {

    private IUsuarioRestClient usuarioRestClient;
    private IUsuarioResponseMapper usuarioResponseMapper;

    public UsuarioClientAdapter(IUsuarioRestClient usuarioRestClient, IUsuarioResponseMapper usuarioResponseMapper) {
        this.usuarioRestClient = usuarioRestClient;
        this.usuarioResponseMapper = usuarioResponseMapper;
    }

    @Override
    public UsuarioResponseDtoModel findUsuarioById(Integer idUsuario) {
        try{
            return usuarioResponseMapper.toUsuarioResponseModel(usuarioRestClient.findUsuarioById(idUsuario));
        } catch (Exception e){
            throw new NoDataFoundException("Usuario no encontrado");
        }
    }
}
