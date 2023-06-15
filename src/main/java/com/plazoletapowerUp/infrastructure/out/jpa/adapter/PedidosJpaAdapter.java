package com.plazoletapowerUp.infrastructure.out.jpa.adapter;

import com.plazoletapowerUp.domain.model.PedidoModel;
import com.plazoletapowerUp.domain.model.PedidoPlatosModel;
import com.plazoletapowerUp.domain.model.PedidosPageableModel;
import com.plazoletapowerUp.domain.spi.IPedidosPersistencePort;
import com.plazoletapowerUp.infrastructure.enums.PedidoEstadoEnum;
import com.plazoletapowerUp.infrastructure.exception.NoDataFoundException;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.PedidoEntity;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.PedidoPlatoEntity;
import com.plazoletapowerUp.infrastructure.out.jpa.mapper.IPedidosEntityMapper;
import com.plazoletapowerUp.infrastructure.out.jpa.repository.IPedidosPlatosRepository;
import com.plazoletapowerUp.infrastructure.out.jpa.repository.IPedidosRepository;
import com.plazoletapowerUp.infrastructure.out.jpa.repository.IPlatosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PedidosJpaAdapter implements IPedidosPersistencePort {

    private final IPedidosRepository pedidosRepository;
    private final IPedidosPlatosRepository pedidosPlatosRepository;
    private final IPlatosRepository platosRepository;
    private final IPedidosEntityMapper pedidosEntityMapper;

    @Override
    public void savePedidosPP(PedidoModel pedidoModel, List<PedidoPlatosModel> pedidoPlatosModelList) {
        PedidoEntity pedidoEntity = new PedidoEntity(null, pedidoModel.getIdCliente(),
                new Date(System.currentTimeMillis()), PedidoEstadoEnum.PENDIENTE.getDbValue(),
                null, pedidoModel.getIdRestaurante(), null);
        PedidoEntity pedidoEntitySave = pedidosRepository.save(pedidoEntity);
        final Map<Integer, Integer> platoCantidad = pedidoPlatosModelList.stream().collect(Collectors
                .toMap(PedidoPlatosModel::getIdPlato, PedidoPlatosModel::getCantidad));


        platosRepository.findAllById(platoCantidad.keySet())
                .forEach(plato -> pedidosPlatosRepository
                        .save(new PedidoPlatoEntity(null,
                                pedidoEntitySave.getIdPedido(), plato.getId(), platoCantidad.get(plato.getId()))));
    }

    @Override
    public void savePedido(PedidoModel pedidoModel) {
        pedidosRepository.save(pedidosEntityMapper.toEntity(pedidoModel));
    }

    @Override
    public List<PedidoModel> findPedidosByIdClientePP(Integer idCliente) {
        List<PedidoEntity> pedidoEntity = pedidosRepository.findByIdCliente(idCliente);
        if (pedidoEntity != null){
            return pedidosEntityMapper.toPedidosModel(pedidoEntity);
        } else {
            throw new NoDataFoundException("Pedido no encontrado para el cliente");

        }
    }

    @Override
    public PedidosPageableModel findPedidoByEstado(Integer idEmpleado,
                                                   Integer idRestaurante,
                                                   String estado,
                                                   Integer page,
                                                   Integer numElemPage) {
        Integer initPage = (page-1);
        List<PedidoModel> pedidoModelList;
        Pageable pageable = PageRequest.of(initPage, numElemPage);
        Page<PedidoEntity> pedidoEntities = pedidosRepository
                .getByIdRestauranteAndEstado(idRestaurante, estado, pageable);
        List<PedidoEntity> pedidoEntityList = pedidoEntities.getContent();
        if (!pedidoEntityList.isEmpty()) {
            pedidoModelList = pedidosEntityMapper.toPedidosModel(pedidoEntityList);
            return new PedidosPageableModel(pedidoEntities.getTotalPages(),
                    pedidoModelList);
        } else {
            throw new NoDataFoundException("Pedido no encontrado por estado seleccionado");
        }
    }

    @Override
    public PedidoModel findPedidoById(Integer idPedido) {
        Optional<PedidoEntity> pedidoEntityOptional = pedidosRepository.findById(idPedido);
        if(pedidoEntityOptional.isPresent()){
            PedidoEntity pedidoEntity = pedidoEntityOptional.get();
            return pedidosEntityMapper.toModel(pedidoEntity);
        } else {
            throw new NoDataFoundException("Pedido no encontrado por estado seleccionado");
        }
    }

    @Override
    public PedidoModel findPedidoByCodigoEntrega(String codigoEntrega) {
        PedidoEntity pedidoEntity = pedidosRepository.findByCodigoEntrega(codigoEntrega);
        if(pedidoEntity != null){
            return pedidosEntityMapper.toModel(pedidoEntity);
        } else{
            throw new NoDataFoundException("Codigo de entrega no encontrado para pedido");
        }
    }
}

