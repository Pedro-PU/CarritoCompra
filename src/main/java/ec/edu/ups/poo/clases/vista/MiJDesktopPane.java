package ec.edu.ups.poo.clases.vista;

import javax.swing.*;
import java.awt.*;

public class MiJDesktopPane extends JDesktopPane {

    public MiJDesktopPane() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // --- Configuración ---
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // --- Fondo blanco ---
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // --- Carrito como "división" ---
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));

        // Línea horizontal superior (como en "3 ⟌12")
        g2d.drawLine(centerX - 50, centerY - 40, centerX + 50, centerY - 40);

        // Cuerpo del carrito (rectángulo dividido en secciones)
        g2d.drawRect(centerX - 60, centerY - 30, 120, 60); // Marco exterior

        // Líneas horizontales internas (como pasos de división)
        for (int i = 1; i <= 2; i++) {
            g2d.drawLine(centerX - 60, centerY - 30 + (i * 30), centerX + 60, centerY - 30 + (i * 30));
        }

        // Líneas verticales (para huecos de rejilla)
        for (int i = 1; i <= 3; i++) {
            g2d.drawLine(centerX - 60 + (i * 30), centerY - 30, centerX - 60 + (i * 30), centerY + 30);
        }

        // --- Asa a la izquierda (como el divisor "⟌") ---
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(centerX - 80, centerY - 45, centerX - 60, centerY - 30); // Diagonal
        g2d.drawLine(centerX - 80, centerY - 45, centerX - 80, centerY + 15); // Vertical

        // --- Ruedas como "números" ---
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("○", centerX - 40, centerY + 50); // Rueda izquierda (símbolo circular)
        g2d.drawString("○", centerX + 30, centerY + 50);  // Rueda derecha

        // --- Texto "Compras" alineado bajo el carrito ---
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        String texto = "Compras";
        int textoWidth = g2d.getFontMetrics().stringWidth(texto);
        g2d.drawString(texto, centerX - textoWidth / 2, centerY + 80);
    }
}
