package speedfast.dao.interfaces;

import speedfast.modelo.Entrega;

import java.util.List;

/**
 * Interfaz para la persistencia de la relación entre pedidos y repartidores.
 * @author Benjamín Fernández-Niño
 */
public interface EntregaDAO {
    /**
     * Registra una nueva entrega asociando un pedido y un repartidor.
     * @param entrega Objeto entrega con IDs de pedido y repartidor, fecha y hora.
     */
    void create(Entrega entrega);

    /**
     * Obtiene el historial de todas las entregas realizadas.
     * @return Lista de entregas.
     */
    List<Entrega> readAll();

    /**
     * Actualiza los datos de una entrega.
     * @param entrega Objeto con datos nuevos.
     */
    void update(Entrega entrega);

    /**
     * Borra el registro de una entrega por su ID.
     * @param id ID de la entrega.
     */
    void delete(int id);

    /** busca la id de la entrega
     * @param id  ID de la entrega
     */

    Entrega buscarPorId(int id);
}