package model;

/**
 * Representa compras de farmacia o supermercado.
 * Prioriza la cercanía y velocidad de respuesta.
 */
public class PedidoExpress extends Pedido {

    public PedidoExpress(String idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Express");
    }

    /**
     * Sobrescribe la asignación para buscar por geolocalización inmediata.
     */
    @Override
    public void asignarRepartidor() {
        System.out.println("[SISTEMA EXPRESS] Localizando repartidor en un radio de 3km con disponibilidad...");
    }

    /**
     * Sobrescribe la asignación confirmando la cercanía del repartidor.
     * @param nombreRepartidor Nombre del repartidor más cercano.
     */
    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("[SISTEMA EXPRESS] El repartidor " + nombreRepartidor + " se encuentra a 60m. Asignación inmediata.");
    }
}