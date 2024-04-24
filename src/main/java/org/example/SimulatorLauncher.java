package org.example;

import org.example.buttons.MenuButton;
import org.example.frame.SimulatorFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SimulatorLauncher extends JFrame implements ActionListener {
    private final MenuButton sngl;
    private final MenuButton sngh;
    private final MenuButton cont;
    private final MenuButton exit;
    private File save;

    public SimulatorLauncher(){

        save = new File("save.txt");

        sngl = new MenuButton("Start new game in lattice", 100);
        sngl.addActionListener(this);
        sngl.setBounds(50, 100, 400, 100);

        sngh = new MenuButton("Start new game in hex", 250);
        sngh.addActionListener(this);
        sngh.setBounds(50, 250, 400, 100);

        cont = new MenuButton("Continue", 400);
        cont.setEnabled(save.exists());
        cont.addActionListener(this);

        exit = new MenuButton("Exit", 550);
        exit.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Simulator");
        this.setLayout(null);
        this.setSize(500, 750);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.BLACK);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        this.add(sngl);
        this.add(sngh);
        this.add(cont);
        this.add(exit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==sngl){
            this.dispose();
            new SimulatorFrame(1);
        }
        else if(e.getSource()==sngh){
            this.dispose();
            new SimulatorFrame(2);
        }
        else if(e.getSource()==cont) {
            Scanner reader = null;
            try {
                reader = new Scanner(save);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            int type = reader.nextInt();
            this.dispose();
            new SimulatorFrame(type, reader, save);
        }
        else if(e.getSource()==exit){
            this.dispose();
        }
    }

    public void deleteSave(){
        if(save.exists()){
            save.delete();
        }
    }
}
