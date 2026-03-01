package speedfast.dao.interfaces;

import speedfast.modelo.Pedido;

import java.util.List;

/**
 * Interfaz que define el contrato de operaciones para la entidad Pedido en la base de datos.
 * @author Benjamín Fernández-Niño
 */
public interface PedidoDAO {
    /** Agrega un nuevo pedido a la base de datos */
    void create(Pedido pedido);

    /** Modifica los datos de un pedido existente */
    void update(Pedido pedido);

    /** Elimina un pedido utilizando su ID */
    void delete(int idPedido);

    /** Lista todos los pedidos registrados */
    List<Pedido> readAll();

    /** Busca pedidos filtrando por estado (PENDIENTE, ENTREGADO, etc.) */
    List<Pedido> buscarPorEstado(String estado);

    /** Busca un pedido específico por su ID */
    Pedido buscarPorId(int idPedido);
}