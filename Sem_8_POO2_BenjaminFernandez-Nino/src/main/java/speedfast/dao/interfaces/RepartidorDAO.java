package speedfast.dao.interfaces;

import speedfast.modelo.Repartidor;

import java.util.List;

/**
 * Interfaz que define las operaciones CRUD para la entidad Repartidor.
 * @author Benjamín Fernández-Niño
 */
public interface RepartidorDAO {
    /**
     * Inserta un nuevo repartidor en la base de datos.
     * @param repartidor Objeto con los datos del repartidor.
     */
    void create(Repartidor repartidor);

    /**
     * Recupera todos los repartidores registrados.
     * @return Lista de repartidores.
     */
    List<Repartidor> readAll();

    /**
     * Actualiza la información de un repartidor existente.
     * @param repartidor Objeto con los datos actualizados.
     */
    void update(Repartidor repartidor);

    /**
     * Elimina un repartidor de la base de datos por su ID.
     * @param id Identificador único del repartidor.
     */
    void delete(int id);

    /**
     * Busca un repartidor por su identificador único.
     * @param id Identificador del repartidor.
     */
    Repartidor buscarPorId(int id);
}