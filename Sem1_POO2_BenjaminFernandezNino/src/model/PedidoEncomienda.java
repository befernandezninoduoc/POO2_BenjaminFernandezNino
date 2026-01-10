package model;

/**
 * Representa el envío de documentos o paquetes.
 * Requiere validaciones físicas (peso y empaque).
 */
public class PedidoEncomienda extends Pedido {

    public PedidoEncomienda(String idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Encomienda");
    }

    /**
     * Sobrescribe la asignación para validar logística de carga.
     */
    @Override
    public void asignarRepartidor() {
        System.out.println("[SISTEMA ENCOMIENDA] Validando peso del paquete y tipo de empaque.");
    }

    /**
     * Sobrescribe la asignación con validación de peso.
     * @param nombreRepartidor Nombre del repartidor asignado al paquete.
     */
    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("[SISTEMA ENCOMIENDA] Validando carga para " + nombreRepartidor + ". Peso dentro del rango. Asignado.");
    }
}