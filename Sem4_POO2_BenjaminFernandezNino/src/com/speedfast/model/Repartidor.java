package com.speedfast.model;

import java.util.List;
import java.util.Random;

/**
 * Representa a un repartidor que procesa sus pedidos de forma concurrente.
 * Implementa Runnable para ser ejecutado como un hilo independiente.
 */
public class Repartidor implements Runnable {
    private String nombre;
    private List<Pedido> listaPedidos;
    private Random random = new Random();

    /**
     * Constructor del repartidor.
     * @param nombre Nombre del repartidor
     * @param listaPedidos Lista de pedidos asignados
     */
    public Repartidor(String nombre, List<Pedido> listaPedidos) {
        this.nombre = nombre;
        this.listaPedidos = listaPedidos;
    }

    /**
     * Metodo run que ejecuta el hilo.
     * Realiza la entrega secuencial de los pedidos asignados.
     */
    @Override
    public void run() {
        System.out.println("[HILO] Repartidor " + nombre + " ha iniciando su joranada con " + listaPedidos.size() + " pedidos.");

     for (Pedido pedido : listaPedidos) {
         //Paso 1. nicio de la entrega
         System.out.println("[INICIO] Repartidor " + nombre + " Procesando pedido: " + pedido.getIdPedido());

         // Flujo de Template Method
         pedido.asignarRepartidor();
         pedido.ejecutarFlujoPedido();

         try{
             //Paso 2: Simulación con Thread.sleep y valores aleatorios
             // Tiempo aleatorio entre 1000 y 4000 milisegundos, es decir 1 a 4 segundos.
             int tiempoSimulacion = (random.nextInt(4) + 1) * 1000;
             System.out.println("[TRAYECTO] " + nombre + " pedido en ruta... (Simulación: " + (tiempoSimulacion/1000) + "s)");

             //El hilo se detiene aqui, permitiendo que otros repartidores puedan avanzar
             Thread.sleep(tiempoSimulacion);
             System.out.println("[TERMINO] " + nombre + " entrega completada del pedido..." + pedido.getIdPedido());

         } catch (InterruptedException e){
             System.err.println("Error: el hilo de " + nombre + " ha sido interrumpido.");
             //restablecer el estado de interrupción.
             Thread.currentThread().interrupt();
         }
     }
        System.out.println("*** REPARTIDOR " + nombre + " ha terminado todas sus entregas ***");
    }
}
