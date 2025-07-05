package ec.edu.ups.poo.clases.vista;

import javax.swing.*;
import java.awt.*;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

public class MiJDesktopPane extends JDesktopPane {
    private MensajeInternacionalizacionHandler mi;

    public MiJDesktopPane(MensajeInternacionalizacionHandler mi) {
        super();
        this.mi = mi;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //Activar suavizado
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        //Fondo degradado con azul oscuro
        GradientPaint fondoGradiente = new GradientPaint(
                0, 0, new Color(0, 51, 102),
                0, height, new Color(0, 22, 48)
        );
        g2d.setPaint(fondoGradiente);
        g2d.fillRect(0, 0, width, height);

        //Colores
        Color azulOscuro = new Color(0, 22, 48);
        Color azulClaro = new Color(0, 102, 204);
        Color amarilloTuti = new Color(255, 204, 0);

        //Rectángulo redondeado
        int boxWidth = Math.min(width, height) / 2 + 100;
        int boxHeight = boxWidth / 2;
        int x = (width - boxWidth) / 2;
        int y = (height - boxHeight) / 2 - 30;

        //Sombra del rectángulo
        g2d.setColor(new Color(0, 0, 0, 60));
        g2d.fillRoundRect(x + 6, y + 6, boxWidth, boxHeight, 60, 60);

        //Gradiente dentro del rectángulo
        GradientPaint gradiente = new GradientPaint(
                x, y, azulClaro,
                x + boxWidth, y + boxHeight, azulOscuro
        );
        g2d.setPaint(gradiente);
        g2d.fillRoundRect(x, y, boxWidth, boxHeight, 60, 60);

        //Borde amarillo
        g2d.setStroke(new BasicStroke(8));
        g2d.setColor(amarilloTuti);
        g2d.drawRoundRect(x, y, boxWidth, boxHeight, 60, 60);

        //Titulo
        String texto = "Tilin";
        Font fuente = new Font("SansSerif", Font.BOLD, (boxHeight / 2)+30);
        g2d.setFont(fuente);

        FontMetrics metrics = g2d.getFontMetrics();
        int textWidth = metrics.stringWidth(texto);
        int textHeight = metrics.getAscent();
        int textX = width / 2 - textWidth / 2;
        int textY = y + boxHeight / 2 + textHeight / 4;

        //Sombra del texto
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.drawString(texto, textX + 2, textY + 2);

        //Texto principal en amarillo
        g2d.setColor(amarilloTuti);
        g2d.drawString(texto, textX, textY);

        //Eslogan debajo
        String eslogan = mi.get("eslogan.texto");
        Font fuenteEslogan = new Font("SansSerif", Font.PLAIN, 20);
        g2d.setFont(fuenteEslogan);

        FontMetrics esloganMetrics = g2d.getFontMetrics();
        int esloganWidth = esloganMetrics.stringWidth(eslogan);
        int esloganX = (width - esloganWidth) / 2;
        int esloganY = y + boxHeight + 50;

        //Sombra del eslogan
        g2d.setColor(new Color(0, 0, 0, 80));
        g2d.drawString(eslogan, esloganX + 2, esloganY + 2);

        //Texto principal del eslogan
        g2d.setColor(amarilloTuti);
        g2d.drawString(eslogan, esloganX, esloganY);
    }
    public void actualizarIdioma() {
        repaint();
    }
}
