package com.speedfast.app;

import com.speedfast.model.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Clase principal
 * Simula el acceso concurrente a una Zona de Carga compartida.
 * @author Benjamín Fernández-Niño
 * @version 3.0
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   SISTEMA DE CARGA SINCRONIZADA SPEEDFAST V3.0");
        System.out.println("=================================================");

        // 1. Instanciar el recurso compartido (ZonaDeCarga)
        ZonaDeCarga zonaComun = new ZonaDeCarga();

        // 2. Cargar pedidos al sistema
        System.out.println("\n--- Cargando pedidos en la Zona Común ---");
        zonaComun.agregarPedido(new Pedido(101, "Los Gladiolos 742"));
        zonaComun.agregarPedido(new Pedido(102, "Los Pintores 658"));
        zonaComun.agregarPedido(new Pedido(103, "La cascada 684"));
        zonaComun.agregarPedido(new Pedido(104, "El conquistador 700"));
        zonaComun.agregarPedido(new Pedido(105, "Lo Martinez 6824"));
        zonaComun.agregarPedido(new Pedido(106, "El Fundo 7030"));

        System.out.println("-------------------------------------------------\n");

        // 3. Pool de hilos para los repartidores

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.execute(new Repartidor("Andrés", zonaComun));
        executor.execute(new Repartidor("Beatriz", zonaComun));
        executor.execute(new Repartidor("Carlos", zonaComun));

        // 4. Iniciar el cierre y esperar
        executor.shutdown();

        try {
            // Esperamos hasta que todos terminen
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        // 5. Mensaje final
        System.out.println("\n=================================================");
        System.out.println(" Todos los pedidos han sido entregados correctamente");
        System.out.println("=================================================");
    }
}