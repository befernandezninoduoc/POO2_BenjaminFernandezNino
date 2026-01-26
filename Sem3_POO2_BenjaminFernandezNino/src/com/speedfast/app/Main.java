package com.speedfast.app;

import com.speedfast.model.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que actúa como el punto de entrada al sistema SpeedFast.
 * Se utiliza el patrón Template Method para procesar los pedidos,
 * delegando la responsabilidad de la secuencia lógica a la clase base Pedido.
 * @author  Fernández-Niño
 * @version 2.0
 */
public class Main {

    /**
     * Punto de entrada de la aplicación.
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        // Creación de la colección de pedidos (Polimorfismo)
        List<Pedido> pedidos = new ArrayList<>();

        // Poblar la lista con diferentes tipos de pedidos
        pedidos.add(new PedidoComida("C-202", "Calle Los Alerces 450", 4.2));
        pedidos.add(new PedidoEncomienda("E-800", "Parque Industrial Bodega 4", 12.5));
        pedidos.add(new PedidoExpress("X-101", "Torre Gran Costanera", 1.5));
        pedidos.add(new PedidoComida("B-123","las Lilas 3123", 20));

        System.out.println("==============================================");
        System.out.println("   BIENVENIDO AL SISTEMA SPEEDFAST V2.0");
        System.out.println("==============================================");

        // Procesamiento de pedidos
        for (Pedido p : pedidos) {

            /**
             * Caso especial: Asignación manual antes de ejecutar el flujo
             * Si es un pedido Express, simulamos una asignación manual por prioridad.
             */
            if (p instanceof PedidoExpress) {
                p.asignarRepartidor("Andrés 'Velocidad' Rivas");
            }

            /** Uso de template method:
             * Invocamos el método plantilla que ya conoce el orden correcto de ejecución.
             */
            p.ejecutarFlujoPedido();

            /**
             * Caso especial: Cancelación
             * 1. Cancelación automática por regla de negocio (Comida > 10km)
             * 2. Cancelación manual por ID
             */
            if (p instanceof PedidoComida && p.getDistanciaKm() > 10) {
                p.cancelar();
            }
            else if (p.getIdPedido().equals("E-800")) {
                // Ejemplo de cancelación puntual por ID de pedido
                System.out.println("--> Solicitud de cliente: Cancelando encomienda por solicitud del cliente.");
                p.cancelar();
            }
        }

        /**
         * Reporte final de trazabilidad (Interfaz Rastreable)
         */
        System.out.println("\n==============================================");
        System.out.println("       RESUMEN DE HISTORIALES (RASTREO)");
        System.out.println("==============================================");
        for (Pedido p : pedidos) {
            p.verHistorial();
        }
    }
}