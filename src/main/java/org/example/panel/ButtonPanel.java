package org.example.panel;

import org.example.buttons.GameButton;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {

    public GameButton button;

    public ButtonPanel(int width, int height, int resztaHeight){
        button = new GameButton();
        this.setBackground(new Color(21, 180, 140));
        this.setOpaque(true);

        this.add(button);
        this.setBounds(0, resztaHeight, width, height);
    }
}
