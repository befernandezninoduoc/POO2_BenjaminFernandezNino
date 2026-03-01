package speedfast.dao.implement;

import speedfast.conexion.ConexionBD;
import speedfast.dao.interfaces.EntregaDAO;
import speedfast.modelo.Entrega;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación JDBC para la gestión de la persistencia de Entregas.
 * Esta clase se encarga de realizar las operaciones CRUD directamente en la tabla 'entregas'.
 *  @author Benjamín Fernández-Niño
 */
public class EntregaDAOImpl implements EntregaDAO {

    private Connection conexion;
    private static final Logger LOGGER = Logger.getLogger(EntregaDAOImpl.class.getName());

    /**
     * Constructor que inicializa la conexión a la base de datos.
     */
    public EntregaDAOImpl() {
        try {
            this.conexion = ConexionBD.conectar();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al establecer conexión en EntregaDAO", e);
        }
    }

    /**
     * Registra una nueva entrega en la base de datos.
     * @param e Objeto Entrega que contiene los IDs de pedido, repartidor, fecha y hora.
     */
    @Override
    public void create(Entrega e) {
        String sql = "INSERT INTO entregas (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, e.getIdPedido());
            stmt.setInt(2, e.getIdRepartidor());
            stmt.setDate(3, e.getFecha());
            stmt.setTime(4, e.getHora());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al insertar registro de entrega", ex);
        }
    }

    /**
     * Obtiene todos los registros de la tabla entregas.
     * @return List de objetos Entrega mapeados desde la BD.
     */
    @Override
    public List<Entrega> readAll() {
        List<Entrega> lista = new ArrayList<>();
        String sql = "SELECT * FROM entregas";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Entrega(
                        rs.getInt("id"),
                        rs.getInt("id_pedido"),
                        rs.getInt("id_repartidor"),
                        rs.getDate("fecha"),
                        rs.getTime("hora")
                ));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al listar las entregas", ex);
        }
        return lista;
    }

    /**
     * Actualiza los datos de una entrega existente basada en su ID.
     * @param e Objeto Entrega con los datos actualizados.
     */
    @Override
    public void update(Entrega e) {
        String sql = "UPDATE entregas SET id_pedido = ?, id_repartidor = ?, fecha = ?, hora = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, e.getIdPedido());
            stmt.setInt(2, e.getIdRepartidor());
            stmt.setDate(3, e.getFecha());
            stmt.setTime(4, e.getHora());
            stmt.setInt(5, e.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al actualizar la entrega ID: " + e.getId(), ex);
        }
    }

    /**
     * Elimina un registro de entrega mediante su identificador único.
     * @param id Identificador de la entrega a eliminar.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM entregas WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar la entrega ID: " + id, ex);
        }
    }

    /**
     * Busca una entrega específica por su ID.
     * @param id ID de la entrega a buscar.
     * @return El objeto Entrega si se encuentra, null en caso contrario.
     */
    @Override
    public Entrega buscarPorId(int id) {
        String sql = "SELECT * FROM entregas WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Entrega(
                            rs.getInt("id"),
                            rs.getInt("id_pedido"),
                            rs.getInt("id_repartidor"),
                            rs.getDate("fecha"),
                            rs.getTime("hora")
                    );
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al buscar entrega por ID", ex);
        }
        return null;
    }
}