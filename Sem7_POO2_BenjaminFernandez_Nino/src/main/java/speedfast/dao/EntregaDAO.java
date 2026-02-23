package speedfast.dao;

import speedfast.conexion.ConexionBD;
import speedfast.modelo.Entrega;
import java.sql.*;

// DAO son las siglas de Data Access Object (Objeto de Acceso a Datos).
// Es un patrón de diseño de software, sirve para separar la lógica de negocio de la lógica de acceso a datos.
/**
 * Clase Data Access Object para la entidad Entrega.
 * Gestiona la inserción de entregas y la actualización de estados de pedidos.
 */
public class EntregaDAO {

    /**
     * Registra una nueva entrega y actualiza el estado del pedido asociado.
     * Utiliza una transacción implícita al ejecutar el insert.
     * @param entrega Objeto Entrega con datos validados.
     */
    public void guardar(Entrega entrega) {
        String sql = "INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entrega.getIdPedido());
            stmt.setInt(2, entrega.getIdRepartidor());
            stmt.setDate(3, entrega.getFecha());
            stmt.setTime(4, entrega.getHora());

            int filasAffectadas = stmt.executeUpdate();

            if (filasAffectadas > 0) {
                System.out.println("Entrega registrada correctamente.");
                actualizarEstadoPedido(entrega.getIdPedido(), "EN_REPARTO");
            }

        } catch (SQLException e) {
            System.err.println("Error en EntregaDAO.guardar: " + e.getMessage());
        }
    }

    /**
     * Método privado para cambiar el estado de un pedido tras ser asignado.
     * @param idPedido ID del pedido a modificar.
     * @param nuevoEstado Estado (PENDIENTE, EN_REPARTO, ENTREGADO).
     * @throws SQLException Si falla la conexión.
     */
    private void actualizarEstadoPedido(int idPedido, String nuevoEstado) throws SQLException {
        String sql = "UPDATE pedido SET estado = ? WHERE id = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idPedido);
            stmt.executeUpdate();
        }
    }
}