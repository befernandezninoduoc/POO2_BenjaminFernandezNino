package speedfast.dao;

import speedfast.conexion.ConexionBD;
import speedfast.modelo.Repartidor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Realiza operaciones de lectura sobre la tabla repartidor.
 */
public class RepartidorDAO {

    /**
     * Obtiene la lista de todos los repartidores registrados.
     * @return List de objetos Repartidor.
     */
    public List<Repartidor> listarTodos() {
        List<Repartidor> lista = new ArrayList<>();
        String sql = "SELECT * FROM repartidor";

        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Repartidor(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar repartidores: " + e.getMessage());
        }
        return lista;
    }
}