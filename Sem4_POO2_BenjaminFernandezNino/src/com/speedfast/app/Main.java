package com.speedfast.app;

import com.speedfast.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Clase principal que orquesta la simulación del sistema SpeedFast.
 * Esta clase se encarga de instanciar los repartidores, asignarles pedidos
 * y ejecutar sus tareas de forma paralela utilziando un pool de hilos.
 * @author Benjamín Fernández-Niño
 * @version 3.0
 */
public class Main {
    public static void main(String[] args) {
        //1. Preparar datos del repartidor 1
        List<Pedido> pedidosAndres = new ArrayList<>();
        pedidosAndres.add(new PedidoComida("C-100", "Los Gladiolos 742", 3.5));
        pedidosAndres.add(new PedidoExpress("C-103", "Los Pintores 658", 1.2));

        //2. Preparar datos del repartidor 2
        List<Pedido> pedidosSarah = new ArrayList<>();
        pedidosSarah.add(new PedidoEncomienda("B-156", "La cascada 684", 15.0));
        pedidosSarah.add(new PedidoComida("B-180", "El conquistador 700", 5.1));

        //3. Preparar datos del repartidor 3
        List<Pedido> pedidosDiego = new ArrayList<>();
        pedidosDiego.add(new PedidoExpress("B-300", "Lo Martinez 6824", 1.0));
        pedidosDiego.add(new PedidoEncomienda("B-189", "El Fundo 7030", 25.0));

        //4. Intanciación de los objetos de Repartidor, implentan Runabble
        Repartidor repartidor1 = new Repartidor("Andres", pedidosAndres);
        Repartidor repartidor2 = new Repartidor("Sarah", pedidosSarah);
        Repartidor repartidor3 = new Repartidor("Diego", pedidosDiego);

        System.out.println("======================================================");
        System.out.println("   INICIO DE SIMULACIÓN LOGÍSTICA CONCURRENTE");
        System.out.println("======================================================");

        /**
         * 5. Configuración del ExecutorService.
         * Se crea un pool de hilos fijo para ejecutar a los repartidores en parelelo.
         */
        ExecutorService executor = Executors.newFixedThreadPool(3);

        //6. Ejecución de los hilos
        executor.execute(repartidor1);
        executor.execute(repartidor2);
        executor.execute(repartidor3);

        /**
         *7. Gestion del cierre del ExcecutorService.
         * Detiene la aceptación de nuevas tareas y espera a que las actuales terminen.
         */
        executor.shutdown();

        try{
            if(!executor.awaitTermination(2, TimeUnit.MINUTES)) {
                executor.shutdownNow();
                System.out.println("La simulación tardó demasiado y fue forzado a detenerse.");
            }
        }catch(InterruptedException e){
            executor.shutdownNow();
            Thread.currentThread().interrupt();
            System.out.println("El proceso principal fue interrrumpido inesperadamente.");
        }

        System.out.println("\n======================================================");
        System.out.println("   SIMULACIÓN FINALIZADA: Todos los repartidores regresaron");
        System.out.println("======================================================");

    }
}
