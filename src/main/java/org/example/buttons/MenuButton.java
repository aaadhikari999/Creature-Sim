package org.example.buttons;

import javax.swing.*;
import java.awt.*;

public class MenuButton extends JButton {

   public MenuButton(String string, int y){
        this.setText(string);
        this.setBounds(125, y, 250, 100);
        this.setFocusable(false);
        this.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        this.setForeground(Color.WHITE);
        this.setBackground(Color.DARK_GRAY);
    }
}
