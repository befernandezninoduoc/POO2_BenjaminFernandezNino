package speedfast.controlador;

import speedfast.dao.implement.RepartidorDAOImpl;
import speedfast.dao.interfaces.RepartidorDAO;
import speedfast.excepciones.DatosInvalidosException;
import speedfast.modelo.Repartidor;
import java.util.List;

/**
 * Controlador para la gestión de repartidores en el sistema SpeedFast.
 * Centraliza la lógica de negocio y aplica validaciones de integridad antes
 * de enviar los datos en la base de datos, protege la calidad de la información.
 * @author Benjamín Fernández-Niño
 */
public class RepartidorControlador {

    /** Instancia estática del acceso a datos para Repartidores utilizando el patrón DAO. */
    private static final RepartidorDAO repartidorDAO = new RepartidorDAOImpl();

    /**
     * Registra un nuevo repartidor en el sistema previa validación de integridad.
     * * @param r Objeto Repartidor a registrar.
     * @throws DatosInvalidosException Si el nombre está vacío o contiene solo caracteres basura (., -, *).
     */
    public static void registrarRepartidor(Repartidor r) throws DatosInvalidosException {
        validarDatos(r);
        repartidorDAO.create(r);
    }

    /**
     * Recupera el listado completo de repartidores registrados en la base de datos.
     * * @return List de objetos Repartidor para su visualización en la interfaz.
     */
    public static List<Repartidor> listarRepartidores() {
        return repartidorDAO.readAll();
    }

    /**
     * Actualiza la información de un repartidor existente tras validar los nuevos datos.
     * @param r Objeto Repartidor con los datos actualizados.
     * @throws DatosInvalidosException Si los nuevos datos no cumplen con las reglas de negocio.
     */
    public static void modificarRepartidor(Repartidor r) throws DatosInvalidosException {
        validarDatos(r);
        repartidorDAO.update(r);
    }

    /**
     * Elimina un repartidor del sistema de forma permanente mediante su identificador único.
     * @param id Identificador único (ID) del repartidor que se va a eliminar.
     */
    public static void eliminarRepartidor(int id) {
        repartidorDAO.delete(id);
    }

    /**
     * Busca un repartidor específico por su identificador único.
     * @param id Identificador numérico del repartidor.
     * @return Objeto Repartidor si se encuentra, null en caso contrario.
     */
    public static Repartidor buscarRepartidorPorId(int id) {
        return repartidorDAO.buscarPorId(id);
    }

    /**
     * Método privado de soporte que centraliza la lógica de validación para Repartidores.
     * Protege el sistema contra entradas basura o campos vacíos.
     * @param r Repartidor a validar.
     * @throws DatosInvalidosException Si se detecta una cadena de texto inválida o basura.
     */
    private static void validarDatos(Repartidor r) throws DatosInvalidosException {
        String regexBasura = "^[.\\-*\\s]+$";

        if (r.getNombre() == null || r.getNombre().trim().isEmpty()) {
            throw new DatosInvalidosException("El nombre del repartidor es obligatorio.");
        }

        if (r.getNombre().matches(regexBasura)) {
            throw new DatosInvalidosException("El nombre ingresado no es válido (solo símbolos).");
        }
    }
}