package speedfast.modelo;

/**
 * Representa a un repartidor dentro del sistema SpeedFast.
 * @author Benjamín Fernández-Niño
 */
public class Repartidor {
    private int id;
    private String nombre;

    /**
     * Constructor para crear un repartidor.
     * @param id Identificador único.
     * @param nombre Nombre completo del repartidor.
     */
    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Sobrescribe el método toString para facilitar la visualización en componentes Swing.
     * @return El nombre del repartidor.
     */
    @Override
    public String toString() {
        return nombre; // Útil para mostrar en JComboBox
    }
}