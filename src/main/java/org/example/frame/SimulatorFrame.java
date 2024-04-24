package org.example.frame;

import org.example.buttons.GameButton;
import org.example.lattice.swiat.SwiatLattice;
import org.example.listener.MyMouseListener;
import org.example.menu.MyMenuBar;
import org.example.panel.ButtonPanel;
import org.example.panel.InfPanel;
import org.example.hex.swiat.SwiatHex;
import org.example.panel.MainPanel;
import org.example.panel.TextPanel;
import org.example.swiat.organizm.Organizm;
import org.example.swiat.organizm.organizmy.rosliny.*;
import org.example.swiat.organizm.organizmy.zwierze.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Scanner;

public class SimulatorFrame extends JFrame implements ActionListener, KeyListener {

    private MyMenuBar menuBar;
    private MainPanel mainPanel;
    private TextPanel textPanel;
    private GameButton button;
    private MyMouseListener mouseListener;
    final private int x_size;
    final private int y_size;
    final private int mainContentWidth;
    final private int width;
    final private int height;
    final private int infPanelHeight;
    final private int textPanelWidth;
    final private int panelHeight;
    final private int buttonPanelHeight;

    public SimulatorFrame(int type){

        x_size = Integer.parseInt(JOptionPane.showInputDialog("Podaj szerokość pola:"));
        y_size = Integer.parseInt(JOptionPane.showInputDialog("Podaj wysokość pola:"));

        infPanelHeight = 40;
        if(type==2) panelHeight = y_size*47+95 + (x_size*23);
        else panelHeight = 42*2 + y_size*20;
        buttonPanelHeight = 40;

        textPanelWidth = 500;
        if(type==2) mainContentWidth = x_size*42+95;
        else mainContentWidth = x_size*20;

        width = mainContentWidth + textPanelWidth;
        height = panelHeight + infPanelHeight + buttonPanelHeight+57;

        InfPanel infPanel = new InfPanel(width, infPanelHeight);

        if(type==1) mainPanel = new SwiatLattice(x_size, y_size, infPanelHeight, this, true);
        else mainPanel = new SwiatHex(x_size, y_size, infPanelHeight, this, true);
        mouseListener = new MyMouseListener(this, mainPanel);
        mainPanel.addMouseListener(mouseListener);

        ButtonPanel buttonPanel = new ButtonPanel(mainContentWidth, buttonPanelHeight, infPanelHeight+panelHeight);
        button = buttonPanel.button;
        button.addActionListener(this);

        textPanel = new TextPanel(mainContentWidth, panelHeight+buttonPanelHeight, infPanelHeight);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Simulator");
        this.setLayout(null);
        this.setSize(width, height);
        this.setResizable(false);

        menuBar = new MyMenuBar();
        menuBar.saveItem.addActionListener(this);
        menuBar.exitItem.addActionListener(this);

        this.addKeyListener(this);
        this.setJMenuBar(menuBar);
        this.add(infPanel);
        this.add(mainPanel);
        this.add(buttonPanel);
        this.add(textPanel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public SimulatorFrame(int type,  Scanner reader1, File file){
        Scanner reader2 = null;
        try {
            reader2 = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < 3; i++){
            reader2.nextInt();
        }
        reader2.nextLine();
        reader2.nextLine();


        x_size = reader1.nextInt();
        y_size = reader1.nextInt();

        infPanelHeight = 40;
        if(type==2) panelHeight = y_size*47+95 + (x_size*23);
        else panelHeight =42*2 + y_size*20;
        buttonPanelHeight = 40;

        textPanelWidth = 500;
        if(type==2) mainContentWidth = x_size*42+95;
        else mainContentWidth = x_size*20;

        width = mainContentWidth + textPanelWidth;
        height = panelHeight + infPanelHeight + buttonPanelHeight+57;

        InfPanel infPanel = new InfPanel(width, infPanelHeight);

        if(type==1) mainPanel = new SwiatLattice(x_size, y_size, infPanelHeight, this, false);
        else mainPanel = new SwiatHex(x_size, y_size, infPanelHeight, this, false);
        mouseListener = new MyMouseListener(this, mainPanel);
        mainPanel.addMouseListener(mouseListener);

        ButtonPanel buttonPanel = new ButtonPanel(mainContentWidth, buttonPanelHeight, infPanelHeight+panelHeight);
        button = buttonPanel.button;
        button.addActionListener(this);

        textPanel = new TextPanel(mainContentWidth, panelHeight+buttonPanelHeight, infPanelHeight);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Simulator");
        this.setLayout(null);
        this.setSize(width, height);
        this.setResizable(false);

        menuBar = new MyMenuBar();
        menuBar.saveItem.addActionListener(this);
        menuBar.exitItem.addActionListener(this);

        this.addKeyListener(this);
        this.setJMenuBar(menuBar);
        this.add(infPanel);
        this.add(mainPanel);
        this.add(buttonPanel);
        this.add(textPanel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        wczytywanieGry(reader1, reader2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==menuBar.saveItem){
            try {
                saveGame();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else if(e.getSource()==menuBar.exitItem){
            this.dispose();
        }else if(e.getSource()==button){
            if(mainPanel.getCzlowiek()!=null){
                if(mainPanel.getCzlowiek().getAlive()){
                    mainPanel.clearResults();
                    mainPanel.setResults("<html>Podaj kierunek człowieka strzałkami lub aktywuj umiejętność przyciskiem 'U'<br></html>");
                    mainPanel.setCanSetKierunekCzlowieka(true);
                }
            }
            else {
                mainPanel.WykonajTure();
            }
            textPanel.setText(mainPanel.getResults());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP){
            if(mainPanel.getCanSetKierunekCzlowieka()){
                mainPanel.setKierunekCzlowieka(1);
                mainPanel.setCanSetKierunekCzlowieka(false);
                mainPanel.WykonajTure();
                textPanel.setText(mainPanel.getResults());
            }
        }else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if(mainPanel.getCanSetKierunekCzlowieka()){
                mainPanel.setKierunekCzlowieka(3);
                mainPanel.setCanSetKierunekCzlowieka(false);
                mainPanel.WykonajTure();
                textPanel.setText(mainPanel.getResults());
            }
        }else if(e.getKeyCode()==KeyEvent.VK_DOWN){
            if(mainPanel.getCanSetKierunekCzlowieka()){
                mainPanel.setKierunekCzlowieka(5);
                mainPanel.setCanSetKierunekCzlowieka(false);
                mainPanel.WykonajTure();
                textPanel.setText(mainPanel.getResults());
            }
        }else if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if(mainPanel.getCanSetKierunekCzlowieka()){
                mainPanel.setKierunekCzlowieka(7);
                mainPanel.setCanSetKierunekCzlowieka(false);
                mainPanel.WykonajTure();
                textPanel.setText(mainPanel.getResults());
            }
        }else if(e.getKeyCode()==KeyEvent.VK_U){
            if(mainPanel.getCzlowiek()!=null){
                Czlowiek czlowiek = (Czlowiek) mainPanel.getCzlowiek();
                if(czlowiek.getUmiejetnosc()){
                    mainPanel.clearResults();
                    mainPanel.setResults("<html>Umiejętność już jest aktywna<br></html>");
                    textPanel.setText(mainPanel.getResults());
                }else if(czlowiek.getNoOfToursToNextActivation()==0){
                    czlowiek.setUmiejetnosc(true);
                    czlowiek.setTimeToEndUmiejetnosc(5);
                    czlowiek.setNoOfToursToNextActivation(11);
                    mainPanel.setKierunekCzlowieka(0);
                    mainPanel.setCanSetKierunekCzlowieka(false);
                    mainPanel.WykonajTure();
                    textPanel.setText(mainPanel.getResults());
                }else{
                    mainPanel.clearResults();
                    mainPanel.setResults("<html>Umiejętność można będzie aktywować dopiero po " + czlowiek.getNoOfToursToNextActivation() + " turach<br></html>");
                    textPanel.setText(mainPanel.getResults());
                }
            }
        }
    }

    public void saveGame() throws IOException {
        PrintWriter writer = new PrintWriter("save.txt");

        if(mainPanel instanceof SwiatHex){ writer.println(2); }
        else writer.println(1);
        writer.println(mainPanel.GetSizeX() + " " + mainPanel.GetSizeY());
        writer.println(mainPanel.getResults());
        for(Organizm i : mainPanel.getOrganizmy()){
            if(i instanceof Czlowiek){
                Czlowiek iCz = (Czlowiek) i;
                writer.println(iCz.getTypeOfOrganizm() + " " + iCz.getPolozenie()[0] + " " + iCz.getPolozenie()[1] + " " + ((iCz.getAlive())?1:0) + " " + iCz.getSila() + " " + iCz.getInicjatywa() + " " + iCz.getLifeTime() + " " + ((iCz.getUmiejetnosc())?1:0) + " " + iCz.getTimeToEndUmiejetnosc() + " " + iCz.getNoOfToursToNextActivation());
            }else
                writer.println(i.getTypeOfOrganizm() + " " + i.getPolozenie()[0] + " " + i.getPolozenie()[1] + " " + ((i.getAlive())?1:0) + " " + i.getSila() + " " + i.getInicjatywa() + " " + i.getLifeTime());
        }
        writer.close();
    }

    public void wczytywanieGry(Scanner reader1, Scanner reader2){
        int x=0, y=0, alive=0, sila=0, inicjatywa=0, lifeTime=0, umiejetnosc=0, timeToEndUmiejetnosc = 0, noOfToursToNextActivation=0;

        reader1.nextLine();
        textPanel.setText(reader1.nextLine());
        while(reader1.hasNextLine()){
            Organizm organizm;
            String inf = reader1.nextLine();
            String typeOfOrganizm = getTypeFromInf(inf);
            reader2.next();
            if(!reader2.hasNextInt()) reader2.next();
            if(typeOfOrganizm.equals("Człowiek")){
                x = reader2.nextInt(); y = reader2.nextInt(); alive = reader2.nextInt(); sila = reader2.nextInt(); inicjatywa = reader2.nextInt(); lifeTime = reader2.nextInt(); umiejetnosc = reader2.nextInt(); timeToEndUmiejetnosc = reader2.nextInt(); noOfToursToNextActivation = reader2.nextInt();
            }else {
                x = reader2.nextInt(); y = reader2.nextInt(); alive = reader2.nextInt(); sila = reader2.nextInt(); inicjatywa = reader2.nextInt(); lifeTime = reader2.nextInt();
            }
            organizm = createOrganizm(typeOfOrganizm);
            mainPanel.setOnBoard(organizm.getPolozenie()[0], organizm.getPolozenie()[1], 0);
            if(organizm instanceof Czlowiek){
                if(umiejetnosc==1) ((Czlowiek) organizm).setUmiejetnosc(true);
                else ((Czlowiek) organizm).setUmiejetnosc(false);
                ((Czlowiek) organizm).setTimeToEndUmiejetnosc(timeToEndUmiejetnosc);
                ((Czlowiek) organizm).setNoOfToursToNextActivation(noOfToursToNextActivation);
            }
            organizm.setSila(sila); organizm.setInicjatywa(inicjatywa); organizm.setLifeTime(lifeTime); organizm.setPolozenie(x, y);
            if(alive==1) organizm.setAlive(true);
            else organizm.setAlive(false);

            mainPanel.addOrganizm(organizm);
            organizm.rysowanie();
        }
        mainPanel.rysujSwiat();
    }

    public String getTypeFromInf(String string){
        int indexOfEnd = 0;
        for(int i = 0; i < string.length(); i++){
            if(Character.isDigit(string.charAt(i))){
                indexOfEnd = i-1;
                break;
            }
        }
        return string.substring(0,indexOfEnd);
    }

    public Organizm createOrganizm(String type){
        Organizm organizm;
        if(type.equals("Barszcz Sosnowksiego")) organizm = new Barszcz_Sosnowskiego(mainPanel);
        else if(type.equals("Guarana")) organizm = new Guarana(mainPanel);
        else if(type.equals("Mlecz")) organizm = new Mlecz(mainPanel);
        else if(type.equals("Trawa")) organizm = new Trawa(mainPanel);
        else if(type.equals("Wilcze Jagody")) organizm = new Wilcze_Jagody(mainPanel);
        else if(type.equals("Antylopa")) organizm = new Antylopa(mainPanel);
        else if(type.equals("Człowiek")) organizm = new Czlowiek(mainPanel);
        else if(type.equals("Lis")) organizm = new Lis(mainPanel);
        else if(type.equals("Owca")) organizm = new Owca(mainPanel);
        else if(type.equals("Wilk")) organizm = new Wilk(mainPanel);
        else if(type.equals("Zolw")) organizm = new Zolw(mainPanel);
        else organizm = new Trawa(mainPanel);
        return organizm;
    }

    public void setTextToTextPanel(String string){
        textPanel.setText(string);
    }
}
