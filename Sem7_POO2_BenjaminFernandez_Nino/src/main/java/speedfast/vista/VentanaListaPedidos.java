package speedfast.vista;

import speedfast.controlador.PedidoControlador;
import speedfast.modelo.Pedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana que visualiza los pedidos consultando la base de datos en tiempo real.
 */
public class VentanaListaPedidos extends JFrame {

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    /**
     * Constructor que inicializa la interfaz de listado.
     */
    public VentanaListaPedidos() {
        setTitle("Listado de Pedidos Persistentes - SpeedFast");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Configuración de la tabla
        String[] columnas = {"ID", "Dirección", "Tipo de Pedido"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPedidos = new JTable(modeloTabla);

        // Cargar los datos iniciales
        cargarDatos();

        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        // Panel de botones inferior
        JPanel panelInferior = new JPanel();
        JButton btnActualizar = new JButton("Actualizar Datos");
        JButton btnVolver = new JButton("Volver");

        /**
         * Refrescar la tabla manualmente.
         */
        btnActualizar.addActionListener(e -> cargarDatos());

        /**
         * Cerrar la ventana.
         */
        btnVolver.addActionListener(e -> dispose());

        panelInferior.add(btnActualizar);
        panelInferior.add(btnVolver);
        add(panelInferior, BorderLayout.SOUTH);
    }

    /**
     * Método que consulta al controlador y refresca el contenido de la JTable.
     * Se asegura de limpiar la tabla antes de insertar los nuevos datos.
     */
    public void cargarDatos() {
        // 1. Limpiar el modelo actual
        modeloTabla.setRowCount(0);

        // 2. Obtener lista actualizada desde la BD vía Controlador
        List<Pedido> lista = PedidoControlador.getListaPedidos();

        // 3. Poblar el modelo
        for (Pedido p : lista) {
            Object[] fila = {p.getId(), p.getDireccionEntrega(), p.getTipo()};
            modeloTabla.addRow(fila);
        }
    }
}