package com.plazoletapowerUp.infrastructure.input.rest;

import com.plazoletapowerUp.application.dto.request.PedidoRequestDto;
import com.plazoletapowerUp.application.dto.response.PedidoPageResponseDto;
import com.plazoletapowerUp.application.dto.response.RestaurantePageResponseDto;
import com.plazoletapowerUp.application.handler.IPedidosHandler;
import com.plazoletapowerUp.infrastructure.enums.PedidoEstadoEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
@Validated
public class PedidosRestController {

    private final IPedidosHandler pedidosHandler;

    @Operation(summary = "Add a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> createPedido(@RequestBody PedidoRequestDto pedidoRequestDto) {
        pedidosHandler.savePedido(pedidoRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get order by estado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found",
                    content = @Content(schema = @Schema(implementation = RestaurantePageResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Orders not found", content = @Content)
    })
    @GetMapping("/{idRestaurante}")
    public ResponseEntity<PedidoPageResponseDto> getPedidosByEstado (@PathVariable Integer idRestaurante,
                                                                     @RequestParam (name = "id") Integer idEmpleado,
                                                                     @RequestParam(defaultValue = "pendiente")
                                                                     String estado,
                                                                     @RequestParam (defaultValue = "1")
                                                                     @Min(1) Integer page,
                                                                     @RequestParam (defaultValue = "10")
                                                                     @Min(1) Integer numElementsPage){
        PedidoPageResponseDto pedidoPageResponseDto =
                pedidosHandler.findPedidosByEstado(idEmpleado, idRestaurante, estado, page, numElementsPage);
        if ( pedidoPageResponseDto != null){
            return ResponseEntity.ok(pedidoPageResponseDto);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Assign chef and change order status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assigned employee and status changed successfully",
                    content = @Content)
    })
    @PatchMapping("/preparar-pedido/{idPedido}")
    public ResponseEntity<Void> updateEmpleadoAndEstado (@PathVariable Integer idPedido,
                                                         @RequestParam Integer idChef){
        pedidosHandler.updateIdEmpleado(idChef, idPedido, PedidoEstadoEnum.EN_PREPARACION.getDbValue());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Update order to ready and generate security code for delivery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status to ready updated successfully",
                    content = @Content)
    })
    @PatchMapping("/actualizar-pedido-listo/{idPedido}")
    public ResponseEntity<Void> updatePedidoToReady (@PathVariable Integer idPedido){
        pedidosHandler.updatePedidoToReady(idPedido);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Deliver order and update order status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order delivered", content = @Content)
    })
    @PatchMapping("/entregar-pedido/{codigoEntrega}")
    public ResponseEntity<Void> updatePedidoToDelivered ( @PathVariable String codigoEntrega){
        pedidosHandler.updatePedidoToDelivered(codigoEntrega);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Cancel order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order canceled", content = @Content)
    })
    @PatchMapping("/cancelar-pedido/{idPedido}")
    public ResponseEntity<Void> cancelarPedido (@PathVariable Integer idPedido){
        pedidosHandler.cancelarPedido(idPedido);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

