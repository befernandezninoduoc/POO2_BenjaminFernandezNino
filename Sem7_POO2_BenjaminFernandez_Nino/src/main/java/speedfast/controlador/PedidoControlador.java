package speedfast.controlador;

import speedfast.dao.PedidoDAO;
import speedfast.modelo.Pedido;
import java.util.List;

/**
 * Controlador que hace de puente entre las Vistas y la Base de Datos (DAO).
 */
public class PedidoControlador {
    private static PedidoDAO pedidoDAO = new PedidoDAO();

    public static void agregarPedido(Pedido p) {
        // Envia el objeto directamente a MySQL
        pedidoDAO.guardar(p);
    }

    public static List<Pedido> getListaPedidos() {
        // Trae los datos frescos desde MySQL
        return pedidoDAO.listarTodos();
    }
}