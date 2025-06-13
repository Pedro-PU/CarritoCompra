package ec.edu.ups.poo.clases.vista;

import ec.edu.ups.poo.clases.modelo.ItemCarrito;
import ec.edu.ups.poo.clases.servicio.CarritoService;
import ec.edu.ups.poo.clases.servicio.CarritoServiceImpl;

import javax.swing.*;
import java.awt.event.*;

public class ImprimirView extends JFrame {
    private JPanel cbxPanelPrincipal;
    private JPanel cbxTitulo;
    private JPanel cbxContenido;
    private JLabel lblTitulo;
    private JTextArea textAreaCarrito;
    private JPanel cbxAccion;
    private JTextField txtBuscarProducto;
    private JButton btnEliminarProducto;
    private JButton vaciarButton;
    private JButton btnActualizar;
    private CarritoServiceImpl carrito;

    public ImprimirView(CarritoService carrito) {
        setContentPane(cbxPanelPrincipal);
        setTitle("Impresión Datos del producto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        this.carrito = (CarritoServiceImpl) carrito;

        textAreaCarrito.setEditable(false);
        textAreaCarrito.setText(""); // Limpia el contenido anterior
        textAreaCarrito.append("Contenido del carrito:\n");

        for (ItemCarrito item : carrito.obtenerItems()) {
            textAreaCarrito.append("- " + item + "\n");
        }
        textAreaCarrito.append("\n");
        double total = carrito.calcularTotal();
        textAreaCarrito.append("Total: $" + total);
        btnEliminarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buscar = txtBuscarProducto.getText();
                int buscarProducto = Integer.parseInt(buscar);
                carrito.eliminarProducto(buscarProducto );
                txtBuscarProducto.setText("");
            }
        });
        vaciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carrito.vaciarCarrito();
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new VentanaPrincipalView(carrito).setVisible(true);
                dispose();
            }
        });
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTextArea();
            }
        });
    }

    private void actualizarTextArea() {
        textAreaCarrito.setText("");
        textAreaCarrito.append("Contenido del carrito:\n");

        for (ItemCarrito item : carrito.obtenerItems()) {
            textAreaCarrito.append("- " + item + "\n");
        }

        textAreaCarrito.append("\n");
        double total = carrito.calcularTotal();
        textAreaCarrito.append("Total: $" + total);
    }
}
