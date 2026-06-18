package main.Menu;

import java.awt.*;
import javax.swing.*;

public class FondoPanelSemi extends JPanel {

    private Image imagen;
    private Color colorSemi;


    public FondoPanelSemi(String ruta) {
        imagen = new ImageIcon(
                getClass().getResource(ruta)).getImage();
    }


    public FondoPanelSemi(Color color) {
        colorSemi = color;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (imagen != null) {

            g.drawImage(
                    imagen,
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    this);
        }

        if (colorSemi != null) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setColor(colorSemi);

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    30,
                    30);

            g2.dispose();
        }
    }
}