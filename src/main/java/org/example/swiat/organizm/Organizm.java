package org.example.swiat.organizm;

import org.example.panel.MainPanel;

import java.util.Random;

public abstract class Organizm {
    protected int sila;
    protected int inicjatywa;
    protected int lifeTime=0;
    protected boolean alive;
    protected int[] polozenie = new int[2];
    protected MainPanel swiat;

    public abstract void akcja();
    public abstract int kolizja(Organizm atakujacy);
    public abstract void rysowanie();

    public boolean czyOdbilAtak(Organizm atakujacy) {
        if(sila > atakujacy.getSila()) return true;
        else return false;
    }

    protected int[] randomizePolozenie(){
        Random random = new Random();
        int[] nowePolozenie = new int[2];
        do{
            nowePolozenie[0] = random.nextInt(swiat.GetSizeX());
            nowePolozenie[1] = random.nextInt((swiat.GetSizeY()));
        }while(swiat.getFromBoard(nowePolozenie[0], nowePolozenie[1])!=0);
        return nowePolozenie;
    }

    public final int[] getPolozenie() {
        return polozenie;
    }
    public final int getSila(){
        return sila;
    }

    public void setSila(int sila) {
        this.sila = sila;
    }

    public final int getInicjatywa(){
        return inicjatywa;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public abstract String getTypeOfOrganizm();

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    public void setInicjatywa(int inicjatywa) {
        this.inicjatywa = inicjatywa;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public void setPolozenie(int x, int y) {
        this.polozenie[0] = x;
        this.polozenie[1] = y;
    }

    public final boolean getAlive(){
        return alive;
    }

}
