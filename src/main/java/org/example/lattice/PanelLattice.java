package org.example.lattice;

import org.example.frame.SimulatorFrame;
import org.example.panel.MainPanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PanelLattice extends MainPanel {
    private SimulatorFrame simulatorFrame;

    private Image image;

    protected int[][] board;

    private final Color[] colors={Color.WHITE, Color.DARK_GRAY, Color.LIGHT_GRAY, Color.ORANGE, Color.GREEN, Color.PINK, Color.RED, Color.cyan, Color.YELLOW, Color.MAGENTA, Color.BLACK, Color.BLUE};
    protected final int x_size;
    protected final int y_size;
    protected String results = new String();
    private int width=0;
    private int gameHeight = 0;
    private int height=0;
    private int infPanelHeight;

    private int turn=1;
    private int colour=1;

    public PanelLattice(int x_size, int y_size, int infPanelHeight, SimulatorFrame frame){
        simulatorFrame = frame;

        width = x_size*20; gameHeight = 42*2 + y_size*20;
        height = gameHeight;

        this.x_size = x_size;
        this.y_size = y_size;
        this.setBounds(0, infPanelHeight, width, height);

        board=new int[y_size][x_size];

        image=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        drawBoard();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }

    protected void drawBoard() {
        Graphics2D graph= ((BufferedImage)image).createGraphics();
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graph.setColor(new Color(181, 201, 91));
        graph.fillRect(0, 0, width, height);


        for (int y=0; y<y_size; y++) {
            for (int x=0; x<x_size; x++) {
                Color color = getColor(x,y);
                graph.setColor(color);
                graph.fillRect(x*20+1, 42 + y*20+1, 18, 18);
            }
        }

        graph.dispose();
        paintImmediately(getBounds());
    }

    private Color getColor(int x, int y) {
        return colors[board[y][x]];
    }
}
