package com.speedfast;

import com.speedfast.vista.VentanaLogin;

import javax.swing.*;

/**
 * Clase principal que lanza la aplicación SpeedFast
 * @author Benjamín Fernández-Niño
 */
public class Main {
    /**
     * Punto de entrada principal. Inicia el hilo de la interfaz gráfica.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaLogin().setVisible(true);
        });
    }
}
