package com.speedfast.vista;

import com.speedfast.controlador.PedidoControlador;
import com.speedfast.modelo.Pedido;

import javax.swing.*;
import java.awt.*;

/**
 * Formulario para el registro de nuevos pedidos en el sistema.
 * Implementa validaciones básicas y se comunica con el PedidoControlador.
 */
public class VentanaRegistroPedido extends javax.swing.JFrame {

    public VentanaRegistroPedido() {
        setTitle("Registro de pedido - SpeedFast");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        //Componentes de la intefaz
        JLabel lblId = new JLabel("Id pedido:");
        JTextField idPedido = new JTextField();

        JLabel lblDireccion = new JLabel("Direccion:");
        JTextField direccionPedido = new JTextField();

        JLabel lblTipo = new JLabel("Tipo:");
        String[] opciones = {"Comida", "Encomienda", "Express"};
        JComboBox<String> tipoPedido = new JComboBox<>(opciones);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        // Lógica del boton guardar
        btnGuardar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idPedido.getText());
                String direccion = direccionPedido.getText();
                String tipo = (String) tipoPedido.getSelectedItem();

                if(direccion.isEmpty()) {
                    throw new Exception("La direccion no puede estar vacia.");
                }

                //creación objeto y lo envía a controlador
                Pedido nuevoPedido = new Pedido(id, direccion, tipo);
                PedidoControlador.agregarPedido(nuevoPedido);

                JOptionPane.showMessageDialog(this, "Pedido agregado correctamente.");
                dispose(); //cierra la ventana despues de guardar
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La ID debe ser un numero entero.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCancelar.addActionListener(e -> dispose()); {
            add(lblId); add(idPedido);
            add(lblDireccion); add(direccionPedido);
            add(lblTipo); add(tipoPedido);
            add(btnCancelar); add(btnGuardar);
        }
    }
}
