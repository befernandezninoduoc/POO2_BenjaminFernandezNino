package speedfast.vista;

import speedfast.controlador.RepartidorControlador;
import speedfast.excepciones.DatosInvalidosException;
import speedfast.modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana que muestra el listado de repartidores registrados.
 * Permite gestionar el ciclo de vida de los repartidores (Listar, Buscar, Editar, Eliminar).
 * @author Benjamín Fernández-Niño
 */
public class VentanaListaRepartidor extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtIdBuscar;

    /**
     * Constructor que inicializa la interfaz de listado de repartidores.
     */
    public VentanaListaRepartidor() {
        setTitle("Listado de Repartidores - SpeedFast");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel de Búsqueda (Norte)
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscador"));

        txtIdBuscar = new JTextField(10);
        JButton btnBuscar = new JButton("Buscar por ID");

        /**
         * Lógica para buscar un repartidor específico usando buscarRepartidorPorId.
         */
        btnBuscar.addActionListener(e -> {
            String ingreso = txtIdBuscar.getText().trim();
            if (!ingreso.isEmpty()) {
                try {
                    int id = Integer.parseInt(ingreso);
                    // Uso del metodo buscarRepartidorPorId
                    Repartidor r = RepartidorControlador.buscarRepartidorPorId(id);

                    if (r != null) {
                        modelo.setRowCount(0);
                        Object[] fila = {r.getId(), r.getNombre()};
                        modelo.addRow(fila);
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró el repartidor con ID: " + id);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                cargarDatos(); // Recargar lista completa si el campo está vacío
            }
        });

        panelBusqueda.add(new JLabel("ID Repartidor:"));
        panelBusqueda.add(txtIdBuscar);
        panelBusqueda.add(btnBuscar);
        add(panelBusqueda, BorderLayout.NORTH);

        // Configuración de la tabla (Centro)
        String[] columnas = {"ID", "Nombre del Repartidor"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modelo);

        cargarDatos();
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Panel de acciones (Sur)
        JPanel panelAcciones = new JPanel();
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEditar = new JButton("Editar Nombre");
        JButton btnEliminar = new JButton("Eliminar Seleccionado");
        JButton btnCerrar = new JButton("Cerrar");

        /**
         * Lógica para editar el nombre de un repartidor seleccionado.
         */
        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                int id = (int) modelo.getValueAt(fila, 0);

                // Consultar datos frescos de la BD antes de editar
                Repartidor actual = RepartidorControlador.buscarRepartidorPorId(id);

                if (actual != null) {
                    String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", actual.getNombre());
                    if (nuevoNombre == null) return;

                    try {
                        actual.setNombre(nuevoNombre);
                        // Uso del metodo modificarRepartidor
                        RepartidorControlador.modificarRepartidor(actual);

                        cargarDatos();
                        JOptionPane.showMessageDialog(this, "Repartidor actualizado correctamente.");
                    } catch (DatosInvalidosException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un repartidor para editar.");
            }
        });

        /**
         * Lógica para eliminar el registro, validando su existencia primero.
         */
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                int id = (int) modelo.getValueAt(fila, 0);

                // Verificamos que aún exista en la BD
                if (RepartidorControlador.buscarRepartidorPorId(id) != null) {
                    int confirma = JOptionPane.showConfirmDialog(this, "¿Eliminar repartidor ID: " + id + "?",
                            "Confirmar", JOptionPane.YES_NO_OPTION);

                    if (confirma == JOptionPane.YES_OPTION) {
                        RepartidorControlador.eliminarRepartidor(id);
                        cargarDatos();
                        JOptionPane.showMessageDialog(this, "Repartidor eliminado.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "El registro ya no existe.");
                    cargarDatos();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un repartidor de la tabla.");
            }
        });

        btnActualizar.addActionListener(e -> cargarDatos());
        btnCerrar.addActionListener(e -> dispose());

        panelAcciones.add(btnActualizar);
        panelAcciones.add(btnEditar);
        panelAcciones.add(btnEliminar);
        panelAcciones.add(btnCerrar);
        add(panelAcciones, BorderLayout.SOUTH);
    }

    /**
     * Método privado para cargar los datos desde la base de datos a la tabla.
     */
    private void cargarDatos() {
        modelo.setRowCount(0);
        List<Repartidor> lista = RepartidorControlador.listarRepartidores();
        if (lista != null) {
            for (Repartidor r : lista) {
                Object[] fila = {r.getId(), r.getNombre()};
                modelo.addRow(fila);
            }
        }
    }
}