package org.example.swiat.organizm.organizmy.zwierze;

import org.example.hex.swiat.SwiatHex;
import org.example.panel.MainPanel;
import org.example.swiat.organizm.Organizm;
import org.example.swiat.organizm.organizmy.Zwierze;

import java.util.Random;

public class Zolw extends Zwierze {

    public Zolw(MainPanel swiat){
        sila = 2;
        inicjatywa = 1;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        polozenie = randomizePolozenie();
        rysowanie();
    }

    public Zolw(MainPanel swiat, int x, int y){
        sila = 2;
        inicjatywa = 1;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        polozenie[0] = x; polozenie[1] = y;
        rysowanie();
    }

    @Override
    public void akcja() {
        Random random = new Random();
        if(random.nextInt(4)!=0) return;
        int kierunek, to_x, to_y;
        int kolizjaResult = 1;
        do {
            kierunek = random.nextInt(8);
            switch (kierunek) {
                case 0: { to_x = this.polozenie[0] - 1; to_y = this.polozenie[1] - 1; break; }
                case 1: { to_x = this.polozenie[0]; to_y = this.polozenie[1] - 1; break; }
                case 2: { to_x = this.polozenie[0] + 1; to_y = this.polozenie[1] - 1; break; }
                case 3: { to_x = this.polozenie[0] + 1; to_y = this.polozenie[1]; break; }
                case 4: { to_x = this.polozenie[0] + 1; to_y = this.polozenie[1] + 1; break; }
                case 5: { to_x = this.polozenie[0]; to_y = this.polozenie[1] + 1; break; }
                case 6: { to_x = this.polozenie[0] - 1; to_y = this.polozenie[1] + 1; break; }
                case 7: { to_x = this.polozenie[0] - 1; to_y = this.polozenie[1]; break; }
                default: { to_x = this.polozenie[0]; to_y = this.polozenie[1]; break; }
            }
        } while (to_x < 0 || to_x >= swiat.GetSizeX() || to_y < 0 || to_y >= swiat.GetSizeY());

        if(swiat.getFromBoard(to_x, to_y)!=0){
            Organizm organizm = swiat.whatOrganizmOnCoordinate(to_x, to_y);
            if(organizm instanceof Czlowiek){
                Czlowiek orgCzlowiek = (Czlowiek) organizm;
                if(orgCzlowiek.getUmiejetnosc()){
                    this.swiat.setOnBoard(polozenie[0], polozenie[1], 0);
                    int[] nowePolozenie = przesunNaSasiedniePoleOd(to_x, to_y);
                    to_x=nowePolozenie[0]; to_y=nowePolozenie[1];
                    swiat.setResults(this.getTypeOfOrganizm() + " został przestraszony przez " + organizm.getTypeOfOrganizm() + " i przemieścił się na sąsiednie pole<br>");
                }
            }
        }

        if(swiat.getFromBoard(to_x, to_y)!=0){
            Organizm organizm = swiat.whatOrganizmOnCoordinate(to_x, to_y);
            if(organizm!=null && organizm.getAlive()){
                kolizjaResult = organizm.kolizja(this);
            }
        }
        this.swiat.setOnBoard(polozenie[0], polozenie[1], 0);
        if(kolizjaResult==0 ) return;
        else if(kolizjaResult==1){
            this.polozenie[0] = to_x;
            this.polozenie[1] = to_y;
            lifeTime++;
        }else if(kolizjaResult==2){
            lifeTime++;
        }
        rysowanie();
    }

    @Override
    public int kolizja(Organizm atakujacy) {
        if(this.getTypeOfOrganizm().equals(atakujacy.getTypeOfOrganizm())){
            nowyOrganizm(atakujacy);
            return 2;
        }

        if(atakujacy.getSila() < 5){
            swiat.setResults("Żółw odbil atak " + atakujacy.getTypeOfOrganizm() + " i atakujący wrócił na poprzednie pole<br>");
            return 2;
        }
        if(this.sila < atakujacy.getSila()){
            swiat.setToRemove(this);
            this.alive = false;
            swiat.setResults("W walce pomiędzy " + this.getTypeOfOrganizm() + " a " + atakujacy.getTypeOfOrganizm() + " zwyciężył " + atakujacy.getTypeOfOrganizm() + "<br>");
            return 1;
        }else if(this.sila > atakujacy.getSila()){
            swiat.setToRemove(atakujacy);
            atakujacy.setAlive(false);
            swiat.setResults("W walce pomiędzy " + this.getTypeOfOrganizm() + " a " + atakujacy.getTypeOfOrganizm() + " zwyciężył " + this.getTypeOfOrganizm() + "<br>");
            return 0;
        }else{
            swiat.setToRemove(this);
            this.setAlive(false);
            swiat.setResults("W walce pomiędzy " + this.getTypeOfOrganizm() + " a " + atakujacy.getTypeOfOrganizm() + " zwyciężył " + atakujacy.getTypeOfOrganizm() + "<br>");
            return 1;
        }
    }

    @Override
    public void rysowanie() {
        swiat.setOnBoard(getPolozenie()[0], getPolozenie()[1], 4);
    }

    @Override
    public String getTypeOfOrganizm() {
        return "Żółw";
    }
}
