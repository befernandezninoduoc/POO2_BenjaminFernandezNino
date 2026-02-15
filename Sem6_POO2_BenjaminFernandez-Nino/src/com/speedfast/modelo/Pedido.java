package com.speedfast.modelo;

/**
 * Representa la entidad Pedido con sus atributos.
 * @author benjamín Fernández-Niño
 */
public class Pedido {
    private int id;
    private String direccionEntrega;
    private String tipo;

    /**
     * Constructor para inicializar un pedido desde la interfaz gráfica (IG)
     * @param id
     * @param direccionEntrega
     * @param tipo
     */
    public Pedido(int id, String direccionEntrega, String tipo) {
        this.id = id;
        this.direccionEntrega = direccionEntrega;
        this.tipo = tipo;
    }

    /**
     * Obtine el identificador del pedido
     * @return id numérico.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la direccion de entrega.
     * @return cadena string con la direccion
     */
    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    /**
     * Optiene tipo de pedido.
     * @return tipo de pedido, ej: comida
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Modifica la direccion de entrega.
     * @param direccionEntrega Nueva dirección del destino.
     */
    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    /**
     * Modifica el identificador del pedido.
     * @param id Nuevo identificador numérico
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Modifica el tipo de pedido
     * @param tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "[id=" + id +
                ", direccionEntrega='" + direccionEntrega +
                ", tipo='" + tipo + ']' +
                '}';
    }
}
