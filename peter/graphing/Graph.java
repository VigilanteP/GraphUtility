/*
 * Created on Nov 14, 2004
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package peter.graphing;

import java.util.ArrayList;

/**
 * @author Programming
 * 
 * 
 * Preferences - Java - Code Style - Code Templates
 */
public class Graph {

    private Function[] function;
    private Window window;
    private int width;
    private int height;
    private double xinc;
    private String[] func;
    private ArrayList vals;
    private ArrayList coords;

    public Graph() {
        this(new Function(""), 0, 0, new Window());
    }

    public String getFunc(int i) {
        return func[i];
    }

    public String getFunc() {
        return func[0];
    }

    public void setFunc(String func) {
        this.func[0] = func;
    }

    public void setFunc(String func, int i) {
        this.func[i] = func;
    }

    public void setCoords(ArrayList coords, int i) {
        this.coords.set(i, coords);
    }

    public void setVals(double[][] vals, int i) {
        this.vals.set(i, vals);
    }

    public Graph(Function[] f, int w, int h, Window win) {
        function = f;
        vals = new ArrayList(f.length);
        coords = new ArrayList(f.length);
        func = new String[f.length];
        setWindow(win);
        setWidth(w);
        setHeight(h);
        System.out.println((win.getXmax() - win.getXmin()) / width);
        setXinc((win.getXmax() - win.getXmin()) / width);
        this.getVals();
        this.generateCoords();
    }

    public Graph(Function f, int w, int h, Window win) {
        this(peter.Math.array(f), w, h, win);
    }

    public ArrayList getCoords() {
        return (ArrayList) coords.get(0);
    }
    
    public ArrayList[] getAllCoords() {
        Object[] tempobj = coords.toArray();
        ArrayList[] templist = new ArrayList[tempobj.length];
        for(int i = 0; i<tempobj.length; i++) {
            templist[i] = (ArrayList)tempobj[i];
        }
        
        return templist;
    }

    public ArrayList getCoords(int i) {
        return (ArrayList) coords.get(i);
    }

    public Function getFunction() {
        return function[0];
    }

    public Function[] getFunctions() {
        return function;
    }

    public void setFunction(Function f) {
        function[0] = f;
    }

    public void setFunction(Function[] function) {
        this.function = function;
    }

    public void setFunction(Function function, int i) {
        this.function[i] = function;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window w) {
        this.window = w;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public double getXinc() {
        return xinc;
    }

    public void setWidth(int w) {
        width = w;
    }

    public void setHeight(int h) {
        height = h;
    }

    public void setXinc(double inc) {
        xinc = inc;
    }

    public int[] getAxes() {

        int[] axes = new int[2];

        if (window.getXmin() < 0 && window.getXmax() > 0) {
            axes[1] = (int) (Math.round(width / (window.getXmax() - window.getXmin()) * (0 - window.getXmin())));
        }

        if (window.getYmin() < 0 && window.getYmax() > 0) {
            axes[0] = height
                    - (int) (Math.round(height / (window.getYmax() - window.getYmin()) * (0 - window.getYmin())));
        }

        return axes;

    }

    private void getVals() {
        for (int j = 0; j < function.length; j++) {            

            double temp;
            double[][] testVals = new double[width][2];

            for (int i = 0; i < width; i += window.getXres()) {
                testVals[i][0] = i * xinc + window.getXmin();
                testVals[i][1] = function[j].eval(testVals[i][0]);
                function[j].setRoot(function[j].getRoot().setLeft(null));
                function[j].setRoot(function[j].getRoot().setRight(null));
            }
                this.vals.add(testVals);
        

	        for (int i = 0; i < testVals.length; i++) {
	            System.out.println("(" + testVals[i][0] + " ," + testVals[i][1] + " )");
	        }
        }
    }

    public int[] getCoord(double x, double y) {
        int[] coord = new int[2];
        if (x < window.getXmin() || x > window.getXmax()) {
            coord[0] = -1;
            coord[1] = -1;
            return coord;
        }
        coord[0] = (int) Math.round(((x - window.getXmin()) / (window.getXmax() - window.getXmin()) * width));
        coord[1] = height - (int) Math.round(((y - window.getYmin()) / (window.getYmax() - window.getYmin()) * height));
        System.out.println(coord[1] + "  " + y);
        return coord;
    }

    public double[] getPoint(int x, int y) {
        double[] point = new double[2];
        point[0] = (double) x / (double) width * (window.getXmax() - window.getXmin()) + window.getXmin();
        point[1] = (double) (height - y) / (double) height * (window.getYmax() - window.getYmin()) + window.getYmin();
        return point;
    }

    private void generateCoords() {

        for (int j = 0; j < function.length; j++) {

            double[][] tempVals = (double[][]) vals.get(j);
            ArrayList tempCoords = new ArrayList();

            for (int i = 0; i < tempVals.length; i++) {

                if (tempVals[i][1] <= window.getYmax() && tempVals[i][1] >= window.getYmin()) {

                    tempCoords.add(new Integer(height
                            - (int) (Math
                                    .round(((height / (window.getYmax() - window.getYmin())) * (tempVals[i][1] - window
                                            .getYmin()))))));
                } else
                    tempCoords.add(new Integer(-1));
            }

            for (int i = 1; i < tempCoords.size() - 1; i++) {
                if (((Integer) tempCoords.get(i - 1)).intValue() == -1
                        && ((Integer) tempCoords.get(i + 1)).intValue() == -1)
                    tempCoords.set(i, new Integer(-1));
            }

            try {
                this.coords.set(j, tempCoords);
            } catch (Exception e) {
                this.coords.add(tempCoords);
            }

        }

    }
}