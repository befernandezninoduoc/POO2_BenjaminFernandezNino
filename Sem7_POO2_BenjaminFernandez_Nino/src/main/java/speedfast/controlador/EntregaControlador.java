package speedfast.controlador;

import speedfast.dao.EntregaDAO;
import speedfast.modelo.Entrega;

/**
 * Controlador para la gestión de entregas en SpeedFast.
 * Coordina la asignación de repartidores a pedidos específicos.
 * * @author Benjamín Fernández-Niño
 */
public class EntregaControlador {

    private static final EntregaDAO entregaDAO = new EntregaDAO();

    /**
     * Registra una nueva entrega en el sistema.
     * @param entrega Objeto con los datos de la relación pedido-repartidor.
     */
    public static void registrarEntrega(Entrega entrega) {
        entregaDAO.guardar(entrega);
    }
}