package org.example.swiat.organizm.organizmy.zwierze;

import org.example.hex.swiat.SwiatHex;
import org.example.panel.MainPanel;
import org.example.swiat.organizm.organizmy.Zwierze;

import java.util.Random;

public class Owca extends Zwierze {

    public Owca(MainPanel swiat){
        sila = 4;
        inicjatywa = 4;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        polozenie = randomizePolozenie();
        rysowanie();
    }

    public Owca(MainPanel swiat, int x, int y){
        sila = 4;
        inicjatywa = 4;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        polozenie[0] = x; polozenie[1] = y;
        rysowanie();
    }

    @Override
    public void rysowanie() {
        swiat.setOnBoard(getPolozenie()[0], getPolozenie()[1], 2);
    }

    @Override
    public String getTypeOfOrganizm() {
        return "Owca";
    }
}
