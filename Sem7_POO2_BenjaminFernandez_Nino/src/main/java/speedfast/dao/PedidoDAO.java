package speedfast.dao;

import speedfast.conexion.ConexionBD;
import speedfast.modelo.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Gestiona las operaciones CRUD para la entidad Pedido en la base de datos.
 */
public class PedidoDAO {

    /**
     * Guarda un nuevo pedido en la base de datos.
     * @param pedido El objeto Pedido a registrar.
     */
    public void guardar(Pedido pedido) {
        String sql = "INSERT INTO pedido (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pedido.getDireccionEntrega());
            stmt.setString(2, pedido.getTipo());
            stmt.setString(3, "PENDIENTE"); // Por defecto

            stmt.executeUpdate();
            System.out.println("Pedido guardado en BD exitosamente.");

        } catch (SQLException e) {
            System.err.println("Error al guardar el pedido: " + e.getMessage());
        }
    }

    /**
     * Recupera todos los pedidos almacenados en la base de datos.
     * @return Lista de objetos Pedido.
     */
    public List<Pedido> listarTodos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido";

        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pedido p = new Pedido(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        rs.getString("tipo")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pedidos: " + e.getMessage());
        }
        return lista;
    }

}
