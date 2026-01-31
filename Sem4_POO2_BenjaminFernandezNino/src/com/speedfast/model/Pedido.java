package com.speedfast.model;

import com.speedfast.interfaces.Cancelable;
import com.speedfast.interfaces.Despachable;
import com.speedfast.interfaces.Rastreable;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase base abstracta para la gestión de pedidos en SpeedFast.
 * Implementa el patrón Template Method para estandarizar el flujo de entrega.
 * @author Benjamín Fernández-Niño
 * @version 2.0
 */
public abstract class Pedido implements Rastreable, Despachable, Cancelable {
    private String idPedido;
    private String direccionEntrega;
    private double distanciaKm;
    private String nombreRepartidor;
    private List<String> historialEstados;

    /**
     * Constructor para inicializar un pedido base.
     * @param idPedido Identificador único.
     * @param direccionEntrega Destino final.
     * @param distanciaKm Distancia en kilómetros.
     */
    public Pedido(String idPedido, String direccionEntrega, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
        this.historialEstados = new ArrayList<>();
        registrarEvento("Pedido creado en sistema.");
    }

    /**
     * Template Method que define el esqueleto del algoritmo de entrega.
     * Es final para garantizar que el orden de los pasos no sea alterado.
     */
    public final void ejecutarFlujoPedido() {
        System.out.println("\n>>> PROCESANDO PEDIDO: " + idPedido);
        mostrarResumen();

        // Paso1 : Asignación (Solo si no hay uno previo manual)
        if (this.nombreRepartidor == null) {
            asignarRepartidor();
        }

        // Paso 2: Cálculo de tiempo
        int tiempo = calcularTiempoEntrega();
        System.out.println("Cálculo logístico: " + tiempo + " min estimados.");

        // Paso 3: Despacho
        despachar();
        registrarEvento("Flujo de entrega finalizado.");
    }

    /**
     * Paso abstracto: Asigna un repartidor según el tipo de pedido.
     */
    public abstract void asignarRepartidor();

    /**
     * Paso abstracto: Calcula el tiempo estimado de entrega.
     * @return tiempo estimado en minutos.
     */
    public abstract int calcularTiempoEntrega();

    /**
     * Registro en historial del pedido.
     * @param evento Descripción de la acción realizada.
     */
    protected void registrarEvento(String evento) {
        this.historialEstados.add(evento);
    }

    /**
     * Establece el nombre del repartidor asignado.
     * @param nombre Nombre del empleado o unidad.
     */
    protected void setNombreRepartidor(String nombre) {
        this.nombreRepartidor = nombre;
    }

    /**
     * Sobrecarga para permitir la asignación manual de un repartidor.
     * @param nombre Nombre del repartidor asignado.
     */
    public void asignarRepartidor(String nombre) {
        setNombreRepartidor(nombre);
        registrarEvento("Asignación manual: " + nombre);
    }

    @Override
    public void despachar() {
        registrarEvento("Pedido despachado a ruta.");
        System.out.println("Acción: El pedido " + idPedido + " ha sido despachado.");
    }

    @Override
    public void cancelar() {
        registrarEvento("Pedido cancelado.");
        System.out.println("Acción: Pedido " + idPedido + " CANCELADO.");
    }

    @Override
    public void verHistorial() {
        System.out.println("Trazabilidad [" + idPedido + "]: " + historialEstados);
    }

    /**
     * Muestra en consola los datos básicos del pedido.
     */
    public void mostrarResumen() {
        System.out.println("--------------------------------------");
        System.out.println("ID Pedido: " + idPedido);
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Distancia: " + distanciaKm + " km");
        System.out.println("Repartidor: " + (nombreRepartidor != null ? nombreRepartidor : "Pendiente"));
    }

    // Getters
    public double getDistanciaKm() { return distanciaKm; }
    public String getIdPedido() { return idPedido; }
}