package org.example.panel;

import org.example.swiat.organizm.Organizm;

import javax.swing.*;
import java.util.ArrayList;

public abstract class MainPanel extends JPanel {
    public abstract void WykonajTure();
    public abstract String getResults();
    public abstract  int GetSizeX();
    public abstract  int GetSizeY();
    public abstract ArrayList<Organizm> getOrganizmy();
    public abstract int getFromBoard(int x, int y);
    public abstract Organizm whatOrganizmOnCoordinate(int x, int y);
    public abstract Organizm getCzlowiek();
    public abstract int getKierunekCzlowieka();
    public abstract boolean getCanSetKierunekCzlowieka();
    public abstract void setOnBoard(int x, int y, int a);
    public abstract void setToRemove(Organizm organizm);
    public abstract void setCzlowiek(Organizm organizm);
    public abstract void setResults(String string);
    public abstract void setKierunekCzlowieka(int kierunek);
    public abstract void setCanSetKierunekCzlowieka(boolean can);
    public abstract boolean isFreeCellAroundCoordinate(int x, int y, int distance);
    public abstract boolean isCellInGameMapAroundCoordinate(int x, int y, int distance);
    public abstract void addOrganizm(Organizm organizm);
    public abstract ArrayList<Organizm> getAllZwierzetaAroundCoordinate(int x, int y);
    public abstract void clearResults();
    public abstract void rysujSwiat();
}
