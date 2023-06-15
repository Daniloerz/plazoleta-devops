package com.plazoletapowerUp.infrastructure.out.feing.adapter;

import com.plazoletapowerUp.domain.responseDtoModel.UsuarioResponseDtoModel;
import com.plazoletapowerUp.infrastructure.exception.NoDataFoundException;
import com.plazoletapowerUp.infrastructure.out.feing.dto.UsuarioResponseDto;
import com.plazoletapowerUp.infrastructure.out.feing.mapper.IUsuarioResponseMapper;
import com.plazoletapowerUp.infrastructure.out.feing.restClient.IUsuarioRestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioClientAdapterTest {

    private IUsuarioRestClient usuarioRestClient;
    private IUsuarioResponseMapper usuarioResponseMapper;
    private UsuarioClientAdapter usuarioClientAdapter;

    @BeforeEach
    public void setUp() {
        usuarioRestClient = Mockito.mock(IUsuarioRestClient.class);
        usuarioResponseMapper = Mockito.mock(IUsuarioResponseMapper.class);
        usuarioClientAdapter = new UsuarioClientAdapter(usuarioRestClient, usuarioResponseMapper);
    }

    @Test
    public void testFindUsuarioById_Success() {
        Integer idUsuario = 1;
        UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto();
        UsuarioResponseDtoModel usuarioResponseDtoModel = new UsuarioResponseDtoModel();

        Mockito.when(usuarioRestClient.findUsuarioById(idUsuario)).thenReturn(usuarioResponseDto);
        Mockito.when(usuarioResponseMapper.toUsuarioResponseModel(usuarioResponseDto)).thenReturn(usuarioResponseDtoModel);

        UsuarioResponseDtoModel result = usuarioClientAdapter.findUsuarioById(idUsuario);

        Mockito.verify(usuarioRestClient).findUsuarioById(idUsuario);
        Mockito.verify(usuarioResponseMapper).toUsuarioResponseModel(usuarioResponseDto);
        Assertions.assertEquals(usuarioResponseDtoModel, result);
    }

    @Test
    public void testFindUsuarioById_Exception() {
        Integer idUsuario = 1;

        Mockito.when(usuarioRestClient.findUsuarioById(idUsuario)).thenThrow(new RuntimeException("Error"));

        Assertions.assertThrows(NoDataFoundException.class, () -> {
            usuarioClientAdapter.findUsuarioById(idUsuario);
        });

        Mockito.verify(usuarioRestClient).findUsuarioById(idUsuario);
        Mockito.verifyNoInteractions(usuarioResponseMapper);
    }
}