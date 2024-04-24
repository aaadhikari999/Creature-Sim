package org.example.swiat.organizm.organizmy.rosliny;

import org.example.hex.swiat.SwiatHex;
import org.example.panel.MainPanel;
//import sun.applet.Main;
import org.example.swiat.organizm.Organizm;
import org.example.swiat.organizm.organizmy.Roslina;
import org.example.swiat.organizm.organizmy.zwierze.Czlowiek;

import java.util.ArrayList;
import java.util.Random;

public class Barszcz_Sosnowskiego extends Roslina {

    public Barszcz_Sosnowskiego(MainPanel swiat){
        sila = 10;
        inicjatywa = 0;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        polozenie = randomizePolozenie();
        rysowanie();
    }

    public Barszcz_Sosnowskiego(MainPanel swiat, int x, int y){
        sila = 10;
        inicjatywa = 0;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        polozenie[0] = x; polozenie[1] = y;
        rysowanie();
    }

    @Override
    public void akcja(){
        zabicWszystkichZwierzatWokol();
    }

    @Override
    public int kolizja(Organizm atakujacy) {
        swiat.setToRemove(this);
        swiat.setToRemove(atakujacy);
        this.setAlive(false);
        atakujacy.setAlive(false);
        if(atakujacy instanceof Czlowiek) swiat.setCzlowiek(null);
        swiat.setResults(atakujacy.getTypeOfOrganizm() + " zjadl " + this.getTypeOfOrganizm() + " i zaginął<br>");
        return 0;
    }

    @Override
    public String getTypeOfOrganizm() {
        return "Barszcz Sosnowskiego";
    }

    @Override
    public void rysowanie() {
        swiat.setOnBoard(getPolozenie()[0], getPolozenie()[1], 11);
    }

    public void zabicWszystkichZwierzatWokol(){
        ArrayList<Organizm> zabic = swiat.getAllZwierzetaAroundCoordinate(polozenie[0], polozenie[1]);
        for(Organizm i : zabic){
            swiat.setToRemove(i);
            i.setAlive(false);
            if(i instanceof Czlowiek) swiat.setCzlowiek(null);
            swiat.setOnBoard(i.getPolozenie()[0], i.getPolozenie()[1], 0);
            swiat.setResults(this.getTypeOfOrganizm() + " zabił blisko stojącego " + i.getTypeOfOrganizm() + "<br>");
        }
    }
}
