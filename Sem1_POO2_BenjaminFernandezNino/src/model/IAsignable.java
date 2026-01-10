package model;

/**
 * Interfaz que define el contrato para la asignación de repartidores.
 * Establece los métodos necesarios para gestionar la logística de entrega.
 * * @author Benjamin Fernández-Niño
 * @version 1.0
 */
public interface IAsignable {
    /**
     * Realiza la búsqueda y asignación automática de un repartidor
     * según los criterios específicos del tipo de pedido.
     */
    void asignarRepartidor();

    /**
     * Asigna manualmente un repartidor específico al pedido.
     * * @param nombreRepartidor Nombre del personal de entregas asignado.
     */
    void asignarRepartidor(String nombreRepartidor);
}