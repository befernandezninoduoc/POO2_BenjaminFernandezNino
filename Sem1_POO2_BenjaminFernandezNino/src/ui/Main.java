package ui;

import model.*;

/**
 * Clase principal que ejecuta la simulación del sistema SpeedFast.
 */
public class Main {
    /**
     * Punto de entrada de la aplicación.
     */
    public static void main(String[] args) {
        // Creación de instancias de cada tipo de pedido
        Pedido pedidoPizza = new PedidoComida("COM-101", "Calle 10 #45");
        Pedido pedidoCaja = new PedidoEncomienda("ENC-202", "Av. Libertador 100");
        Pedido pedidoMedicinas = new PedidoExpress("EXP-303", "Farmacia Central");

        System.out.println("--- INICIANDO GESTIÓN DE PEDIDOS SPEEDFAST ---\n");

        // Prueba Pedido Comida
        ejecutarPrueba(pedidoPizza, "Veronica López");

        // Prueba Pedido Encomienda
        ejecutarPrueba(pedidoCaja, "Beatriz Lobos");

        // Prueba Pedido Express
        ejecutarPrueba(pedidoMedicinas, "Carlos Rucalon");
    }

    /**
     * Método auxiliar para ejecutar las dos versiones de asignarRepartidor
     * aprovechando el polimorfismo.
     * @param p El objeto de tipo Pedido (o subclase).
     * @param repartidor El nombre del repartidor para la sobrecarga.
     */
    public static void ejecutarPrueba(Pedido p, String repartidor) {
        System.out.println("Procesando: " + p.getClass().getSimpleName());
        p.asignarRepartidor(); // Versión sobrescrita
        p.asignarRepartidor(repartidor); // Versión sobrecargada
        System.out.println();
    }
}