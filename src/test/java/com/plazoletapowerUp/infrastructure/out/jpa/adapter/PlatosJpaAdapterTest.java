package com.plazoletapowerUp.infrastructure.out.jpa.adapter;

import com.plazoletapowerUp.domain.model.PlatoRestauranteModel;
import com.plazoletapowerUp.domain.model.PlatosModel;
import com.plazoletapowerUp.domain.model.PlatosRestaurantePageableModel;
import com.plazoletapowerUp.infrastructure.exception.NoDataFoundException;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.PlatoEntity;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.custom.IPlatoRestaurante;
import com.plazoletapowerUp.infrastructure.out.jpa.mapper.IPlatosEntityMapper;
import com.plazoletapowerUp.infrastructure.out.jpa.repository.IPlatosRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PlatosJpaAdapterTest {
    private PlatosJpaAdapter platosJpaAdapter;

    @Mock
    private IPlatosRepository platosRepository;

    @Mock
    private IPlatosEntityMapper platosEntityMapper;

    @Captor
    private ArgumentCaptor<PlatoEntity> platoEntityCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        platosJpaAdapter = new PlatosJpaAdapter(platosRepository, platosEntityMapper);
    }


    @Test
    public void testFindPlatoById_Success() {
        PlatosModel platosModel = new PlatosModel();
        platosModel.setId(1);
        PlatoEntity platoEntity = new PlatoEntity();

        Mockito.when(platosRepository.findById(platosModel.getId())).thenReturn(Optional.of(platoEntity));
        Mockito.when(platosEntityMapper.toPlatosModel(platoEntity)).thenReturn(platosModel);

        PlatosModel result = platosJpaAdapter.findPlatoById(platosModel);

        Mockito.verify(platosRepository).findById(platosModel.getId());
        Mockito.verify(platosEntityMapper).toPlatosModel(platoEntity);
        Assertions.assertEquals(platosModel, result);
    }

    @Test
    public void testFindPlatoById_NoDataFound() {
        PlatosModel platosModel = new PlatosModel();
        platosModel.setId(1);

        Mockito.when(platosRepository.findById(platosModel.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoDataFoundException.class, () -> platosJpaAdapter.findPlatoById(platosModel));
    }

}