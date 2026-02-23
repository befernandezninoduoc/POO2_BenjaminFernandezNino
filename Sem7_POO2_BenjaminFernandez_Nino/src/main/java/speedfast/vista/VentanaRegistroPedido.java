package speedfast.vista;

import speedfast.controlador.PedidoControlador;
import speedfast.modelo.Pedido;
import javax.swing.*;
import java.awt.*;

/**
 * Formulario para el registro de pedidos integrados con Base de Datos.
 */
public class VentanaRegistroPedido extends JFrame {

    public VentanaRegistroPedido() {
        setTitle("Registrar Pedido - SpeedFast DB");
        setSize(350, 250);
        setLocationRelativeTo(null);

        // El ID ya no se pide porque la Base de Datos lo asigna (AUTO_INCREMENT)
        setLayout(new GridLayout(4, 2, 10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblDir = new JLabel("Dirección:");
        JTextField txtDir = new JTextField();

        JLabel lblTipo = new JLabel("Tipo:");
        String[] opciones = {"Comida", "Encomienda", "Express"};
        JComboBox<String> cbTipo = new JComboBox<>(opciones);

        JButton btnGuardar = new JButton("Guardar en BD");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                String direccion = txtDir.getText();
                String tipo = (String) cbTipo.getSelectedItem();

                if (direccion.trim().isEmpty()) {
                    throw new Exception("La dirección es obligatoria.");
                }

                // Ahora el ID se envía como 0 (o nulo) porque MySQL lo auto-incrementará
                Pedido nuevoPedido = new Pedido(0, direccion, tipo);

                // Esto ahora guarda en MySQL
                PedidoControlador.agregarPedido(nuevoPedido);

                JOptionPane.showMessageDialog(this, "Pedido guardado en la Base de Datos.");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        add(lblDir); add(txtDir);
        add(lblTipo); add(cbTipo);
        add(new JLabel()); add(new JLabel()); // Espacios
        add(btnCancelar); add(btnGuardar);
    }
}
