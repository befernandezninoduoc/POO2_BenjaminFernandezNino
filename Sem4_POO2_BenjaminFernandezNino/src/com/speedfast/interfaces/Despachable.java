package com.speedfast.interfaces;

/**
 * Define el contrato para objetos que pueden ser enviados a destino.
 */
public interface Despachable {
    /**
     * Ejecuta la lógica necesaria para iniciar el traslado del pedido
     */
    void despachar();
}

