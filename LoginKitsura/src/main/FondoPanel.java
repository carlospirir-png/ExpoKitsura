package main;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class FondoPanel extends JPanel {

    private Image imagen;

    public FondoPanel(String ruta) {

        URL url = getClass().getResource(ruta);

        if (url == null) {
            throw new RuntimeException("No se encontró la imagen: " + ruta);
        }

        imagen = new ImageIcon(url).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
}