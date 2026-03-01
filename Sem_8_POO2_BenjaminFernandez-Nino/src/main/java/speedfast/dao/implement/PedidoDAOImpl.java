package speedfast.dao.implement;

import speedfast.conexion.ConexionBD;
import speedfast.dao.interfaces.PedidoDAO;
import speedfast.modelo.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación JDBC para la gestión de pedidos con soporte de estados.
 * Esta clase se encarga de las operaciones CRUD directamente en la base de datos MySQL.
 * @author Benjamín Fernández-Niño
 */
public class PedidoDAOImpl implements PedidoDAO {
    private Connection conexion;
    private static final Logger LOGGER = Logger.getLogger(PedidoDAOImpl.class.getName());

    /**
     * Constructor que inicializa la conexión a la base de datos.
     */
    public PedidoDAOImpl() {
        try {
            this.conexion = ConexionBD.conectar();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error de conexión en el constructor DAO", e);
        }
    }

    /**
     * Inserta un nuevo registro de pedido en la base de datos.
     * @param p Objeto Pedido que contiene la dirección, tipo y estado inicial.
     */
    @Override
    public void create(Pedido p) {
        String sql = "INSERT INTO pedidos (direccion, tipo, estado) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, p.getDireccionEntrega());
            stmt.setString(2, p.getTipo());
            // Si el pedido ya trae un estado se usa ese, sino por defecto PENDIENTE
            stmt.setString(3, (p.getEstado() != null) ? p.getEstado() : "PENDIENTE");
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al crear el registro del pedido", e);
        }
    }

    /**
     * Recupera todos los pedidos almacenados en la tabla pedidos.
     * @return List de objetos Pedido con sus datos y estados actualizados.
     */
    @Override
    public List<Pedido> readAll() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pedido p = new Pedido(rs.getInt("id"), rs.getString("direccion"), rs.getString("tipo"));
                p.setEstado(rs.getString("estado"));
                lista.add(p);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar todos los pedidos", e);
        }
        return lista;
    }

    /**
     * Actualiza la información y el estado de un pedido existente.
     * @param p Objeto Pedido con los nuevos datos y el ID correspondiente.
     */
    @Override
    public void update(Pedido p) {
        String sql = "UPDATE pedidos SET direccion=?, tipo=?, estado=? WHERE id=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, p.getDireccionEntrega());
            stmt.setString(2, p.getTipo());
            stmt.setString(3, p.getEstado());
            stmt.setInt(4, p.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el pedido ID: " + p.getId(), e);
        }
    }

    /**
     * Elimina físicamente un pedido de la base de datos mediante su ID.
     * @param id Identificador único del pedido a eliminar.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM pedidos WHERE id=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar el pedido con ID: " + id, e);
        }
    }

    /**
     * Busca un pedido específico utilizando su clave primaria.
     * @param id Identificador numérico del pedido.
     * @return Objeto Pedido si se encuentra, null en caso contrario.
     */
    @Override
    public Pedido buscarPorId(int id) {
        String sql = "SELECT * FROM pedidos WHERE id=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Pedido p = new Pedido(rs.getInt("id"), rs.getString("direccion"), rs.getString("tipo"));
                    p.setEstado(rs.getString("estado")); // Cargar estado
                    return p;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar el pedido por ID: " + id, e);
        }
        return null;
    }

    /**
     * Filtra y recupera una lista de pedidos según su estado logístico.
     * @param estado Etiqueta de estado a buscar (ej: 'PENDIENTE', 'ENTREGADO').
     * @return List de pedidos que coinciden con el estado proporcionado.
     */
    @Override
    public List<Pedido> buscarPorEstado(String estado) {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedidos WHERE estado = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, estado);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pedido p = new Pedido(rs.getInt("id"), rs.getString("direccion"), rs.getString("tipo"));
                    p.setEstado(rs.getString("estado"));
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al filtrar pedidos por estado: " + estado, e);
        }
        return lista;
    }
}