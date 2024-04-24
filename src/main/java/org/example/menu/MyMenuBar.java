package org.example.menu;

import javax.swing.*;

public class MyMenuBar extends JMenuBar {

    public JMenuItem saveItem;
    public JMenuItem exitItem;

    public MyMenuBar(){
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu helpMenu = new JMenu("Help");
        ;
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        this.add(fileMenu);
        this.add(editMenu);
        this.add(helpMenu);
    }
}
