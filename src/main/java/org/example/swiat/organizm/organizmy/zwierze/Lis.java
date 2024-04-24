package org.example.swiat.organizm.organizmy.zwierze;

import org.example.hex.swiat.SwiatHex;
import org.example.panel.MainPanel;
import org.example.swiat.organizm.Organizm;
import org.example.swiat.organizm.organizmy.Zwierze;

import java.util.Random;

public class Lis extends Zwierze {

    public Lis(MainPanel swiat){
        sila = 3;
        inicjatywa = 7;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        polozenie = randomizePolozenie();
        rysowanie();
    }

    public Lis(MainPanel swiat, int x, int y){
        sila = 3;
        inicjatywa = 7;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        polozenie[0] = x; polozenie[1] = y;
        rysowanie();
    }

    @Override
    public void akcja(){
        if(!isFreeCellAroundCoordinateForLis()) {lifeTime++; return;}
        Random random = new Random();
        int kierunek, to_x, to_y, kolizjaResult=1;
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
        } while (to_x < 0 || to_x >= swiat.GetSizeX() || to_y < 0 || to_y >= swiat.GetSizeY() || (swiat.getFromBoard(to_x, to_y)!=0 && swiat.whatOrganizmOnCoordinate(to_x, to_y).getSila() > sila));

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
    public void rysowanie() {
        swiat.setOnBoard(getPolozenie()[0], getPolozenie()[1], 3);
    }

    @Override
    public String getTypeOfOrganizm() {
        return "Lis";
    }

    public boolean isFreeCellAroundCoordinateForLis(){
        if(polozenie[0]-1>=0 && polozenie[0]-1<swiat.GetSizeX() && polozenie[1]-1>=0 && polozenie[1]-1< swiat.GetSizeY()) {
            if(swiat.getFromBoard(polozenie[0]-1, polozenie[1]-1)==0 || swiat.whatOrganizmOnCoordinate(polozenie[0]-1, polozenie[1]-1).getSila() < sila) return true;
        }
        if(polozenie[0]>=0 && polozenie[0]<swiat.GetSizeX() && polozenie[1]-1>=0 && polozenie[1]-1<swiat.GetSizeY()) {
            if(swiat.getFromBoard(polozenie[0], polozenie[1]-1)==0 || swiat.whatOrganizmOnCoordinate(polozenie[0], polozenie[1]-1).getSila() < sila) return true;
        }
        if(polozenie[0]+1>=0 && polozenie[0]+1<swiat.GetSizeX() && polozenie[1]-1>=0 && polozenie[1]-1<swiat.GetSizeY()) {
            if(swiat.getFromBoard(polozenie[0]+1, polozenie[1]-1)==0 || swiat.whatOrganizmOnCoordinate(polozenie[0]+1, polozenie[1]-1).getSila() < sila) return true;
        }
        if(polozenie[0]+1>=0 && polozenie[0]+1<swiat.GetSizeX() && polozenie[1]>=0 && polozenie[1]<swiat.GetSizeY()) {
            if(swiat.getFromBoard(polozenie[0]+1, polozenie[1])==0 || swiat.whatOrganizmOnCoordinate(polozenie[0]+1, polozenie[1]).getSila() < sila) return true;
        }
        if(polozenie[0]+1>=0 && polozenie[0]+1<swiat.GetSizeX() && polozenie[1]+1>=0 && polozenie[1]+1<swiat.GetSizeY()) {
            if(swiat.getFromBoard(polozenie[0]+1, polozenie[1]+1)==0 || swiat.whatOrganizmOnCoordinate(polozenie[0]+1, polozenie[1]+1).getSila() < sila) return true;
        }
        if(polozenie[0]>=0 && polozenie[0]<swiat.GetSizeX() && polozenie[1]+1>=0 && polozenie[1]+1<swiat.GetSizeY()) {
            if(swiat.getFromBoard(polozenie[0], polozenie[1]+1)==0 || swiat.whatOrganizmOnCoordinate(polozenie[0], polozenie[1]+1).getSila() < sila) return true;
        }
        if(polozenie[0]-1>=0 && polozenie[0]-1<swiat.GetSizeX() && polozenie[1]+1>=0 && polozenie[1]+1<swiat.GetSizeY()) {
            if(swiat.getFromBoard(polozenie[0]-1, polozenie[1]+1)==0 || swiat.whatOrganizmOnCoordinate(polozenie[0]-1, polozenie[1]+1).getSila() < sila) return true;
        }
        if(polozenie[0]-1>=0 && polozenie[0]-1<swiat.GetSizeX() && polozenie[1]>=0 && polozenie[1]<swiat.GetSizeY()) {
            if(swiat.getFromBoard(polozenie[0]-1, polozenie[1])==0 || swiat.whatOrganizmOnCoordinate(polozenie[0]-1, polozenie[1]).getSila() < sila) return true;
        }
        return false;
    }
}
