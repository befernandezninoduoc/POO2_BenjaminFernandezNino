package speedfast.vista;

import speedfast.controlador.RepartidorControlador;
import speedfast.excepciones.DatosInvalidosException;
import speedfast.modelo.Repartidor;

import javax.swing.*;
import java.awt.*;

/**
 * Formulario para el alta y búsqueda de repartidores en el sistema.
 * @author Benjamín Fernández-Niño
 */
public class VentanaRegistroRepartidor extends JFrame {

    /**
     * Constructor que inicializa la interfaz de registro y búsqueda.
     */
    public VentanaRegistroRepartidor() {
        setTitle("Registrar Repartidor - SpeedFast");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Panel de Cabecera y Buscador (Norte)
        JPanel panelNorte = new JPanel(new GridLayout(2, 1, 5, 5));
        JLabel lblTitulo = new JLabel("Gestión de Personal Repartidor", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField txtIdBuscar = new JTextField(5);
        JButton btnBuscar = new JButton("Buscar por ID");
        panelBusqueda.add(new JLabel("ID a buscar:"));
        panelBusqueda.add(txtIdBuscar);
        panelBusqueda.add(btnBuscar);

        panelNorte.add(lblTitulo);
        panelNorte.add(panelBusqueda);
        add(panelNorte, BorderLayout.NORTH);

        // Formulario de datos (Centro)
        JPanel panelCentral = new JPanel(new GridLayout(2, 1, 5, 5));
        JLabel lblNombre = new JLabel("Nombre Completo del Repartidor:");
        JTextField txtNombre = new JTextField();

        panelCentral.add(lblNombre);
        panelCentral.add(txtNombre);
        add(panelCentral, BorderLayout.CENTER);

        // Panel de acciones (Botones) (Sur)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        /**
         * Lógica para buscar un repartidor mediante su ID.
         */
        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtIdBuscar.getText());
                // Uso del método buscarPorId a través del controlador
                Repartidor r = RepartidorControlador.buscarRepartidorPorId(id);

                if (r != null) {
                    txtNombre.setText(r.getNombre());
                    JOptionPane.showMessageDialog(this, "Repartidor encontrado: " + r.getNombre());
                } else {
                    JOptionPane.showMessageDialog(this, "No existe repartidor con ID: " + id, "Sin resultados", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        /**
         * Lógica para guardar el repartidor mediante el controlador.
         */
        btnGuardar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText();

                // Crear el objeto repartidor (ID 0 para autoincremento en BD)
                Repartidor r = new Repartidor(0, nombre);

                // Llamar al controlador para persistencia y validación
                RepartidorControlador.registrarRepartidor(r);

                JOptionPane.showMessageDialog(this, "Repartidor guardado con éxito");
                dispose();
            } catch (DatosInvalidosException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        /**
         * Cierra la ventana sin realizar cambios.
         */
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);
        add(panelBotones, BorderLayout.SOUTH);
    }
}