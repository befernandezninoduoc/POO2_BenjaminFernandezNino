package com.speedfast.model;

/**
 * Enumeración que define los posibles estados de un pedido en el sistema.
 * Mejora la integridad de los datos evitando errores de escritura.
 */
public enum EstadoPedido {
    PENDIENTE,
    EN_REPARTO,
    ENTREGADO
}
