package org.example.listener;

import org.example.frame.SimulatorFrame;
import org.example.hex.PanelHex;
import org.example.lattice.PanelLattice;
import org.example.panel.MainPanel;
import org.example.swiat.organizm.Organizm;
import org.example.swiat.organizm.organizmy.rosliny.*;
import org.example.swiat.organizm.organizmy.zwierze.*;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListener implements MouseListener {
    private SimulatorFrame simulatorFrame;
    private MainPanel swiat;

    public MyMouseListener(SimulatorFrame simulatorFrame, MainPanel swiat){
        this.simulatorFrame = simulatorFrame;
        this.swiat = swiat;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x, y;
        if(swiat instanceof PanelLattice){
            x = e.getX()/20;
            y = (e.getY()-42)/20;
        }else {
            x = (e.getX()-95/2)/42;
            y = (e.getY()-95/2-(swiat.GetSizeX()-x)*23)/47;
        }
        if(swiat.getFromBoard(x, y)==0){
            int org = Integer.parseInt(JOptionPane.showInputDialog("Podaj organizm:\n1.Antylopa   2.Lis\n3.Owca  4.Wilk   5.Żółw\n6.Barszcz Sosnowskiego\n7.Guarana   8.Mlecz\n9.Trawa   10.Wilcze Jagody"));
            Organizm organizm=null;
            switch(org){
                case 1:{ organizm = new Antylopa(swiat, x, y); break; }
                case 2:{ organizm = new Lis(swiat, x, y); break;}
                case 3:{ organizm = new Owca(swiat, x, y); break; }
                case 4:{ organizm = new Wilk(swiat, x, y); break; }
                case 5:{ organizm = new Zolw(swiat, x, y); break; }
                case 6:{ organizm = new Barszcz_Sosnowskiego(swiat, x, y); break; }
                case 7:{ organizm = new Guarana(swiat, x, y); break; }
                case 8:{ organizm = new Mlecz(swiat, x, y); break; }
                case 9:{ organizm = new Trawa(swiat, x, y); break; }
                case 10:{ organizm = new Wilcze_Jagody(swiat, x, y); break; }
                default: break;
            }
            swiat.addOrganizm(organizm);
            swiat.rysujSwiat();
            swiat.clearResults();
            if(organizm!=null)
                swiat.setResults("<html>Został utworzony " + organizm.getTypeOfOrganizm() + " na {" + x + "," + y + "}<br></html>");
            simulatorFrame.setTextToTextPanel(swiat.getResults());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
