package com.speedfast.model;

/**
 * Representa la entidad pedido en el sistema SpeedFast.
 * Esta clase actúa como el objeto de datos que transita por la Zona de Carga
 * y es procesado de forma concurrente por los repartidores.
 * Se integra el uso del enum EstadoPedido para garantizar
 * la integridad de los estados durante la sincronización de hilos.
 * @author Benjamín Fernández-Niño
 * @version 3.0
 */
public class Pedido {

    /** Identificador único numérico del pedido */
    private int id;

    /** Dirección física donde debe entregarse el pedido */
    private String direccionEntrega;

    /** Estado actual del pedido dentro del flujo logístico */
    private EstadoPedido estado;

    /**
     * Constructor para inicializar un nuevo pedido.
     * Por defecto, todo pedido inicia con el estado PENDIENTE.
     * @param id El identificador único del pedido.
     * @param direccionEntrega La dirección de destino del envío.
     */
    public Pedido(int id, String direccionEntrega) {
        this.id = id;
        this.direccionEntrega = direccionEntrega;
        this.estado = EstadoPedido.PENDIENTE;
    }

    /**
     * Obtiene el identificador único del pedido.
     * @return id numérico.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del pedido.
     * @param id Nuevo identificador.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene la dirección de entrega registrada.
     * @return Cadena con la dirección.
     */
    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    /**
     * Establece una nueva dirección de entrega.
     * @param direccionEntrega Nueva dirección de destino.
     */
    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    /**
     * Obtiene el estado actual del pedido.
     * @return Objeto del enum EstadoPedido (PENDIENTE, EN_REPARTO o ENTREGADO).
     */
    public EstadoPedido getEstado() {
        return estado;
    }

    /**
     * Actualiza el estado del pedido durante el proceso de entrega.
     * @param estado El nuevo estado que asumirá el pedido.
     */
    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    /**
     * Proporciona una representación textual del objeto Pedido.
     * Útil para la depuración y el seguimiento en consola durante la ejecución de hilos.
     * @return Detalles del pedido e identificación de su estado actual.
     */
    @Override
    public String toString() {
        return "Pedido [ID=" + id + ", Dirección=" + direccionEntrega + ", Estado=" + estado + "]";
    }
}