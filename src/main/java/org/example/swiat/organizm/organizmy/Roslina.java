package org.example.swiat.organizm.organizmy;

import org.example.swiat.organizm.Organizm;
import org.example.swiat.organizm.organizmy.rosliny.*;

import java.util.Random;

public abstract class Roslina extends Organizm {
    @Override
    public void akcja() {
        Random random = new Random();
        if(random.nextInt(3)!=0) return;
        if(!swiat.isFreeCellAroundCoordinate(polozenie[0], polozenie[1], 1)) return;
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
