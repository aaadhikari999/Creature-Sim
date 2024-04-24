package org.example.panel;

import javax.swing.*;
import java.awt.*;

public class InfPanel extends JPanel {

   final int x_size;

    public InfPanel(int width, int height){
        x_size = width;
        JLabel label = new JLabel();
        label.setText("Uladzislau Lukashevich, nr. 191608");
        label.setFont(new Font("Arial", Font.ITALIC, height/2));
        this.setBackground(new Color(181, 201, 91));
        this.setOpaque(true);

        this.add(label);
        this.setBounds(0, 0, width, height);
    }
}
