package speedfast.vista;

import speedfast.controlador.PedidoControlador;
import speedfast.excepciones.DatosInvalidosException;
import speedfast.modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana que visualiza los pedidos consultando la base de datos en tiempo real.
 * Permite gestionar el ciclo de vida de los pedidos (Listar, Editar, Eliminar).
 * @author Benjamín Fernández-Niño
 */
public class VentanaListaPedidos extends JFrame {

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> cbFiltroEstado;
    private JButton btnBuscarId; // Botón para usar buscarPorId

    /**
     * Constructor que inicializa la interfaz de listado.
     */
    public VentanaListaPedidos() {
        setTitle("Listado de Pedidos Persistentes - SpeedFast");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel de filtro (Norte)
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.add(new JLabel("Filtrar por Estado: "));

        // Opciones del buscador
        String[] estadosFiltro = {"TODOS", "PENDIENTE", "EN_REPARTO", "ENTREGADO"};
        cbFiltroEstado = new JComboBox<>(estadosFiltro);

        cbFiltroEstado.addActionListener(e -> {
            String seleccionado = (String) cbFiltroEstado.getSelectedItem();
            cargarDatos(seleccionado);
        });

        panelFiltro.add(cbFiltroEstado);

        // Lógica para buscar un pedido específico por ID
        btnBuscarId = new JButton("Buscar por ID");
        btnBuscarId.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Ingrese el ID del pedido:");
            if (input != null && !input.isEmpty()) {
                try {
                    int id = Integer.parseInt(input);
                    // Uso del método buscarPorId a través del controlador
                    Pedido p = PedidoControlador.buscarPedidoPorId(id);

                    if (p != null) {
                        modeloTabla.setRowCount(0);
                        Object[] fila = {p.getId(), p.getDireccionEntrega(), p.getTipo(), p.getEstado()};
                        modeloTabla.addRow(fila);
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró ningún pedido con el ID: " + id);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panelFiltro.add(btnBuscarId);

        add(panelFiltro, BorderLayout.NORTH);

        // Configuración de la tabla (Centro)
        String[] columnas = {"ID", "Dirección", "Tipo de Pedido", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evitar que el usuario edite las celdas directamente
            }
        };
        tablaPedidos = new JTable(modeloTabla);

        // Cargar los datos iniciales
        cargarDatos();

        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        // Panel de botones inferior (Sur)
        JPanel panelInferior = new JPanel();
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar Seleccionado");
        JButton btnVolver = new JButton("Volver");

        /**
         * Lógica para eliminar un pedido seleccionado en la tabla.
         */
        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaPedidos.getSelectedRow();
            if (filaSeleccionada != -1) {
                int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                int confirmar = JOptionPane.showConfirmDialog(this,
                        "¿Está seguro de eliminar el pedido ID: " + id + "?",
                        "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

                if (confirmar == JOptionPane.YES_OPTION) {
                    PedidoControlador.borrarPedido(id);
                    cargarDatos(); // Refrescar después de eliminar
                    JOptionPane.showMessageDialog(this, "Pedido eliminado con éxito.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione una fila.");
            }
        });

        /**
         * Lógica para editar un pedido seleccionado.
         */
        btnEditar.addActionListener(e -> {
            int filaSeleccionada = tablaPedidos.getSelectedRow();

            if (filaSeleccionada != -1) {
                int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

                // Uso de buscarPorId para obtener los datos más recientes antes de editar
                Pedido pedidoActual = PedidoControlador.buscarPedidoPorId(id);

                if (pedidoActual != null) {
                    // Entradas de texto con datos recuperados
                    String nuevaDireccion = JOptionPane.showInputDialog(this, "Nueva Dirección:", pedidoActual.getDireccionEntrega());
                    if (nuevaDireccion == null) return;

                    String nuevoTipo = JOptionPane.showInputDialog(this, "Nuevo Tipo de Pedido:", pedidoActual.getTipo());
                    if (nuevoTipo == null) return;

                    // Selector de Estado (PENDIENTE, EN_REPARTO, ENTREGADO)
                    String[] opcionesEstado = {"PENDIENTE", "EN_REPARTO", "ENTREGADO"};
                    String nuevoEstado = (String) JOptionPane.showInputDialog(this,
                            "Cambiar Estado:", "Actualizar",
                            JOptionPane.QUESTION_MESSAGE, null, opcionesEstado, pedidoActual.getEstado());

                    if (nuevoEstado == null) return;

                    try {
                        Pedido pedidoEditado = new Pedido(id, nuevaDireccion, nuevoTipo);
                        pedidoEditado.setEstado(nuevoEstado);

                        PedidoControlador.modificarPedido(pedidoEditado);

                        cargarDatos((String) cbFiltroEstado.getSelectedItem());
                        JOptionPane.showMessageDialog(this, "Pedido actualizado con éxito.");

                    } catch (DatosInvalidosException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.WARNING_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un pedido.");
            }
        });

        /**
         * Refrescar la tabla manualmente.
         */
        btnActualizar.addActionListener(e -> cargarDatos((String) cbFiltroEstado.getSelectedItem()));

        /**
         * Cerrar la ventana.
         */
        btnVolver.addActionListener(e -> dispose());

        panelInferior.add(btnActualizar);
        panelInferior.add(btnEditar);
        panelInferior.add(btnEliminar);
        panelInferior.add(btnVolver);
        add(panelInferior, BorderLayout.SOUTH);
    }

    /**
     * Método mejorado para cargar datos usando el filtro de estado.
     * @param filtro Estado por el cual filtrar la lista.
     */
    public void cargarDatos(String filtro) {
        modeloTabla.setRowCount(0);
        List<Pedido> lista;

        // Si el filtro es TODOS o nulo, traemos todo. Si no, se usa el buscador por estado.
        if (filtro == null || filtro.equals("TODOS")) {
            lista = PedidoControlador.getListaPedidos();
        } else {
            lista = PedidoControlador.buscarPorEstado(filtro);
        }

        if (lista != null) {
            for (Pedido p : lista) {
                Object[] fila = {p.getId(), p.getDireccionEntrega(), p.getTipo(), p.getEstado()};
                modeloTabla.addRow(fila);
            }
        }
    }

    /**
     * Sobrecarga para mantener compatibilidad con llamadas sin argumentos.
     */
    public void cargarDatos() {
        cargarDatos("TODOS");
    }
}