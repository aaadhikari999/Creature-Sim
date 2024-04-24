package org.example.swiat.organizm.organizmy.rosliny;

import org.example.hex.swiat.SwiatHex;
import org.example.panel.MainPanel;
import org.example.swiat.organizm.Organizm;
import org.example.swiat.organizm.organizmy.Roslina;

import java.util.Random;

public class Mlecz extends Roslina {

    public Mlecz(MainPanel swiat){
        sila = 0;
        inicjatywa = 0;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        polozenie = randomizePolozenie();
        rysowanie();
    }

    public Mlecz(MainPanel swiat, int x, int y){
        sila = 0;
        inicjatywa = 0;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        polozenie[0] = x; polozenie[1] = y;
        rysowanie();
    }

    @Override
    public void akcja() {
        for(int i = 0; i < 3; i++){
            Random random = new Random();
            if(random.nextInt(3)!=0) continue;
            if(!swiat.isFreeCellAroundCoordinate(polozenie[0], polozenie[1], 1)) continue;
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
            if(this.getTypeOfOrganizm().equals("Barszcz Sosnowskiego")){
                nowy = new Barszcz_Sosnowskiego(swiat, to_x, to_y);
            }else if(this.getTypeOfOrganizm().equals("Guarana")){
                nowy = new Guarana(swiat, to_x, to_y);
            }else if(this.getTypeOfOrganizm().equals("Mlecz")){
                nowy = new Mlecz(swiat, to_x, to_y);
            }else if(this.getTypeOfOrganizm().equals("Trawa")){
                nowy = new Trawa(swiat, to_x, to_y);
            }else if(this.getTypeOfOrganizm().equals("Wilcze Jagody")){
                nowy = new Wilcze_Jagody(swiat, to_x, to_y);
            }else nowy = new Trawa(swiat, to_x, to_y);

            swiat.addOrganizm(nowy);
            nowy.rysowanie();
            swiat.setResults("Wyrosła nowa roślina " + nowy.getTypeOfOrganizm() + "<br>");
        }
    }

    @Override
    public int kolizja(Organizm atakujacy) {
        swiat.setToRemove(this);
        this.setAlive(false);
        swiat.setResults(atakujacy.getTypeOfOrganizm() + " zjadl " + this.getTypeOfOrganizm() + "<br>");
        return 1;
    }

    @Override
    public void rysowanie() {
        swiat.setOnBoard(getPolozenie()[0], getPolozenie()[1], 8);
    }

    @Override
    public String getTypeOfOrganizm() {
        return "Mlecz";
    }
}
