package com.plazoletapowerUp.infrastructure.out.jpa.adapter;

import com.plazoletapowerUp.domain.model.PedidoModel;
import com.plazoletapowerUp.domain.model.PedidoPlatosModel;
import com.plazoletapowerUp.infrastructure.enums.PedidoEstadoEnum;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.PedidoEntity;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.PedidoPlatoEntity;
import com.plazoletapowerUp.infrastructure.out.jpa.mapper.IPedidosEntityMapper;
import com.plazoletapowerUp.infrastructure.out.jpa.repository.IPedidosPlatosRepository;
import com.plazoletapowerUp.infrastructure.out.jpa.repository.IPedidosRepository;
import com.plazoletapowerUp.infrastructure.out.jpa.repository.IPlatosRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidosJpaAdapterTest {

    private PedidosJpaAdapter pedidosJpaAdapter;

    @Mock
    private IPedidosRepository pedidosRepository;

    @Mock
    private IPedidosPlatosRepository pedidosPlatosRepository;

    @Mock
    private IPlatosRepository platosRepository;

    @Mock
    private IPedidosEntityMapper pedidosEntityMapper;

    @Captor
    private ArgumentCaptor<PedidoEntity> pedidoEntityCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        pedidosJpaAdapter = new PedidosJpaAdapter(pedidosRepository, pedidosPlatosRepository,
                platosRepository, pedidosEntityMapper);
    }

    @Test
    public void testSavePedido() {
        PedidoModel pedidoModel = new PedidoModel();

        pedidosJpaAdapter.savePedido(pedidoModel);

        Mockito.verify(pedidosRepository).save(pedidosEntityMapper.toEntity(pedidoModel));
    }
}