package com.speedfast.vista;


import javax.swing.*;
import java.awt.*;

/**
 * Ventana de autentificacion para el sistema SpeedFast.
 * Esta clase proporciona la interfaz inicial para que el usuario ingrese sus credenciales.
 * Si la validación es exitosa, permite el acceso a la Ventana Principal del sistema.
 * @author Benjamín Fernández-Niño
 */
public class VentanaLogin extends javax.swing.JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JButton btnEntrar;

    /**
     * Constructor que configura la geometría y visibilidad de los componentes.
     */
    public VentanaLogin() {
        setTitle("Acceso - SpeedFast");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // BorderLayout para separar el título del formulario
        setLayout(new BorderLayout());

        // Título
        JLabel lblHeader = new JLabel("CONTROL DE ACCESO", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 16));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(lblHeader, BorderLayout.NORTH);

        // Panel Central
        JPanel panelContenedor = new JPanel(new GridLayout(2, 2, 10, 20));
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        panelContenedor.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panelContenedor.add(txtUsuario);

        panelContenedor.add(new JLabel("Contraseña:"));
        txtClave = new JPasswordField();
        panelContenedor.add(txtClave);

        add(panelContenedor, BorderLayout.CENTER);

        // Panel Inferior para el botón
        JPanel panelBoton = new JPanel();
        panelBoton.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        btnEntrar = new JButton("Ingresar");
        btnEntrar.setPreferredSize(new Dimension(150, 35)); // Botón con tamaño fijo

        btnEntrar.addActionListener(e -> validarAcceso());

        panelBoton.add(btnEntrar);
        add(panelBoton, BorderLayout.SOUTH);
    }

    /**
     * Lógica de validación de credenciales.
     */
    private void validarAcceso() {
        String usuario = txtUsuario.getText();
        String clave = new String(txtClave.getPassword());

        if (usuario.equals("admin") && clave.equals("1234")) {
            new VentanaPrincipal().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos",
                    "Error de Acceso", JOptionPane.ERROR_MESSAGE);
        }
    }
}
