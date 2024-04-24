package org.example.hex;

import org.example.frame.SimulatorFrame;
import org.example.panel.MainPanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PanelHex extends MainPanel {
    private SimulatorFrame simulatorFrame;

    private BufferedImage image;

    protected int[][] board;
    private Polygon[][] squares;

    private final Color[] colors={Color.WHITE, Color.DARK_GRAY, Color.LIGHT_GRAY, Color.ORANGE, Color.GREEN, Color.PINK, Color.RED, Color.cyan, Color.YELLOW, Color.MAGENTA, Color.BLACK, Color.BLUE};
    protected final int x_size;
    protected final int y_size;
    protected String results = new String();
    private int width=0;
    private int gameHeight = 0;
    private int height=0;

    public PanelHex(int x_size, int y_size, int infPanelHeight, SimulatorFrame frame){
        simulatorFrame = frame;

        width = x_size*42+95; gameHeight = y_size*47+95+(x_size*23);
        height = gameHeight;

        this.x_size = x_size;
        this.y_size = y_size;
        this.setBounds(0, infPanelHeight, width, height);

        board=new int[y_size][x_size];

        squares=new Polygon[y_size][x_size];

        image=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        drawBoard();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }

    protected void drawBoard() {
        Graphics2D graph = ((BufferedImage)image).createGraphics();
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graph.setColor(new Color(181, 201, 91));
        graph.fillRect(0, 0, width, height);

        for (int y=0; y<y_size; y++) {
            for (int x=x_size-1; x>=0; x--) {
                graph.setColor(getColor(x, y));
                squares[y][x]=drawHex(25.0, x*42+70, y*47+70+(23*(x_size-1-x)));
                graph.fillPolygon(squares[y][x]);
            }
        }

        graph.dispose();
        paintImmediately(getBounds());
    }

    private Polygon drawHex(double rad, double centerX, double centerY) {
        Polygon p=new Polygon();

        double arc=(Math.PI*2)/6;

        for (int i=0; i<=6; i++) {
            p.addPoint((int)Math.round(centerX+rad*Math.cos(arc*i)), (int)Math.round(centerY+rad*Math.sin(arc*i)));
        }

        return p;
    }

    private Color getColor(int x, int y) {
        return colors[board[y][x]];
    }
}
