/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Sistema_Solar;

/**
 *
 * @author Davil
 */
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedPanelBorda extends JPanel {
    private int arcWidth = 20;
    private int arcHeight = 20;
    private Color borderColor = new Color(100, 100, 100); // cinza escuro
    private int borderThickness = 1;

    public RoundedPanelBorda() {
        setOpaque(false); // necessário para que os cantos apareçam
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // desenha o fundo dos filhos normalmente
        Graphics2D g2 = (Graphics2D) g.create();

        // Anti-aliasing para suavizar
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Desenha fundo com cantos arredondados
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Float(
                borderThickness / 2f,
                borderThickness / 2f,
                getWidth() - borderThickness,
                getHeight() - borderThickness,
                arcWidth, arcHeight
        ));

        // Desenha a borda (linha)
        g2.setStroke(new BasicStroke(borderThickness));
        g2.setColor(borderColor);
        g2.draw(new RoundRectangle2D.Float(
                borderThickness / 2f,
                borderThickness / 2f,
                getWidth() - borderThickness,
                getHeight() - borderThickness,
                arcWidth, arcHeight
        ));

        g2.dispose();
    }
}
