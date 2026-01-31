package com.speedfast.model;

/**
 * Especialización para entrega de alimentos.
 * Implementa los pasos específicos definidos por la clase base Pedido.
 */
public class PedidoComida extends Pedido {

    public PedidoComida(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    /**
     * Implementación del paso de asignación para comida.
     */
    @Override
    public void asignarRepartidor() {
        // Usamos el método protegido del padre en lugar de acceso directo
        setNombreRepartidor("Moto-Repartidor de Alimentos");
        registrarEvento("Asignación: Se ha vinculado una motocicleta para entrega rápida.");
    }

    /**
     * Implementación del cálculo de tiempo para comida.
     */
    @Override
    public int calcularTiempoEntrega() {
        return 15 + (int)(getDistanciaKm() * 2);
    }
}