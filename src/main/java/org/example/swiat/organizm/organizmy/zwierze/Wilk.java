package org.example.swiat.organizm.organizmy.zwierze;

import org.example.hex.swiat.SwiatHex;
import org.example.panel.MainPanel;
import org.example.swiat.organizm.organizmy.Zwierze;

import java.util.Random;

public class Wilk extends Zwierze {

    public Wilk(MainPanel swiat){
        sila = 9;
        inicjatywa = 5;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        polozenie = randomizePolozenie();
        rysowanie();
    }

    public Wilk(MainPanel swiat, int x, int y){
        sila = 9;
        inicjatywa = 5;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        polozenie[0] = x; polozenie[1] = y;
        rysowanie();
    }

    @Override
    public void rysowanie() {
        swiat.setOnBoard(getPolozenie()[0], getPolozenie()[1], 1);
    }

    @Override
    public String getTypeOfOrganizm() {
        return "Wilk";
    }
}
