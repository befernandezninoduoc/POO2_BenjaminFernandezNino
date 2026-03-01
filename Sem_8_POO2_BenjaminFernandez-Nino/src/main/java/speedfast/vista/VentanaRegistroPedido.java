package speedfast.vista;

import speedfast.controlador.PedidoControlador;
import speedfast.excepciones.DatosInvalidosException;
import speedfast.modelo.Pedido;

import javax.swing.*;
import java.awt.*;

/**
 * Formulario para el registro de pedidos integrados con Base de Datos.
 * Utiliza el controlador para persistir la información en MySQL.
 * @author Benjamín Fernández-Niño
 */
public class VentanaRegistroPedido extends JFrame {

    /**
     * Constructor que inicializa los componentes del formulario de registro.
     */
    public VentanaRegistroPedido() {
        setTitle("Registrar Pedido - SpeedFast DB");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Panel de Cabecera y Buscador (Norte)
        JPanel panelNorte = new JPanel(new GridLayout(2, 1, 5, 5));
        JLabel lblTitulo = new JLabel("Gestión de Registro de Pedidos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField txtIdBuscar = new JTextField(5);
        JButton btnBuscar = new JButton("Buscar por ID");
        panelBusqueda.add(new JLabel("ID a consultar:"));
        panelBusqueda.add(txtIdBuscar);
        panelBusqueda.add(btnBuscar);

        panelNorte.add(lblTitulo);
        panelNorte.add(panelBusqueda);
        add(panelNorte, BorderLayout.NORTH);

        // Formulario de datos (Centro)
        JPanel panelCentral = new JPanel(new GridLayout(3, 2, 10, 20));
        JLabel lblDir = new JLabel("Dirección de Entrega:");
        JTextField txtDir = new JTextField();

        JLabel lblTipo = new JLabel("Tipo de Paquete:");
        String[] opciones = {"Comida", "Encomienda", "Express"};
        JComboBox<String> cbTipo = new JComboBox<>(opciones);

        panelCentral.add(lblDir);
        panelCentral.add(txtDir);
        panelCentral.add(lblTipo);
        panelCentral.add(cbTipo);
        add(panelCentral, BorderLayout.CENTER);

        // Panel de acciones (Botones) (Sur)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar en BD");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        /**
         * Lógica para buscar un pedido mediante su ID.
         */
        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtIdBuscar.getText());
                // Uso del método buscarPorId a través del controlador
                Pedido p = PedidoControlador.buscarPedidoPorId(id);

                if (p != null) {
                    txtDir.setText(p.getDireccionEntrega());
                    cbTipo.setSelectedItem(p.getTipo());
                    JOptionPane.showMessageDialog(this, "Pedido encontrado. Estado actual: " + p.getEstado());
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el pedido con ID: " + id,
                            "Sin resultados", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        /**
         * Evento para procesar el registro del pedido.
         */
        btnGuardar.addActionListener(e -> {
            try {
                String direccion = txtDir.getText().trim();
                String tipo = (String) cbTipo.getSelectedItem();

                if (direccion.isEmpty()) {
                    throw new DatosInvalidosException("La dirección no puede estar vacía.");
                }

                if (direccion.length() < 5) {
                    throw new DatosInvalidosException("La dirección es demasiado corta.");
                }

                // El ID se envía como 0 porque en la BD es AUTO_INCREMENT
                Pedido nuevoPedido = new Pedido(0, direccion, tipo);

                // Llamar al controlador
                PedidoControlador.registrarPedido(nuevoPedido);

                JOptionPane.showMessageDialog(this,
                        "¡Pedido registrado exitosamente!",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

                dispose();

            } catch (DatosInvalidosException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Validación", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(),
                        "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            }
        });

        /**
         * Evento para cancelar y cerrar la ventana.
         */
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);
        add(panelBotones, BorderLayout.SOUTH);
    }
}