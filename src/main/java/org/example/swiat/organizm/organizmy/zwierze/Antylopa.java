package org.example.swiat.organizm.organizmy.zwierze;

import org.example.hex.swiat.SwiatHex;
import org.example.panel.MainPanel;
//import sun.applet.Main;
import org.example.swiat.organizm.Organizm;
import org.example.swiat.organizm.organizmy.Zwierze;

import java.util.Random;

public class Antylopa extends Zwierze {

    public Antylopa(MainPanel swiat){
        sila = 4;
        inicjatywa = 4;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        polozenie = randomizePolozenie();
        rysowanie();
    }

    public Antylopa(MainPanel swiat, int x, int y){
        sila = 4;
        inicjatywa = 4;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        polozenie[0] = x; polozenie[1] = y;
        rysowanie();
    }

    @Override
    public void akcja() {
        if(!swiat.isCellInGameMapAroundCoordinate(polozenie[0],polozenie[1], 2)) {lifeTime++; return;}
        Random random = new Random();
        int kierunek, to_x, to_y;
        int kolizjaResult = 1;
        do {
            kierunek = random.nextInt(8);
            switch (kierunek) {
                case 0: { to_x = this.polozenie[0] - 2; to_y = this.polozenie[1] - 2; break; }
                case 1: { to_x = this.polozenie[0]; to_y = this.polozenie[1] - 2; break; }
                case 2: { to_x = this.polozenie[0] + 2; to_y = this.polozenie[1] - 2; break; }
                case 3: { to_x = this.polozenie[0] + 2; to_y = this.polozenie[1]; break; }
                case 4: { to_x = this.polozenie[0] + 2; to_y = this.polozenie[1] + 2; break; }
                case 5: { to_x = this.polozenie[0]; to_y = this.polozenie[1] + 2; break; }
                case 6: { to_x = this.polozenie[0] - 2; to_y = this.polozenie[1] + 2; break; }
                case 7: { to_x = this.polozenie[0] - 2; to_y = this.polozenie[1]; break; }
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
                if(random.nextInt(2)==0 && swiat.isFreeCellAroundCoordinate(to_x, to_y, 1)){
                    uciecOdWalki(organizm, to_x, to_y);
                    kolizjaResult = 2;
                }else
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

        Random random = new Random();
        if(random.nextInt(2)==0){
            if(swiat.isFreeCellAroundCoordinate(polozenie[0], polozenie[1], 1)){
                uciecOdWalki(atakujacy, polozenie[0], polozenie[1]);
                return 1;
            }
        }
        if(czyOdbilAtak(atakujacy)){
            swiat.setToRemove(atakujacy);
            atakujacy.setAlive(false);
            swiat.setResults("W walce pomiędzy " + this.getTypeOfOrganizm() + " a " + atakujacy.getTypeOfOrganizm() + " zwyciężył " + this.getTypeOfOrganizm() + "<br>");
            return 0;
        }else{
            swiat.setToRemove(this);
            this.alive = false;
            swiat.setResults("W walce pomiędzy " + this.getTypeOfOrganizm() + " a " + atakujacy.getTypeOfOrganizm() + " zwyciężył " + atakujacy.getTypeOfOrganizm() + "<br>");
            return 1;
        }
    }

    @Override
    public void rysowanie() {
        swiat.setOnBoard(getPolozenie()[0], getPolozenie()[1], 5);
    }

    @Override
    public String getTypeOfOrganizm() {
        return "Antylopa";
    }

    public void uciecOdWalki(Organizm atakujacy, int x, int y){
        Random random = new Random();
        int kierunek, to_x, to_y;
        swiat.setResults(this.getTypeOfOrganizm() + " uciekła przed walką z " + atakujacy.getTypeOfOrganizm() + " na sąsiednie pole<br>" );
        do {
            kierunek = random.nextInt(8);
            switch (kierunek) {
                case 0: { to_x = x - 1; to_y = y - 1; break; }
                case 1: { to_x = x; to_y = y - 1; break; }
                case 2: { to_x = x + 1; to_y = y - 1; break; }
                case 3: { to_x = x + 1; to_y = y; break; }
                case 4: { to_x = x + 1; to_y = y + 1; break; }
                case 5: { to_x = x; to_y = y + 1; break; }
                case 6: { to_x = x - 1; to_y = y + 1; break; }
                case 7: { to_x = x - 1; to_y = y; break; }
                default: { to_x = x; to_y = y; break; }
            }
        } while (to_x < 0 || to_x >= swiat.GetSizeX() || to_y < 0 || to_y >= swiat.GetSizeY() || swiat.getFromBoard(to_x, to_y)!=0);
        swiat.setOnBoard(polozenie[0], polozenie[1], 0);
        polozenie[0] = to_x; polozenie[1] = to_y;
        rysowanie();
    }
}
