package com.speedfast.vista;

import com.speedfast.controlador.PedidoControlador;
import com.speedfast.modelo.Pedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana encargada de visualizar todos los pedidos registrados.
 * Utiliza un JTable para mostrar la información de forma tabular.
 */
public class VentanaListaPedidos extends JFrame {

    public VentanaListaPedidos() {
        setTitle("Listado de Pedidos - SpeedFast");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Definición de columnas de la tabla
        String[] columnas = {"ID", "Dirección", "Tipo de Pedido"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tablaPedidos = new JTable(modeloTabla);

        // Obtener datos desde el controlador
        List<Pedido> lista = PedidoControlador.getListaPedidos();

        // Cargar los datos en el modelo de la tabla
        for (Pedido p : lista) {
            Object[] fila = {p.getId(), p.getDireccionEntrega(), p.getTipo()};
            modeloTabla.addRow(fila);
        }

        // Agregar scroll a la tabla
        JScrollPane scrollPane = new JScrollPane(tablaPedidos);
        add(scrollPane, BorderLayout.CENTER);

        // Botón para cerrar
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> dispose());
        add(btnVolver, BorderLayout.SOUTH);
    }
}