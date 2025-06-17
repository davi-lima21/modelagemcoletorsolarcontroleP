package com.mycompany.Sistema_Solar;



import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedPanel extends JPanel {
    private int arcWidth = 20;
    private int arcHeight = 20;

    public RoundedPanel() {
        setOpaque(false); // necessário para que os cantos arredondados sejam visíveis
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Desenha o painel arredondado com a cor de fundo
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcWidth, arcHeight));

        g2.dispose();
        super.paintComponent(g); // desenha os componentes filhos normalmente
    }
}

