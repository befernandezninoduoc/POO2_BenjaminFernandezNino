package com.speedfast.controlador;

import com.speedfast.modelo.Pedido;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador que gestionna la lógica de almacenamiento de pedidos.
 */

public class PedidoControlador {
    private static List<Pedido> ListaPedidos = new ArrayList<>();

    /**
     * Agrega un pedido a la lista global.
     * @param p que se registra.
     */
    public static void agregarPedido(Pedido p) {ListaPedidos.add(p);
    }

    /**
     * Retorna la lista de pedidos registrados.
     * @return lista de pedidos
     */
    public static List<Pedido> getListaPedidos() {
        return ListaPedidos;
    }
}
