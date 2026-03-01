package speedfast.controlador;

import speedfast.dao.implement.EntregaDAOImpl;
import speedfast.dao.interfaces.EntregaDAO;
import speedfast.excepciones.DatosInvalidosException;
import speedfast.modelo.Entrega;
import speedfast.modelo.Pedido;

import java.util.List;

/**
 * Controlador para la gestión de entregas.
 * Coordina la asociación entre repartidores y pedidos.
 * @author Benjamín Fernández-Niño
 */
public class EntregaControlador {

    private static final EntregaDAO entregaDAO = new EntregaDAOImpl();

    /**
     * Registra una entrega y actualiza automáticamente el estado del pedido a ENTREGADO.
     * @param e Objeto entrega a registrar.
     */
    public static void registrarEntrega(Entrega e) throws DatosInvalidosException {
        // 1. Registrar la entrega en su tabla correspondiente
        entregaDAO.create(e);

        // 2. Buscar el pedido asociado por su ID para cambiar su estado
        Pedido pedidoAsociado = PedidoControlador.buscarPedidoPorId(e.getIdPedido());

        if (pedidoAsociado != null) {
            // Cambiar el atributo estado en el objeto
            pedidoAsociado.setEstado("ENTREGADO");

            // 3. Persistir el cambio en la base de datos usando el controlador de pedidos
            PedidoControlador.modificarPedido(pedidoAsociado);
        }
    }

    /**
     * Obtiene todas las entregas para mostrarlas en el JTable.
     * @return Lista de objetos Entrega.
     */
    public static List<Entrega> listarEntregas() {
        return entregaDAO.readAll();
    }

    /**
     * Actualiza los datos de una entrega existente.
     * @param e Objeto Entrega con datos actualizados.
     */
    public static void modificarEntrega(Entrega e) {
        entregaDAO.update(e);
    }

    /**
     * Elimina el registro de una entrega.
     * @param id ID de la entrega a eliminar.
     */
    public static void eliminarEntrega(int id) {
        entregaDAO.delete(id);
    }

    /**
     * Busca una entrega específica por su identificador único.
     * @param id ID de la entrega a localizar.
     * @return Objeto Entrega encontrado o null.
     */
    public static Entrega buscarEntregaPorId(int id) {
        return entregaDAO.buscarPorId(id);
    }
}