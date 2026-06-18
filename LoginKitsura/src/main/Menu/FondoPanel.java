package main.Menu;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FondoPanel extends JPanel{
    private Image imagen;

    public FondoPanel(String ruta) {
        imagen = new ImageIcon(ClassLoader.getSystemResource(ruta.substring(1))).getImage();
}
     @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(imagen,0,0,getWidth(),getHeight(),this);
    }
} 
