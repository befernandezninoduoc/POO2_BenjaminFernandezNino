package model;

/**
 * Representa un pedido de restaurante.
 * Requiere validación de equipo térmico.
 */
public class PedidoComida extends Pedido {

    public PedidoComida(String idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Comida");
    }

    /**
     * Sobrescribe la asignación para filtrar por mochila térmica.
     */
    @Override
    public void asignarRepartidor() {
        System.out.println("[SISTEMA COMIDA] Buscando repartidor con mochila térmica certificada...");
    }

    /**
     * Sobrescribe la asignación con validación de equipo para el repartidor.
     * @param nombreRepartidor Nombre del repartidor evaluado.
     */
    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("[SISTEMA COMIDA] Verificando equipo de " + nombreRepartidor + "... Mochila térmica: OK. Asignado.");
    }
}