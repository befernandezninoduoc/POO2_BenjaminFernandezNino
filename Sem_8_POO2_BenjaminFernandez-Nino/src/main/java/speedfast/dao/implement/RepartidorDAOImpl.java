package speedfast.dao.implement;

import speedfast.conexion.ConexionBD;
import speedfast.dao.interfaces.RepartidorDAO;
import speedfast.modelo.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación JDBC para la gestión de datos de repartidores.
 * Esta clase interactúa directamente con la tabla repartidores en MySQL.
 * @author Benjamín Fernández-Niño
 */
public class RepartidorDAOImpl implements RepartidorDAO {
    private Connection conexion;
    private static final Logger LOGGER = Logger.getLogger(RepartidorDAOImpl.class.getName());

    /**
     * Constructor que inicializa la conexión persistente con la base de datos.
     */
    public RepartidorDAOImpl() {
        try {
            this.conexion = ConexionBD.conectar();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error de conexión en RepartidorDAO", e);
        }
    }

    /**
     * Inserta un nuevo repartidor en la base de datos.
     * @param r Objeto Repartidor que contiene el nombre del personal a registrar.
     */
    @Override
    public void create(Repartidor r) {
        String sql = "INSERT INTO repartidores (nombre) VALUES (?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, r.getNombre());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar repartidor", e);
        }
    }

    /**
     * Recupera todos los repartidores activos en el sistema.
     * @return List de objetos Repartidor con sus identificadores y nombres.
     */
    @Override
    public List<Repartidor> readAll() {
        List<Repartidor> lista = new ArrayList<>();
        String sql = "SELECT * FROM repartidores";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Repartidor(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar repartidores", e);
        }
        return lista;
    }

    /**
     * Actualiza el nombre de un repartidor existente basándose en su ID.
     * @param r Objeto Repartidor con el ID de referencia y el nuevo nombre.
     */
    @Override
    public void update(Repartidor r) {
        String sql = "UPDATE repartidores SET nombre = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, r.getNombre());
            stmt.setInt(2, r.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar repartidor", e);
        }
    }

    /**
     * Elimina de forma permanente un repartidor de la base de datos.
     * @param id Identificador único del repartidor a eliminar.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM repartidores WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar repartidor", e);
        }
    }

    /**
     * Busca un repartidor en la base de datos por su ID.
     * @param id Identificador único del repartidor.
     * @return Objeto Repartidor encontrado o null si no existe coincidencia.
     */
    @Override
    public Repartidor buscarPorId(int id) {
        String sql = "SELECT * FROM repartidores WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Repartidor(rs.getInt("id"), rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar repartidor por ID: " + id, e);
        }
        return null;
    }
}