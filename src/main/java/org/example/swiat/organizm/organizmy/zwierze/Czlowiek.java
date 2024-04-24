package org.example.swiat.organizm.organizmy.zwierze;

import org.example.hex.swiat.SwiatHex;
import org.example.panel.MainPanel;
import org.example.swiat.organizm.Organizm;
import org.example.swiat.organizm.organizmy.Zwierze;

import java.util.Random;

public class Czlowiek extends Zwierze {

    private boolean umiejetnosc = false;
    private int timeToEndUmiejetnosc = 0;
    private int noOfToursToNextActivation=0;

    public Czlowiek(MainPanel swiat){
        sila = 5;
        inicjatywa = 4;
        lifeTime = 0;
        alive = true;
        this.swiat = swiat;
        swiat.setCzlowiek(this);
        polozenie = randomizePolozenie();
        rysowanie();
    }

    @Override
    public void akcja(){
        if(noOfToursToNextActivation!=0) noOfToursToNextActivation--;
        if(timeToEndUmiejetnosc!=0) timeToEndUmiejetnosc--;
        else umiejetnosc = false;
        int to_x, to_y, kolizjaResult=1;
        if(swiat.getKierunekCzlowieka()==1) {
            to_x = polozenie[0]; to_y = polozenie[1]-1;
        }else if(swiat.getKierunekCzlowieka()==3){
            to_x = polozenie[0] + 1; to_y = polozenie[1];
        }else if(swiat.getKierunekCzlowieka()==5){
            to_x = polozenie[0]; to_y = polozenie[1] + 1;
        }else if(swiat.getKierunekCzlowieka()==7){
            to_x = polozenie[0] - 1; to_y= polozenie[1];
        }else{
            lifeTime++;
            return;
        }
        if(to_x < 0 || to_x >=swiat.GetSizeX() || to_y < 0 || to_y>=swiat.GetSizeY()) return;

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
        swiat.setOnBoard(getPolozenie()[0], getPolozenie()[1], 6);
    }

    @Override
    public String getTypeOfOrganizm() {
        return "Człowiek";
    }

    public boolean getUmiejetnosc() {
        return umiejetnosc;
    }

    public void setUmiejetnosc(boolean umiejetnosc) {
        this.umiejetnosc = umiejetnosc;
    }

    public int getTimeToEndUmiejetnosc() {
        return timeToEndUmiejetnosc;
    }

    public int getNoOfToursToNextActivation() {
        return noOfToursToNextActivation;
    }

    public void setNoOfToursToNextActivation(int noOfToursToNextActivation) {
        this.noOfToursToNextActivation = noOfToursToNextActivation;
    }

    public void setTimeToEndUmiejetnosc(int timeToEndEmiejetnosc) {
        this.timeToEndUmiejetnosc = timeToEndEmiejetnosc;
    }
}
