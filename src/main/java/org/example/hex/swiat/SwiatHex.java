package org.example.hex.swiat;

import org.example.frame.SimulatorFrame;
import org.example.hex.PanelHex;
import org.example.swiat.organizm.Organizm;
import org.example.swiat.organizm.organizmy.rosliny.*;
import org.example.swiat.organizm.organizmy.zwierze.*;
import java.util.ArrayList;

public class SwiatHex extends PanelHex {

    private ArrayList<Organizm> organizmy;
    private ArrayList<Organizm> toRemove;
    private Organizm czlowiek;
    private int kierunekCzlowieka=0;
    private boolean canSetKierunekCzlowieka = false;

    public SwiatHex(int x_size, int y_size, int infPanelHeight, SimulatorFrame frame, boolean newGame){
        super(x_size, y_size, infPanelHeight, frame);

        organizmy = new ArrayList<>();
        toRemove = new ArrayList<>();
        results = "";

        if(newGame){
            generowanieSwiata();
        }
        rysujSwiat();
    }

    @Override
    public void WykonajTure(){
        results = "<html>";
        sortOrganizmy();
        int numberOfOrganizms = organizmy.size();
        for( int i = 0; i < numberOfOrganizms; i++){
            Organizm current = organizmy.get(i);
            if(current.getAlive())
                current.akcja();
        }
        for(Organizm i: toRemove){
            organizmy.remove(i);
        }
        toRemove.clear();
        results += "</html>";
        rysujSwiat();
    }

    @Override
    public void rysujSwiat(){
        drawBoard();
    }

    @Override
    final public int GetSizeX(){
        return x_size;
    }
    @Override
    final public int GetSizeY() {return y_size;}
    @Override
    final public String getResults(){ return results; }
    @Override
    public int getFromBoard(int x, int y){
        return board[y][x];
    }
    @Override
    public ArrayList<Organizm> getOrganizmy() {
        return organizmy;
    }
    @Override
    public Organizm getCzlowiek(){ return czlowiek; }
    @Override
    public int getKierunekCzlowieka(){
        return kierunekCzlowieka;
    }
    @Override
    public boolean getCanSetKierunekCzlowieka() {
        return canSetKierunekCzlowieka;
    }

    @Override
    public void addOrganizm(Organizm organizm){
        organizmy.add(organizmy.size(), organizm);
    }

    @Override
    public ArrayList<Organizm> getAllZwierzetaAroundCoordinate(int x, int y) {
        ArrayList<Organizm> organizms = new ArrayList<>();
        if(getFromBoard(x-1, y-1)>0 && getFromBoard(x-1, y-1)<7) organizms.add(whatOrganizmOnCoordinate(x-1, y-1));
        if(getFromBoard(x,y-1)>0 && getFromBoard(x, y-1)<7) organizms.add(whatOrganizmOnCoordinate(x, y-1));
        if(getFromBoard(x+1, y-1)>0 & getFromBoard(x+1, y-1)<7) organizms.add(whatOrganizmOnCoordinate(x+1, y-1));
        if(getFromBoard(x+1, y)>0 && getFromBoard(x+1, y)<7) organizms.add(whatOrganizmOnCoordinate(x+1, y));
        if(getFromBoard(x+1, y+1)>0 && getFromBoard(x+1, y+1)<7) organizms.add(whatOrganizmOnCoordinate(x+1, y+1));
        if(getFromBoard(x, y+1)>0 && getFromBoard(x, y+1)<7) organizms.add(whatOrganizmOnCoordinate(x, y+1));
        if(getFromBoard(x-1, y+1)>0 && getFromBoard(x-1, y+1)<7) organizms.add(whatOrganizmOnCoordinate(x-1, y+1));
        if(getFromBoard(x-1, y)>0 && getFromBoard(x-1, y)<7) organizms.add(whatOrganizmOnCoordinate(x-1, y));
        return organizms;
    }

    @Override
    public void setOnBoard(int x, int y, int a){
        board[y][x] = a;
    }
    @Override
    public void setResults(String string) {results += string;}
    @Override
    public void setToRemove(Organizm organizm) {
        this.toRemove.add(organizm);
    }
    @Override
    public void setCzlowiek(Organizm organizm){
        this.czlowiek = organizm;
    }
    @Override
    public void setKierunekCzlowieka(int kierunek){
        if(canSetKierunekCzlowieka) kierunekCzlowieka = kierunek;
    }
    @Override
    public void setCanSetKierunekCzlowieka(boolean canSetKierunekCzlowieka) {
        this.canSetKierunekCzlowieka = canSetKierunekCzlowieka;
    }

    @Override
    public Organizm whatOrganizmOnCoordinate(int x, int y){
        for(Organizm i : organizmy){
            if(i.getPolozenie()[0]==x && i.getPolozenie()[1]==y && i.getAlive()){
                return i;
            }
        }
        return null;
    }

    public void sortOrganizmy(){
        for(int i = 1; i < organizmy.size(); i++){
            Organizm x = organizmy.get(i);
            int j = i;
            while(j>0 && (x.getInicjatywa() > organizmy.get(j-1).getInicjatywa() || (x.getInicjatywa() == organizmy.get(j-1).getInicjatywa() && x.getLifeTime() >= organizmy.get(j-1).getLifeTime()))){
                organizmy.set(j, organizmy.get(j-1));
                j = j-1;
            }
            organizmy.set(j, x);
        }
    }

    public boolean isFreeCell(){
        for(int[] i:board){
            for(int j: i){
                if (j==0) return true;
            }
        }
        return false;
    }

    @Override
    public boolean isFreeCellAroundCoordinate(int x, int y, int distance){
        if(x-distance>=0 && x-distance<x_size && y-distance>=0 && y-distance<y_size && getFromBoard(x-distance,y-distance)==0) return true;
        else if(x>=0 && x<x_size && y-distance>=0 && y-distance<y_size && getFromBoard(x,y-distance)==0) return true;
        else if(x+distance>=0 && x+distance<x_size && y-distance>=0 && y-distance<y_size && getFromBoard(x+distance,y-distance)==0) return true;
        else if(x+distance>=0 && x+distance<x_size && y>=0 && y<y_size && getFromBoard(x+distance,y)==0) return true;
        else if(x+distance>=0 && x+distance<x_size && y+distance>=0 && y+distance<y_size && getFromBoard(x+distance,y+distance)==0) return true;
        else if(x>=0 && x<x_size && y+distance>=0 && y+distance<y_size && getFromBoard(x,y+distance)==0) return true;
        else if(x-distance>=0 && x-distance<x_size && y+distance>=0 && y+distance<y_size && getFromBoard(x-distance,y+distance)==0) return true;
        else if(x-distance>=0 && x-distance<x_size && y>=0 && y<y_size && getFromBoard(x-distance,y)==0) return true;
        else return false;
    }

    @Override
    public boolean isCellInGameMapAroundCoordinate(int x, int y, int distance){
        if(x-distance>=0 && x-distance<x_size && y-distance>=0 && y-distance<y_size) return true;
        else if(x>=0 && x<x_size && y-distance>=0 && y-distance<y_size) return true;
        else if(x+distance>=0 && x+distance<x_size && y-distance>=0 && y-distance<y_size) return true;
        else if(x+distance>=0 && x+distance<x_size && y>=0 && y<y_size) return true;
        else if(x+distance>=0 && x+distance<x_size && y+distance>=0 && y+distance<y_size) return true;
        else if(x>=0 && x<x_size && y+distance>=0 && y+distance<y_size) return true;
        else if(x-distance>=0 && x-distance<x_size && y+distance>=0 && y+distance<y_size) return true;
        else if(x-distance>=0 && x-distance<x_size && y>=0 && y<y_size) return true;
        else return false;
    }

    @Override
    public void clearResults(){ results = ""; }

    public void generowanieSwiata(){
        if(isFreeCell())
            addOrganizm(new Czlowiek(this));
        if (isFreeCell())
            addOrganizm(new Lis(this));
        if(isFreeCell())
            addOrganizm(new Antylopa(this));
        if(isFreeCell())
            addOrganizm(new Wilcze_Jagody(this));
        if(isFreeCell())
            addOrganizm(new Trawa(this));
        if(isFreeCell())
            addOrganizm(new Mlecz(this));
        if (isFreeCell())
            addOrganizm(new Guarana(this));
        if (isFreeCell())
            addOrganizm(new Barszcz_Sosnowskiego(this));
        if(isFreeCell())
            addOrganizm(new Owca(this));
        if(isFreeCell())
            addOrganizm(new Owca(this));
        if (isFreeCell())
            addOrganizm(new Zolw(this));
        if(isFreeCell())
            addOrganizm(new Wilk(this));
        if(isFreeCell())
            addOrganizm(new Wilk(this));
    }

}
