package model;

/**
 * Clase base que representa un pedido genérico dentro del sistema SpeedFast.
 * Implementa la interfaz IAsignable para garantizar la capacidad de despacho.
 */
public class Pedido implements IAsignable {
    /** Identificador único del pedido */
    protected String idPedido;
    /** Dirección física donde se entregará el producto */
    protected String direccionEntrega;
    /** Categoría del pedido (Comida, Encomienda, Express) */
    protected String tipoPedido;

    /**
     * Constructor para la clase Pedido.
     * @param idPedido Código alfanumérico único.
     * @param direccionEntrega Punto de destino final.
     * @param tipoPedido Clasificación del servicio.
     */
    public Pedido(String idPedido, String direccionEntrega, String tipoPedido) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
    }

    /**
     * Implementación básica del método de asignación.
     */
    @Override
    public void asignarRepartidor() {
        System.out.println("Procesando asignación genérica para el pedido: " + idPedido);
    }

    /**
     * Implementación básica de asignación con nombre.
     * @param nombreRepartidor Nombre del trabajador.
     */
    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Pedido " + idPedido + " asignado a: " + nombreRepartidor);
    }
}