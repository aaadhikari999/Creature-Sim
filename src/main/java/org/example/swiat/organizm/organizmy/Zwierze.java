package org.example.swiat.organizm.organizmy;

import org.example.swiat.organizm.Organizm;
import org.example.swiat.organizm.organizmy.zwierze.*;

import java.util.Random;

public abstract class Zwierze extends Organizm {
    @Override
    public void akcja() {
        Random random = new Random();
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
        if(czyOdbilAtak(atakujacy)){
            swiat.setToRemove(atakujacy);
            atakujacy.setAlive(false);
            if(atakujacy instanceof Czlowiek) swiat.setCzlowiek(null);
            swiat.setResults("W walce pomiędzy " + this.getTypeOfOrganizm() + " a " + atakujacy.getTypeOfOrganizm() + " zwyciężył " + this.getTypeOfOrganizm() + "<br>");
            return 0;
        }else{
            swiat.setToRemove(this);
            this.alive = false;
            if(this instanceof Czlowiek) swiat.setCzlowiek(null);
            swiat.setResults("W walce pomiędzy " + this.getTypeOfOrganizm() + " a " + atakujacy.getTypeOfOrganizm() + " zwyciężył " + atakujacy.getTypeOfOrganizm() + "<br>");
            return 1;
        }
    }

    public void nowyOrganizm(Organizm atakujacy){
        if(!swiat.isFreeCellAroundCoordinate(polozenie[0], polozenie[1], 1)) return;
        Random random = new Random();
        int kierunek, to_x, to_y;
        do{
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
        }while(to_x < 0 || to_x >= swiat.GetSizeX() || to_y < 0 || to_y >= swiat.GetSizeY() || swiat.getFromBoard(to_x, to_y)!=0);
        Organizm nowy;
        if(this.getTypeOfOrganizm().equals("Antylopa")){
            nowy = new Antylopa(swiat, to_x, to_y);
        }else if(this.getTypeOfOrganizm().equals("Lis")){
            nowy = new Lis(swiat, to_x, to_y);
        }else if(this.getTypeOfOrganizm().equals("Owca")){
            nowy = new Owca(swiat, to_x, to_y);
        }else if(this.getTypeOfOrganizm().equals("Wilk")){
            nowy = new Wilk(swiat, to_x, to_y);
        }else if(this.getTypeOfOrganizm().equals("Żółw")){
            nowy = new Zolw(swiat, to_x, to_y);
        }else nowy = new Owca(swiat, to_x, to_y);

        swiat.addOrganizm(nowy);
        nowy.rysowanie();
        swiat.setResults("Po spotkaniu " + this.getTypeOfOrganizm() + " z " + atakujacy.getTypeOfOrganizm() + " zostal stworzony nowy organizm<br>");
    }

    public int[] przesunNaSasiedniePoleOd(int x, int y){
        Random random = new Random();
        int kierunek, to_x, to_y;
        do{
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
        }while(to_x < 0 || to_x >= swiat.GetSizeX() || to_y < 0 || to_y >= swiat.GetSizeY());
        int[] polozenie = {to_x, to_y};
        return polozenie;
    }
}
