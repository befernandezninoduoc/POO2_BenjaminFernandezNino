package speedfast.modelo;

import java.sql.Date;
import java.sql.Time;

/**
 * Representa el registro de una entrega realizada por un repartidor para un pedido específico.
 */
public class Entrega {
    private int id;
    private int idPedido;
    private int idRepartidor;
    private Date fecha;
    private Time hora;

    /**
     * Constructor completo para registrar una entrega.
     * @param id Identificador de la entrega.
     * @param idPedido Referencia al pedido.
     * @param idRepartidor Referencia al repartidor.
     * @param fecha Objeto Date de SQL.
     * @param hora Objeto Time de SQL.
     */
    public Entrega(int id, int idPedido, int idRepartidor, Date fecha, Time hora) {
        this.id = id;
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }
    //Getters y setters

    /** @return ID de la entrega */
    public int getId() { return id; }
    /** @param id Nuevo ID de entrega */
    public void setId(int id) { this.id = id; }

    /** @return ID del pedido asociado */
    public int getIdPedido() { return idPedido; }
    /** @param idPedido ID del pedido a vincular */
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    /** @return ID del repartidor asociado */
    public int getIdRepartidor() { return idRepartidor; }
    /** @param idRepartidor ID del repartidor a vincular */
    public void setIdRepartidor(int idRepartidor) { this.idRepartidor = idRepartidor; }

    /** @return Fecha de la entrega */
    public Date getFecha() { return fecha; }
    /** @param fecha Nueva fecha de entrega */
    public void setFecha(Date fecha) { this.fecha = fecha; }

    /** @return Hora de la entrega */
    public Time getHora() { return hora; }
    /** @param hora Nueva hora de entrega */
    public void setHora(Time hora) { this.hora = hora; }
}