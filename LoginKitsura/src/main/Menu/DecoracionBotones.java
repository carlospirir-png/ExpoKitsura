
package main.Menu;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DecoracionBotones extends JButton {
    
    // Añadimos los colores de nuestra paleta
    private Color colorBase = Color.decode("#447A9C");      // Azul Oscuro (Paleta Principal)
    private Color colorHover = Color.decode("#82D3E0");     // Celeste (Paleta Principal)
    private Color colorTexto = Color.decode("#EFDA9A");     // Amarillo Mantequilla (Paleta Secundaria)
    private Color colorBorde = Color.decode("#3E454C");     // Gris Carbon (Paleta Secundaria)
    
    // Constructor 1: El botón azul estándar
    public DecoracionBotones(String text) {
        super(text);
        inicializarComponente();
    }

    // Constructor 2: Por si quieres crear un botón con otros colores de tu paleta (ej. el Salmón)
    public DecoracionBotones(String text, String hexBase, String hexHover, String hexBorde) {
        super(text);
        this.colorBase = Color.decode(hexBase);
        this.colorHover = Color.decode(hexHover);
        this.colorBorde = Color.decode(hexBorde);
        inicializarComponente();
    }

    // Aquí se configura todo el comportamiento visual del botón
    private void inicializarComponente() {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        
        setForeground(colorTexto);
        setBackground(colorBase);
        
        // Fuente monospaciada que simula bastante bien el estilo píxel si no tienes un archivo .ttf
        setFont(new Font("Monospaced", Font.BOLD, 16)); 
        setBorder(createPixelBorder(colorBorde));

        // Eventos para detectar cuando el cursor entra o sale del botón
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(colorHover);
                setForeground(colorBase); // Invierte el color del texto al pasar el mouse
                setBorder(createPixelBorder(colorBase));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(colorBase);
                setForeground(colorTexto);
                setBorder(createPixelBorder(colorBorde));
                repaint();
            }
        });
    }

    // Dibuja el fondo sólido y plano (estilo pixel art, sin suavizados)
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(getBackground());
        // Rellena el interior dejando espacio para el borde de 4 píxeles
        g2d.fillRect(4, 4, getWidth() - 8, getHeight() - 8);
        g2d.dispose();
        
        super.paintComponent(g);
    }

    // Crea un borde grueso "bloque por bloque" característico del pixel art
    private Border createPixelBorder(Color colorDelBorde) {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(4, 4, 4, 4, colorDelBorde), // Borde de 4px de grosor
            BorderFactory.createEmptyBorder(6, 14, 6, 14)               // Margen del texto interno
        );
    }
}
