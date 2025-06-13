package ec.edu.ups.poo.clases.vista;

import ec.edu.ups.poo.clases.servicio.CarritoService;
import ec.edu.ups.poo.clases.servicio.CarritoServiceImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipalView extends JFrame {
    private JPanel cbxPanelPrincipal;
    private JPanel cbxOpcion1;
    private JPanel cbxOpcion2;
    private JButton btnOpcion1;
    private JLabel lblOpcion1;
    private JButton btnButton;
    private JPanel cbxTitulo;

    private CarritoServiceImpl carrito;

    public VentanaPrincipalView(CarritoService carrito) {
        setContentPane(cbxPanelPrincipal);
        setTitle("Carrito de Compras");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        this.carrito = (CarritoServiceImpl) carrito;


        btnOpcion1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductoView(carrito);
                setVisible(false);
            }
        });


        btnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ImprimirView(carrito);
                setVisible(false);
            }
        });
    }
}
