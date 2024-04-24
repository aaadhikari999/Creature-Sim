package org.example.swiat.organizm.organizmy.rosliny;

import org.example.hex.swiat.SwiatHex;
import org.example.panel.MainPanel;
import org.example.swiat.organizm.Organizm;
import org.example.swiat.organizm.organizmy.Roslina;
import org.example.swiat.organizm.organizmy.zwierze.Czlowiek;

import java.util.Random;

public class Wilcze_Jagody extends Roslina {

    public Wilcze_Jagody(MainPanel swiat){
        sila = 99;
        inicjatywa = 0;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        polozenie = randomizePolozenie();
        rysowanie();
    }

    public Wilcze_Jagody(MainPanel swiat, int x, int y){
        sila = 99;
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
        swiat.setToRemove(atakujacy);
        this.setAlive(false);
        atakujacy.setAlive(false);
        swiat.setOnBoard(polozenie[0], polozenie[1], 0);
        if(atakujacy instanceof Czlowiek) swiat.setCzlowiek(null);
        swiat.setResults(atakujacy.getTypeOfOrganizm() + " zjadl " + this.getTypeOfOrganizm() + " i zaginął<br>");
        return 0;
    }

    @Override
    public void rysowanie() {
        swiat.setOnBoard(getPolozenie()[0], getPolozenie()[1], 10);
    }

    @Override
    public String getTypeOfOrganizm() {
        return "Wilcze Jagody";
    }
}
