package speedfast.vista;

import speedfast.controlador.EntregaControlador;
import speedfast.modelo.Entrega;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Visualiza el historial de entregas registradas en la base de datos.
 * Permite la búsqueda, modificación y eliminación de registros de entrega.
 * @author Benjamín Fernández-Niño
 */
public class VentanaListaEntregas extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtIdBuscar;

    /**
     * Constructor que inicializa la interfaz de listado de entregas.
     */
    public VentanaListaEntregas() {
        setTitle("Historial de Entregas - SpeedFast");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel de búsqueda (Norte)
        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNorte.setBorder(BorderFactory.createTitledBorder("Buscador de Entregas"));

        txtIdBuscar = new JTextField(10);
        JButton btnBuscar = new JButton("Buscar por ID");

        /**
         * Lógica para buscar una entrega específica usando buscarEntregaPorId.
         */
        btnBuscar.addActionListener(e -> {
            String ingreso = txtIdBuscar.getText().trim();
            if (!ingreso.isEmpty()) {
                try {
                    int id = Integer.parseInt(ingreso);
                    Entrega ent = EntregaControlador.buscarEntregaPorId(id);

                    if (ent != null) {
                        modelo.setRowCount(0);
                        Object[] fila = {ent.getId(), ent.getIdPedido(), ent.getIdRepartidor(), ent.getFecha(), ent.getHora()};
                        modelo.addRow(fila);
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró la entrega con ID: " + id);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El ID debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                cargarDatos(); // Si está vacío, recarga todo
            }
        });

        panelNorte.add(new JLabel("ID Entrega:"));
        panelNorte.add(txtIdBuscar);
        panelNorte.add(btnBuscar);
        add(panelNorte, BorderLayout.NORTH);

        // Configuración de la tabla (Centro)
        String[] columnas = {"ID Entrega", "ID Pedido", "ID Repartidor", "Fecha", "Hora"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modelo);

        cargarDatos();
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Panel de botones (Sur)
        JPanel panelBotones = new JPanel();
        JButton btnActualizar = new JButton("Actualizar Tabla");
        JButton btnModificar = new JButton("Modificar Registro");
        JButton btnEliminar = new JButton("Eliminar Registro");
        JButton btnVolver = new JButton("Volver");

        /**
         * Lógica para modificar el historial de entrega.
         * Utiliza buscarEntregaPorId para validar la existencia previa.
         */
        btnModificar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                int idEntrega = (int) modelo.getValueAt(fila, 0);

                // Validación de existencia antes de modificar
                Entrega entregaActual = EntregaControlador.buscarEntregaPorId(idEntrega);

                if (entregaActual != null) {
                    try {
                        String nuevoIdPedido = JOptionPane.showInputDialog(this, "Nuevo ID de Pedido:", entregaActual.getIdPedido());
                        if (nuevoIdPedido == null) return;

                        String nuevoIdRepartidor = JOptionPane.showInputDialog(this, "Nuevo ID de Repartidor:", entregaActual.getIdRepartidor());
                        if (nuevoIdRepartidor == null) return;

                        long ahora = System.currentTimeMillis();
                        Entrega entregaEditada = new Entrega(
                                idEntrega,
                                Integer.parseInt(nuevoIdPedido),
                                Integer.parseInt(nuevoIdRepartidor),
                                new java.sql.Date(ahora),
                                new java.sql.Time(ahora)
                        );

                        EntregaControlador.modificarEntrega(entregaEditada);
                        cargarDatos();
                        JOptionPane.showMessageDialog(this, "Entrega actualizada correctamente.");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Los IDs deben ser números enteros.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La entrega ya no existe en la base de datos.");
                    cargarDatos();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una fila para modificar.");
            }
        });

        /**
         * Lógica para eliminar el registro de una entrega.
         */
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                int id = (int) modelo.getValueAt(fila, 0);

                // Validación de seguridad antes de eliminar
                if (EntregaControlador.buscarEntregaPorId(id) != null) {
                    int confirmar = JOptionPane.showConfirmDialog(this, "¿Eliminar entrega ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (confirmar == JOptionPane.YES_OPTION) {
                        EntregaControlador.eliminarEntrega(id);
                        cargarDatos();
                        JOptionPane.showMessageDialog(this, "Registro eliminado.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "El registro ya no existe.");
                    cargarDatos();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una fila.");
            }
        });

        btnActualizar.addActionListener(e -> cargarDatos());
        btnVolver.addActionListener(e -> dispose());

        panelBotones.add(btnActualizar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Método para cargar o refrescar los datos de la tabla desde la base de datos.
     */
    private void cargarDatos() {
        modelo.setRowCount(0);
        List<Entrega> lista = EntregaControlador.listarEntregas();
        if (lista != null) {
            for (Entrega ent : lista) {
                Object[] fila = {ent.getId(), ent.getIdPedido(), ent.getIdRepartidor(), ent.getFecha(), ent.getHora()};
                modelo.addRow(fila);
            }
        }
    }
}