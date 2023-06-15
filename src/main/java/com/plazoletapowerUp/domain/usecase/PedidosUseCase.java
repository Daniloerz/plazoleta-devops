package com.plazoletapowerUp.domain.usecase;

import com.plazoletapowerUp.domain.api.IPedidosServicePort;
import com.plazoletapowerUp.domain.client.ITwilioClientPort;
import com.plazoletapowerUp.domain.client.IUsuarioClientPort;
import com.plazoletapowerUp.domain.exception.ValidationException;
import com.plazoletapowerUp.domain.model.*;
import com.plazoletapowerUp.domain.responseDtoModel.UsuarioResponseDtoModel;
import com.plazoletapowerUp.domain.spi.IPedidosPersistencePort;
import com.plazoletapowerUp.domain.spi.IPlatosPersistencePort;
import com.plazoletapowerUp.domain.spi.IRestauranteEmpleadoPersistencePort;
import com.plazoletapowerUp.domain.spi.IRestaurantePersistencePort;
import com.plazoletapowerUp.infrastructure.enums.PedidoEstadoEnum;
import com.plazoletapowerUp.infrastructure.exception.TwilioServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PedidosUseCase implements IPedidosServicePort {

    public Logger log = LoggerFactory.getLogger(PedidosUseCase.class);

    private final IPedidosPersistencePort pedidosPersistencePort;
    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IRestauranteEmpleadoPersistencePort restauranteEmpleadoPersistencePort;
    private final ITwilioClientPort twilioClientPort;
    private final IUsuarioClientPort usuarioClientPort;
    private final IPlatosPersistencePort platosPersistencePort;

    private static final String ERROR = "Empleado no encontrado";


    public PedidosUseCase(IPedidosPersistencePort pedidosPersistencePort,
                          IRestaurantePersistencePort restaurantePersistencePort,
                          IRestauranteEmpleadoPersistencePort restauranteEmpleadoPersistencePort,
                          ITwilioClientPort twilioClientPort, IUsuarioClientPort usuarioClientPort,
                          IPlatosPersistencePort platosPersistencePort) {
        this.pedidosPersistencePort = pedidosPersistencePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.restauranteEmpleadoPersistencePort = restauranteEmpleadoPersistencePort;
        this.twilioClientPort = twilioClientPort;
        this.usuarioClientPort = usuarioClientPort;
        this.platosPersistencePort = platosPersistencePort;
    }

    @Override
    public void savePedidoSP(PedidoModel pedidoModel, List<PedidoPlatosModel> pedidoPlatosModelList) {
        this.validatePedido(pedidoModel);
        this.validatePlatos(pedidoPlatosModelList);
        pedidosPersistencePort.savePedidosPP(pedidoModel, pedidoPlatosModelList);
    }

    @Override
    public PedidosPageableModel findPedidosByEstado(Integer idEmpleado,
                                                    Integer idRestaurante,
                                                    String estado,
                                                    Integer page,
                                                    Integer numElemPage) {
        this.validateRestauranteEmpleado(idEmpleado, idRestaurante);
        return pedidosPersistencePort.findPedidoByEstado
                (idEmpleado, idRestaurante, estado, page, numElemPage);
    }

    @Override
    public void updateEmpleadoAndEstado(Integer idEmpleado, Integer idPedido, String estado) {
        RestauranteEmpleadoModel restauranteEmpleadoModel =
                this.validateRestauranteEmpleado(idEmpleado);
        PedidoModel pedidoModel = pedidosPersistencePort.findPedidoById(idPedido);

        if(!pedidoModel.getEstado().equals(PedidoEstadoEnum.PENDIENTE.getDbValue())){
            log.error("Estado incorrecto de pedido para ser actualizado");
            throw new ValidationException("Estado incorrecto de pedido para ser actualizado");
        }

        if(!(restauranteEmpleadoModel.getIdRestaurante().equals(pedidoModel.getIdRestaurante()))){
            log.error("Empleado no autorizado");
            throw new ValidationException("Empleado no autorizado");
        }

        pedidoModel.setIdEmpleado(idEmpleado);
        pedidoModel.setEstado(estado);

        pedidosPersistencePort.savePedido(pedidoModel);
    }

    @Override
    public void updatePedidoToReady(Integer idPedido) {
        PedidoModel pedidoById = pedidosPersistencePort.findPedidoById(idPedido);
        pedidoById.setEstado(PedidoEstadoEnum.LISTO.getDbValue());
        pedidoById.setCodigoEntrega(buildTwilioCode());
        pedidosPersistencePort.savePedido(pedidoById);

        this.sendMessage(pedidoById.getIdCliente(), pedidoById.getCodigoEntrega());
    }

    @Override
    public void updatePedidoToDelivered(String codigoEntrega) {
        PedidoModel pedidoModel = pedidosPersistencePort.findPedidoByCodigoEntrega(codigoEntrega);
        this.isPedidoListo(pedidoModel);
        pedidoModel.setEstado(PedidoEstadoEnum.ENTREGADO.getDbValue());
        pedidosPersistencePort.savePedido(pedidoModel);
    }

    @Override
    public void cancelarPedido(Integer idPedido) {
        PedidoModel pedidoModel = pedidosPersistencePort.findPedidoById(idPedido);

        if(!pedidoModel.getEstado().equals(PedidoEstadoEnum.PENDIENTE.getDbValue())){
            log.error("Lo sentimos, tu pedido ya está en preparación y no puede cancelarse");
            throw new ValidationException("Lo sentimos, tu pedido ya está en preparación y no puede cancelarse");
        }
        pedidoModel.setEstado(PedidoEstadoEnum.CANCELADO.getDbValue());
        pedidosPersistencePort.savePedido(pedidoModel);

    }

    public void validatePedido(PedidoModel pedidoModel){
        this.validateUsuarioCliente(pedidoModel);
        this.validateRestaurante(pedidoModel);
        this.validatePedidosEnProcesoCliente(pedidoModel);
    }

    public void validatePedidosEnProcesoCliente(PedidoModel pedidoModel){

        List<PedidoModel> pedidoModelList = pedidosPersistencePort
                .findPedidosByIdClientePP(pedidoModel.getIdCliente());
        boolean pedidoEncontrado =  pedidoModelList.stream().
                anyMatch(pedidoCliente -> (pedidoCliente.getEstado()
                        .equals(PedidoEstadoEnum.PENDIENTE.getDbValue())
                        || pedidoCliente.getEstado()
                        .equals(PedidoEstadoEnum.EN_PREPARACION.getDbValue())
                        || pedidoCliente.getEstado()
                        .equals(PedidoEstadoEnum.LISTO.getDbValue())));
        if(pedidoEncontrado){
            log.error("El cliente tiene un pedido en proceso");
            throw new ValidationException("El cliente tiene un pedido en proceso");
        }
    }

    private void validateRestaurante(PedidoModel pedidoModel) {
        RestauranteModel restauranteModel = restaurantePersistencePort.findByIdPP(pedidoModel.getIdRestaurante());
        if (restauranteModel == null){
            log.error("Restaurante no encontrado");
            throw new ValidationException("Restaurante no encontrado");
        }
    }

    private void validateUsuarioCliente(PedidoModel pedidoModel) {
        UsuarioResponseDtoModel usuarioById = usuarioClientPort.findUsuarioById(pedidoModel.getIdCliente());
        if (usuarioById == null){
            log.error("Usuario no encontrado");
            throw new ValidationException("Usuario no encontrado");
        }
    }

    private void validatePlatos(List<PedidoPlatosModel> pedidoPlatosModelList) {
        List<Integer> platosNotFound = new ArrayList<>();


        for (int i = 0; i < pedidoPlatosModelList.size(); i++) {
            try {
                platosPersistencePort.findPlatoById(pedidoPlatosModelList.get(i).getIdPlato());
            } catch (Exception e) {
                platosNotFound.add(pedidoPlatosModelList.get(i).getIdPlato());
            }

            if (!(platosNotFound.isEmpty())) {
                throw new ValidationException("Platos del pedido no encontrados. Id platos no encontrados: "
                        + platosNotFound);
            }
        }
    }

    private void validateRestauranteEmpleado(Integer idEmpleado, Integer idRestaurante){
        RestauranteEmpleadoModel restauranteEmpleadoModel =
                restauranteEmpleadoPersistencePort.findEmpleadoByIdAndIdRestaurante(idEmpleado, idRestaurante);
        if(restauranteEmpleadoModel == null){
            log.error(ERROR);
            throw new ValidationException(ERROR);
        }
        if (!(restauranteEmpleadoModel.getIdRestaurante().equals(idRestaurante))){
            log.error("Empleado no autorizado para obtener pedidos de este restautante");
            throw new ValidationException("Empleado no autorizado para obtener pedidos de este restautante");
        }
    }

    private RestauranteEmpleadoModel validateRestauranteEmpleado(Integer idEmpleado){
        RestauranteEmpleadoModel restauranteEmpleadoModel =
                restauranteEmpleadoPersistencePort.findEmpleadoById(idEmpleado);
        if(restauranteEmpleadoModel == null){
            log.error(ERROR);
            throw new ValidationException(ERROR);
        }
        return restauranteEmpleadoModel;
    }


    private String buildTwilioCode() {
        String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String cadena = "";
        for (int x = 0; x < 5; x++) {
            int indiceAleatorio = getNumeroAleatorioEnRango(0, banco.length() - 1);
            char caracterAleatorio = banco.charAt(indiceAleatorio);
            cadena += caracterAleatorio;
        }
        return cadena;
    }

    private int getNumeroAleatorioEnRango(int minimo, int maximo) {
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }

    private void sendMessage (Integer idCliente, String codigo){
        try{
            UsuarioResponseDtoModel usuarioById = usuarioClientPort.findUsuarioById(idCliente);
            MessageModel messageModel = new MessageModel(usuarioById.getCelular(), codigo);
            boolean result = twilioClientPort.sendMessage(messageModel);
            if(!result){
                throw new TwilioServiceException("Error invocando twilio service");
            }
        } catch (Exception e){
            log.error("Error invocando twilio service", e);
        }
    }

    private void isPedidoListo(PedidoModel pedidoModel) {
        if (!pedidoModel.getEstado().equals(PedidoEstadoEnum.LISTO.getDbValue())) {
            String errorMessage = String.format("El estado del pedido debe ser %s",
                    PedidoEstadoEnum.LISTO.getDbValue().toUpperCase());
            log.error(errorMessage);
            throw new ValidationException(errorMessage);
        }
    }



}
