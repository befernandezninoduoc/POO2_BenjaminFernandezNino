package com.speedfast.vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal con el menú de navegación del sistema SpeedFast.
 * Esta clase hereda de JFrame y actúa como el menú central de la app
 * permitiendo la navegación hacia el registro y listado de pedidos.
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    /**
     * Constructor de la VentanaPrincipal.
     * Configura el diseño, los componentes y los eventos de navegación.
     */
    public VentanaPrincipal() {
        //configuracion de la ventana
        setTitle("Sistema SpeedFast - MENU");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //Centrar la pantalla
        setLayout(new BorderLayout(10,10));

        // Título superior
        JLabel lblTitulo = new JLabel("Panel de Control SpeedFast", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel central para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Inicialización de botones
        JButton btnRegistrar = new JButton("Registrar Nuevo Pedido");
        JButton btnListar = new JButton("Ver Listado de Pedidos");
        JButton btnSalir = new JButton("Cerrar Sesión");


        /**
         * Evento para abrir la ventana de registro de pedidos.
         */
        btnRegistrar.addActionListener(e -> {
            VentanaRegistroPedido vRegistro = new VentanaRegistroPedido();
            vRegistro.setVisible(true);
        });

        /**
         * Evento para abrir la ventana de listado de pedidos.
         */
        btnListar.addActionListener(e -> {
            VentanaListaPedidos vLista = new VentanaListaPedidos();
            vLista.setVisible(true);
        });

        /**
         * Evento para cerrar la sesión y regresar al Login.
         */
        btnSalir.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que desea cerrar sesión?", "Confirmar",
                    JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                new VentanaLogin().setVisible(true);
                this.dispose();
            }
        });

        // Agregar botones al panel
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnListar);
        panelBotones.add(btnSalir);

        // Agregar panel a la ventana
        add(panelBotones, BorderLayout.CENTER);

        // Pie de página decorativo
        JLabel lblFooter = new JLabel("Conectado como: Administrador", SwingConstants.LEFT);
        lblFooter.setFont(new Font("SansSerif", Font.ITALIC, 10));
        lblFooter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
        add(lblFooter, BorderLayout.SOUTH);
    }
}