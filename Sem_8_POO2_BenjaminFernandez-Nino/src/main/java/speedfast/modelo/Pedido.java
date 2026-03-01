package speedfast.modelo;

/**
 * Representa la entidad Pedido con sus atributos y estado logístico.
 * @author Benjamín Fernández-Niño
 */
public class Pedido {
    private int id;
    private String direccionEntrega;
    private String tipo;
    private String estado;

    /**
     * Constructor para inicializar un pedido desde la interfaz gráfica (IG).
     * Por defecto, el estado se establece en "PENDIENTE".
     * @param id Identificador numérico del pedido.
     * @param direccionEntrega Dirección de destino.
     * @param tipo Categoría o descripción del paquete.
     */
    public Pedido(int id, String direccionEntrega, String tipo) {
        this.id = id;
        this.direccionEntrega = direccionEntrega;
        this.tipo = tipo;
        this.estado = "PENDIENTE";
    }

    /**
     * Obtiene el identificador del pedido.
     * @return id numérico.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la dirección de entrega.
     * @return cadena string con la dirección.
     */
    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    /**
     * Obtiene tipo de pedido.
     * @return tipo de pedido, ej: comida.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Obtiene el estado actual del pedido.
     * @return estado (PENDIENTE, EN_REPARTO, ENTREGADO).
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Modifica la dirección de entrega.
     * @param direccionEntrega Nueva dirección del destino.
     */
    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    /**
     * Modifica el identificador del pedido.
     * @param id Nuevo identificador numérico.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Modifica el tipo de pedido.
     * @param tipo Descripción del paquete.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Modifica el estado logístico del pedido.
     * @param estado Nuevo estado del pedido.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", direccion='" + direccionEntrega + '\'' +
                ", tipo='" + tipo + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}