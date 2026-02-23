package speedfast.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión JDBC con la base de datos MySQL.
 * Uso del patrón Singleton para la cadena de conexión.
 * @author Benjamín Fernández-Niño
 */
public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/speedfast?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "MySQL093467";

    /**
     * Establece la conexión con la base de datos.
     * @return Objeto Connection activo.
     * @throws SQLException Si ocurre un error de acceso a la base de datos.
     */
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}