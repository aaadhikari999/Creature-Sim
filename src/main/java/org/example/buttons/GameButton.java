package org.example.buttons;

import javax.swing.*;
import java.awt.*;

public class GameButton extends JButton {

    public GameButton(){
        this.setText("Nowa Tura");
        this.setHorizontalAlignment(JButton.CENTER);
        this.setFocusable(false);
        this.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        this.setForeground(Color.WHITE);
        this.setBackground(Color.DARK_GRAY);
    }
}
