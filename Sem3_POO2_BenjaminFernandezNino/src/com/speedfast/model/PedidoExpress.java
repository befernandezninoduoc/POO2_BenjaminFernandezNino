package com.speedfast.model;

/**
 * Pedido de entrega prioritaria con reglas de distancia.
 */
public class PedidoExpress extends Pedido {
    public PedidoExpress(String idPedido, String direccionEntrega, double distanciaKm){
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public void asignarRepartidor() {
        // Este método puede ser sobrescrito manualmente en el Main,
        // pero aquí definimos la lógica por defecto.
        setNombreRepartidor("Ciclista de Mensajería Urgente");
        registrarEvento("Asignación: Se ha vinculado un Repartidor express en bicicleta.");
    }

    /**
     * Calcular tiempo de entrega
     * @return tiempo base 10 min, y 5 min extra por si la distancia es > a 5 km
     */
    @Override
    public int calcularTiempoEntrega() {
        int tiempo = 10;
        if (getDistanciaKm() > 5){
            tiempo += 5;
        }
        return tiempo;
    }
}
