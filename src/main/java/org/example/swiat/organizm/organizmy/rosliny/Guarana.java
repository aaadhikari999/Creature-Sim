package org.example.swiat.organizm.organizmy.rosliny;

import org.example.hex.swiat.SwiatHex;
import org.example.panel.MainPanel;
import org.example.swiat.organizm.Organizm;
import org.example.swiat.organizm.organizmy.Roslina;

import java.util.Random;

public class Guarana extends Roslina {

    public Guarana(MainPanel swiat){
        sila = 0;
        inicjatywa = 0;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        polozenie = randomizePolozenie();
        rysowanie();
    }

    public Guarana(MainPanel swiat, int x, int y){
        sila = 0;
        inicjatywa = 0;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        polozenie[0] = x; polozenie[1] = y;
        rysowanie();
    }

    @Override
    public int kolizja(Organizm atakujacy) {
        swiat.setToRemove(this);
        this.setAlive(false);
        swiat.setResults(atakujacy.getTypeOfOrganizm() + " zjadl " + this.getTypeOfOrganizm() + "<br>");
        atakujacy.setSila(atakujacy.getSila()+3);
        return 1;
    }

    @Override
    public void rysowanie() {
        swiat.setOnBoard(getPolozenie()[0], getPolozenie()[1], 9);
    }

    @Override
    public String getTypeOfOrganizm() {
        return "Guarana";
    }
}
