package speedfast.controlador;

import speedfast.dao.RepartidorDAO;
import speedfast.modelo.Repartidor;
import java.util.List;

/**
 * Controlador para la gestión de repartidores en el sistema SpeedFast.
 * Centraliza las peticiones de la vista hacia el objeto de acceso a datos (DAO).
 * * @author Benjamín Fernández-Niño
 */
public class RepartidorControlador {

    /** Instancia estática del DAO para interactuar con la base de datos */
    private static final RepartidorDAO repartidorDAO = new RepartidorDAO();

    /**
     * Obtiene la lista completa de repartidores desde la base de datos.
     * Este método ayuda a poblar tablas o listas desplegables en la interfaz.
     * * @return List de objetos Repartidor con sus datos de la BD.
     */
    public static List<Repartidor> obtenerTodos() {
        return repartidorDAO.listarTodos();
    }

    /**
     * Ejemplo de método para buscar un repartidor específico por su nombre.
     * @param nombre Nombre a buscar.
     * @return El objeto Repartidor si coincide, sino retorna null.
     */
    public static Repartidor buscarPorNombre(String nombre) {
        List<Repartidor> lista = repartidorDAO.listarTodos();
        for (Repartidor r : lista) {
            if (r.getNombre().equalsIgnoreCase(nombre)) {
                return r;
            }
        }
        return null;
    }
}
