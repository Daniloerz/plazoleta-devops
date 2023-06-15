package com.plazoletapowerUp.infrastructure.enums;

public enum PedidoEstadoEnum {
    PENDIENTE("pendiente"),
    EN_PREPARACION("en preparacion"),
    LISTO("listo"),
    ENTREGADO("entregado"),
    CANCELADO("cancelado");

    private String dbValue;

    private PedidoEstadoEnum(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }
}

