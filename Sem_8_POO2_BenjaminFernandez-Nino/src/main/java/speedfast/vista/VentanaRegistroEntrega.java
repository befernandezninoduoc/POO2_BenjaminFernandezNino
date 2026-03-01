package speedfast.vista;

import speedfast.controlador.EntregaControlador;
import speedfast.controlador.PedidoControlador;
import speedfast.controlador.RepartidorControlador;
import speedfast.excepciones.DatosInvalidosException;
import speedfast.modelo.Entrega;
import speedfast.modelo.Pedido;
import speedfast.modelo.Repartidor;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Ventana para registrar una entrega asociando Pedido y Repartidor.
 * @author Benjamín Fernández-Niño
 */
public class VentanaRegistroEntrega extends JFrame {

    private JComboBox<Pedido> cbPedidos;
    private JComboBox<Repartidor> cbRepartidores;
    private JTextField txtIdPedidoBusqueda;
    private JTextField txtIdRepartidorBusqueda;

    /**
     * Constructor que inicializa los componentes de registro de entrega.
     */
    public VentanaRegistroEntrega() {
        setTitle("Asignar Entrega - SpeedFast");
        setSize(550, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de Búsqueda Rápida (Norte)
        JPanel panelNorte = new JPanel(new GridLayout(2, 1, 5, 5));
        panelNorte.setBorder(BorderFactory.createTitledBorder("Búsqueda Rápida por ID"));

        // Buscador de Pedido
        JPanel busqPedido = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtIdPedidoBusqueda = new JTextField(5);
        JButton btnBuscarP = new JButton("Buscar Pedido");
        busqPedido.add(new JLabel("ID Pedido:"));
        busqPedido.add(txtIdPedidoBusqueda);
        busqPedido.add(btnBuscarP);

        // Buscador de Repartidor
        JPanel busqRepartidor = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtIdRepartidorBusqueda = new JTextField(5);
        JButton btnBuscarR = new JButton("Buscar Repartidor");
        busqRepartidor.add(new JLabel("ID Repartidor:"));
        busqRepartidor.add(txtIdRepartidorBusqueda);
        busqRepartidor.add(btnBuscarR);

        panelNorte.add(busqPedido);
        panelNorte.add(busqRepartidor);
        add(panelNorte, BorderLayout.NORTH);

        // Formulario de Selección (Centro)
        JPanel panelCentro = new JPanel(new GridLayout(2, 2, 10, 20));

        List<Pedido> pedidos = PedidoControlador.getListaPedidos();
        List<Repartidor> repartidores = RepartidorControlador.listarRepartidores();

        panelCentro.add(new JLabel("Pedido Seleccionado:"));
        cbPedidos = new JComboBox<>(pedidos.toArray(new Pedido[0]));
        panelCentro.add(cbPedidos);

        panelCentro.add(new JLabel("Repartidor Seleccionado:"));
        cbRepartidores = new JComboBox<>(repartidores.toArray(new Repartidor[0]));
        panelCentro.add(cbRepartidores);

        add(panelCentro, BorderLayout.CENTER);

        // Panel de Acciones (Sur)
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Registrar Entrega");
        JButton btnCancelar = new JButton("Cancelar");

        /**
         * Lógica para buscar y seleccionar pedido por ID.
         */
        btnBuscarP.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtIdPedidoBusqueda.getText());
                Pedido p = PedidoControlador.buscarPedidoPorId(id);
                if (p != null) {
                    cbPedidos.setSelectedItem(p);
                    JOptionPane.showMessageDialog(this, "Pedido localizado: " + p.getDireccionEntrega());
                } else {
                    JOptionPane.showMessageDialog(this, "Pedido no encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un ID válido.");
            }
        });

        /**
         * Lógica para buscar y seleccionar repartidor por ID.
         */
        btnBuscarR.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtIdRepartidorBusqueda.getText());
                Repartidor r = RepartidorControlador.buscarRepartidorPorId(id);
                if (r != null) {
                    cbRepartidores.setSelectedItem(r);
                    JOptionPane.showMessageDialog(this, "Repartidor localizado: " + r.getNombre());
                } else {
                    JOptionPane.showMessageDialog(this, "Repartidor no encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un ID válido.");
            }
        });

        /**
         * Lógica para registrar la entrega en la base de datos.
         */
        btnGuardar.addActionListener(e -> {
            try {
                Pedido p = (Pedido) cbPedidos.getSelectedItem();
                Repartidor r = (Repartidor) cbRepartidores.getSelectedItem();

                if (p == null || r == null) {
                    throw new DatosInvalidosException("Selección incompleta.");
                }

                Date fechaActual = new Date(System.currentTimeMillis());
                Time horaActual = new Time(System.currentTimeMillis());

                Entrega nuevaEntrega = new Entrega(0, p.getId(), r.getId(), fechaActual, horaActual);
                EntregaControlador.registrarEntrega(nuevaEntrega);

                JOptionPane.showMessageDialog(this, "Entrega registrada exitosamente.");
                dispose();
            } catch (DatosInvalidosException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());
        panelSur.add(btnCancelar);
        panelSur.add(btnGuardar);
        add(panelSur, BorderLayout.SOUTH);
    }
}