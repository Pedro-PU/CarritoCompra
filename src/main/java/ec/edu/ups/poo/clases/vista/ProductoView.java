package ec.edu.ups.poo.clases.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import ec.edu.ups.poo.clases.servicio.CarritoService;
import ec.edu.ups.poo.clases.servicio.CarritoServiceImpl;
import ec.edu.ups.poo.clases.modelo.*;


public class ProductoView extends JFrame {
    private JPanel cbxPanelPrincipal;
    private JButton btnAceptar;
    private JButton btnLimpiar;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JPanel cbxTitulo;
    private JLabel lblCantidad;
    private JPanel cbxBoton;
    private JPanel cbxIngreso;

    private CarritoServiceImpl carrito;

    public ProductoView(CarritoService carrito) {
        setContentPane(cbxPanelPrincipal);
        setTitle("Ingreso Datos del producto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        this.carrito = (CarritoServiceImpl) carrito;

        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostarDatos(carrito);
            }
        });
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtCodigo.setText("");
                txtNombre.setText("");
                txtPrecio.setText("");
                txtCantidad.setText("");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new VentanaPrincipalView(carrito).setVisible(true);
                dispose();
            }
        });
    }
    public void mostarDatos(CarritoService carrito) {
        String codigo = txtCodigo.getText();
        String nombre = txtNombre.getText();
        String precio = txtPrecio.getText();
        String cantidad = txtCantidad.getText();

        int codigoProducto = Integer.parseInt(codigo);

        Producto prod = new Producto(codigoProducto, nombre, Integer.parseInt(precio));
        carrito.agregarProducto(prod,Integer.parseInt(cantidad));

        System.out.println("Codigo: " + codigo);
        System.out.println("Nombre: " + nombre);
        System.out.println("Precio: " + precio);

    }


}
