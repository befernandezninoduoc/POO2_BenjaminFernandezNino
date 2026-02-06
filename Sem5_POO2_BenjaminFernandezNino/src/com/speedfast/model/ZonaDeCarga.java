package com.speedfast.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a una zona de carga, es el recurso compartido donde se almacenan los pedidos.
 * Uso de métodos sincronizados para controlar el acceso concurrente
 * de múltiples repartidores y garantizar la integridad de los datos.
 */
public class ZonaDeCarga {

    private List<Pedido> listaPedidos;

    public ZonaDeCarga() {
        this.listaPedidos = new ArrayList<>();
    }

    /**
     * Agrega un pedido a la zona de carga de manera segura.
     * @param p El pedido a agregar.
     */
    public synchronized void agregarPedido(Pedido p) {
        listaPedidos.add(p);
        System.out.println("ZonaDeCarga: Ingresó el pedido ID " + p.getId());
    }

    /**
     * Permite retirar un pedido pendiente de la lista.
     * El método es synchronized para evitar que dos repartidores retiren el mismo objeto.
     * @return El siguiente Pedido disponible o null si no hay pedidos.
     */
    public synchronized Pedido retirarPedido() {
        if (!listaPedidos.isEmpty()) {
            // Retira y retorna el primer elemento de la lista
            return listaPedidos.remove(0);
        }
        return null; // Retorna null por si la zona está vacía
    }

    /**
     * Verifica si quedan pedidos (método auxiliar).
     */
    public synchronized boolean hayPedidos() {
        return !listaPedidos.isEmpty();
    }
}