package peter.graphing.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import peter.graphing.Function;
import peter.graphing.Graph;

public class GraphPanel extends JPanel {

    private int[][] xcoords;
    private int[][] ycoords;
    private int xAxis, yAxis, height, width;
    private String oldstr = "";
    private Graph graph;

    public GraphPanel(Graph g) {

        graph = g;
        ArrayList[] coords = g.getAllCoords();

        height = g.getHeight();
        width = g.getWidth();
        xAxis = g.getAxes()[0];
        yAxis = g.getAxes()[1];

        this.setSize(width, height);
        this.setMaximumSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));
        this.setBackground(Color.WHITE);
        
        xcoords = new int[coords.length][];
        ycoords = new int[coords.length][];

        for (int i = 0; i < coords.length; i++) {
            int c = 0;

            for (int j = 0; j < coords[i].size(); j++) {
                if (((Integer) coords[i].get(j)).intValue() != -1) {
                    c++;
                }
            }
            xcoords[i] = new int[c];
            ycoords[i] = new int[c];
        }

        for (int j = 0; j < coords.length; j++) {
            for (int i = 0, t = 0; i < coords[j].size(); i++) {
                if ((((Integer) coords[j].get(i)).intValue() != -1)) {
                    ycoords[j][t] = ((Integer) coords[j].get(i)).intValue();
                    xcoords[j][t] = i;
                    t++;
                }
            }
        }

    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getXAxis() {
        return xAxis;
    }

    public void setXAxis(int axis) {
        xAxis = axis;
    }

    public int[][] getXcoords() {
        return xcoords;
    }

    public void setXcoords(int[][] xcoords) {
        this.xcoords = xcoords;
    }

    public int getYAxis() {
        return yAxis;
    }

    public void setYAxis(int axis) {
        yAxis = axis;
    }

    public int[][] getYcoords() {
        return ycoords;
    }

    public void setYcoords(int[][] ycoords) {
        this.ycoords = ycoords;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        g.drawLine(0, xAxis, width, xAxis);
        g.drawLine(yAxis, 0, yAxis, height);
        g.setColor(Color.BLACK);

        for (int i = 0; i < xcoords.length; i++) {
            g.drawPolyline(xcoords[i], ycoords[i], xcoords[i].length);
        }

        g.drawRect(0, 0, width, height);
        g.drawRect(1, 1, width - 1, height - 1);
    }

    public void drawCoords(int x, Function func) {
        Graphics g = this.getGraphics();
        g.setColor(Color.WHITE);
        g.drawString(oldstr, width - 100, height - 5);
        g.setColor(Color.BLACK);
        double[] pts = graph.getPoint(x, 0);
        graph.setFunction(func);
        pts[1] = graph.getFunction().eval(pts[0]);
        String str = peter.Math.round(pts[0], 3) + ", " + peter.Math.round(pts[1], 3);
        oldstr = str;
        g.drawString(str, width - 100, height - 5);
    }

    public void drawLocation(int x, Function func) {
        Graphics g = this.getGraphics();
        int[] coord = new int[2];
        double xpt = graph.getPoint(x, 0)[0];
        coord[0] = x;
        graph.setFunction(func);
        coord[1] = graph.getCoord(0, graph.getFunction().eval(xpt))[1];
        paintComponent(g);
        drawCoords(x, func);
        g.drawLine(coord[0], coord[1] + 5, coord[0], coord[1] - 5);
        g.drawLine(coord[0] - 5, coord[1], coord[0] + 5, coord[1]);
    }
}