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

public class RoundedPanelBordaShadow extends JPanel {
    private int arcWidth = 20;
    private int arcHeight = 20;
    private int sombraOffset = 2; // distância da sombra
    private Color sombraColor = new Color(0, 0, 0, 50); // sombra preta semi-transparente
    //private Color borderColor = new Color(100, 100, 100);
    private int borderThickness = 1;

    public RoundedPanelBordaShadow() {
        setOpaque(false); // necessário para ver a sombra
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Sombra (deslocada para baixo e direita)
        g2.setColor(sombraColor);
        g2.fill(new RoundRectangle2D.Float(
                sombraOffset, sombraOffset,
                getWidth() - sombraOffset - borderThickness,
                getHeight() - sombraOffset - borderThickness,
                arcWidth, arcHeight
        ));

        // 2. Fundo branco (ou outra cor)
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Float(
                0, 0,
                getWidth() - sombraOffset - borderThickness,
                getHeight() - sombraOffset - borderThickness,
                arcWidth, arcHeight
        ));

        // 3. Borda (opcional)
        if (borderThickness > 0) {
            g2.setStroke(new BasicStroke(borderThickness));
            //g2.setColor(borderColor);
            g2.draw(new RoundRectangle2D.Float(
                    0, 0,
                    getWidth() - sombraOffset - borderThickness,
                    getHeight() - sombraOffset - borderThickness,
                    arcWidth, arcHeight
            ));
        }

        g2.dispose();
        super.paintComponent(g);
    }
}

