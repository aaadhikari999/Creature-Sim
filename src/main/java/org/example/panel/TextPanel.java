package org.example.panel;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {
    final int width;
    final int height;
    final int resztaWidth;
    JLabel label;

    public TextPanel(int resztaWidth, int height, int infPanelHeight) {
        this.width = 500;
        this.resztaWidth = resztaWidth;
        this.height = height;

        this.setBounds(resztaWidth, infPanelHeight, width, height);
        this.setBackground(new Color(21, 180, 140));
        this.setOpaque(true);

        label = new JLabel();

        this.add(label);
    }

    public void setText(String string){
        label.setText(string);
    }
}
