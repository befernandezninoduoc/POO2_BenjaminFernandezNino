package speedfast.controlador;

import speedfast.dao.implement.PedidoDAOImpl;
import speedfast.dao.interfaces.PedidoDAO;
import speedfast.excepciones.DatosInvalidosException;
import speedfast.modelo.Pedido;
import java.util.List;

/**
 * Controlador para la gestión de pedidos en el sistema SpeedFast.
 * Actúa como capa intermedia coordinando las validaciones de dirección y tipo
 * de paquete antes de realizar operaciones CRUD, asegurando la calidad de los datos.
 * @author Benjamín Fernández-Niño
 */
public class PedidoControlador {

    /** Instancia estática del acceso a datos para Pedidos utilizando el patrón DAO. */
    private static final PedidoDAO pedidoDAO = new PedidoDAOImpl();

    /**
     * Registra un nuevo pedido en el sistema previa validación de integridad.
     * @param p Objeto Pedido a registrar.
     * @throws DatosInvalidosException Si la dirección o el tipo de paquete contienen
     * solo caracteres basura (., -, *).
     */
    public static void registrarPedido(Pedido p) throws DatosInvalidosException {
        validarDatos(p);
        pedidoDAO.create(p);
    }

    /**
     * Recupera el listado completo de pedidos registrados en la base de datos.
     * @return List de objetos Pedido para su visualización en la interfaz.
     */
    public static List<Pedido> getListaPedidos() {
        return pedidoDAO.readAll();
    }

    /**
     * Actualiza la información de un pedido existente tras validar los nuevos datos.
     * @param p Objeto Pedido con los cambios a aplicar.
     * @throws DatosInvalidosException Si los datos editados no cumplen con las reglas de negocio.
     */
    public static void modificarPedido(Pedido p) throws DatosInvalidosException {
        validarDatos(p);
        pedidoDAO.update(p);
    }

    /**
     * Elimina un pedido del sistema de forma permanente mediante su identificador único.
     * @param id Identificador único (ID) del pedido a eliminar.
     */
    public static void borrarPedido(int id) {
        pedidoDAO.delete(id);
    }

    /**
     * Buscar por estado en el sistema
     * @param estado Estado del pedido, "PENDIENTE", "EN PROCESO", "ENTREGADO".
     */
    public static List<Pedido> buscarPorEstado(String estado) {
        return pedidoDAO.buscarPorEstado(estado);
    }

    /**
     * Busca un pedido específico por su identificador único.
     * @param id Identificador numérico del pedido.
     * @return Objeto Pedido si se encuentra, null en caso contrario.
     */
    public static Pedido buscarPedidoPorId(int id) {
        return pedidoDAO.buscarPorId(id);
    }

    /**
     * Método privado de soporte que centraliza la lógica de validación para Pedidos.
     * Protege el sistema contra entradas de solo símbolos o campos vacíos.
     * @param p Pedido a validar.
     * @throws DatosInvalidosException Si se detecta una cadena de texto inválida.
     */
    private static void validarDatos(Pedido p) throws DatosInvalidosException {
        String regexBasura = "^[.\\-*\\s]+$";

        if (p.getDireccionEntrega() == null || p.getDireccionEntrega().trim().isEmpty()) {
            throw new DatosInvalidosException("La dirección de entrega es obligatoria.");
        }

        if (p.getDireccionEntrega().matches(regexBasura)) {
            throw new DatosInvalidosException("La dirección ingresada no es válida (solo símbolos).");
        }

        if (p.getTipo() != null && p.getTipo().matches(regexBasura)) {
            throw new DatosInvalidosException("El tipo de paquete no es válido (solo símbolos).");
        }
    }
}