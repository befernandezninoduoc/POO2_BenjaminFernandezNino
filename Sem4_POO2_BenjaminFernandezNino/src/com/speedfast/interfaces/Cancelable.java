package com.speedfast.interfaces;

/**
 * Define el contrato para permitir anular una operación
 */
public interface Cancelable {
    /**
     * Cancela el pedido y actualiza su estado interno.
     */
    void cancelar();
}
