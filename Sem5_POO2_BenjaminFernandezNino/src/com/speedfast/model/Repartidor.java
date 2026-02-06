package com.speedfast.model;

import java.util.Random;

/**
 * Representa al trabajador que procesa pedidos concurrentemente.
 * Implementa Runnable para ejecutarse como un hilo independiente.
 */
public class Repartidor implements Runnable {
    private String nombre;
    private ZonaDeCarga zonaDeCarga;
    private Random random;

    /**
     * Constructor del repartidor.
     * @param nombre Nombre del repartidor.
     * @param zonaDeCarga La instancia compartida de la zona de carga.
     */
    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
        this.random = new Random();
    }

    @Override
    public void run() {
        System.out.println("*** Repartidor " + nombre + " iniciando jornada");

        try {
            // El ciclo intenta retirar pedidos mientras la zona tenga trabajo
            // o hasta que retirarPedido() devuelva null.
            Pedido pedido;

            // Intentamos retirar un pedido de la zona compartida
            while ((pedido = zonaDeCarga.retirarPedido()) != null) {

                // 1. Cambiar estado a EN_REPARTO
                pedido.setEstado(EstadoPedido.EN_REPARTO);
                System.out.println("Repartidor " + nombre + " retiró pedido ID: " + pedido.getId() + " [EN_REPARTO]");

                // 2. Simular entrega (Thread.sleep)
                int tiempoEntrega = (random.nextInt(3) + 1) * 1000; // 1 a 3 segundos
                Thread.sleep(tiempoEntrega);

                // 3. Cambiar estado a ENTREGADO
                pedido.setEstado(EstadoPedido.ENTREGADO);
                System.out.println("+++ Repartidor " + nombre + " completó entrega ID: " + pedido.getId() + " [ENTREGADO] (" + (tiempoEntrega/1000) + "s)");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("El repartidor " + nombre + " fue interrumpido.");
        }

        System.out.println("*** Repartidor " + nombre + " finalizó su jornada (No hay más pedidos).");
    }
}