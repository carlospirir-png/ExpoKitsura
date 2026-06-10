package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.JPanel;

public class PanelDecoracion extends JPanel {

    public PanelDecoracion() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;


        Polygon estrella = new Polygon();

        int radioExterior = 270;
        int radioInterior = 180;

        for (int i = 0; i < 16; i++) {

            double angulo = Math.toRadians(-90 + (i * 22.5));

            int radio = (i % 2 == 0)
                    ? radioExterior
                    : radioInterior;

            int x = centroX + (int) (Math.cos(angulo) * radio);
            int y = centroY + (int) (Math.sin(angulo) * radio);

            estrella.addPoint(x, y);
        }

        g2.setColor(new Color(196, 221, 227));
        g2.fillPolygon(estrella);


        int diametro = 360;

        int x = centroX - diametro / 2;
        int y = centroY - diametro / 2;

        g2.setColor(new Color(74, 110, 157));

        g2.fillOval(x, y, diametro, diametro);

        g2.dispose();
    }
}