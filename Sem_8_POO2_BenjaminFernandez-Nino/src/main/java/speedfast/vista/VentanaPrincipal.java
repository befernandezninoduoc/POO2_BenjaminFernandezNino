package speedfast.vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal con el menú de navegación completo del sistema SpeedFast.
 * Centraliza el acceso a Pedidos, Repartidores y Entregas.
 * @author Benjamín Fernández-Niño
 */
public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        // Configuración básica
        setTitle("Sistema SpeedFast - PANEL DE CONTROL");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Título
        JLabel lblTitulo = new JLabel("Menú Principal SpeedFast", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel de Botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(7, 1, 12, 12)); // 7 filas de botones
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 60, 20, 60));

        // Inicialización de botones
        JButton btnRegPedido = new JButton(" Registrar Nuevo Pedido");
        JButton btnLisPedido = new JButton(" Ver Listado de Pedidos");

        JButton btnRegRepartidor = new JButton(" Registrar Nuevo Repartidor");
        JButton btnLisRepartidor = new JButton(" Ver Lista de Repartidores");

        JButton btnAsigEntrega = new JButton(" Asignar/Registrar Entrega");
        JButton btnVerEntregas = new JButton(" Ver Historial de Entregas");

        JButton btnSalir = new JButton("🚪 Cerrar Sesión");
        btnSalir.setForeground(Color.RED);

        // Eventos de Navegación

        btnRegPedido.addActionListener(e -> new VentanaRegistroPedido().setVisible(true));
        btnLisPedido.addActionListener(e -> new VentanaListaPedidos().setVisible(true));

        btnRegRepartidor.addActionListener(e -> new VentanaRegistroRepartidor().setVisible(true));
        btnLisRepartidor.addActionListener(e -> new VentanaListaRepartidor().setVisible(true));

        btnAsigEntrega.addActionListener(e -> new VentanaRegistroEntrega().setVisible(true));
        btnVerEntregas.addActionListener(e -> new VentanaListaEntregas().setVisible(true));

        btnSalir.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(this, "¿Cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                new VentanaLogin().setVisible(true);
                this.dispose();
            }
        });

        // Agregar botones al panel
        panelBotones.add(btnRegPedido);
        panelBotones.add(btnLisPedido);
        panelBotones.add(btnRegRepartidor);
        panelBotones.add(btnLisRepartidor);
        panelBotones.add(btnAsigEntrega);
        panelBotones.add(btnVerEntregas);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);


    }
}