/*
 * Created on Nov 24, 2004
 *
 * Graph Utility : -- Project's Function and description
 * 
 * 
 */
package peter.graphing;

/**
 * Created by : Peter Bergeron
 * 
 * Contact:
 * 
 * VigilanteP@comcast.net VigilanteP on AIM
 *  
 */
public class Window {

    private double xmin, xmax, ymin, ymax;
    private int xscl, yscl, xres;
    
    public static final double[] STANDARD = {-10D, 10D, -10D, 10D, 1, 1, 1};
    public static final double[] TRIG = {-2*Math.PI, 2*Math.PI, -2, 2, 1, 1, 1};
    public static final double[] DECIMAL = {-16D, 16D, -10D, 10D, 1, 1, 1};
    public static final int ZOOM_IN = 1;
    public static final int ZOOM_OUT = 2;
    
    

    public Window() {
        this(-10D, 10D, -10D, 10D, 1, 1, 1);
    }
    
    public Window(double[] win) {
        this(win[0], win[1], win[2], win[3], (int)win[4], (int)win[5], (int)win[6]);
    }

    public Window(double xmin, double xmax, double ymin, double ymax, int xscl, int yscl, int xres) {

        setXmin(xmin);
        setXmax(xmax);
        setYmin(ymin);
        setYmax(ymax);
        setXscl(xscl);
        setYscl(yscl);
        setXres(xres);
    }

    public double getXmax() {
        return xmax;
    }

    public void setXmax(double xmax) {
        this.xmax = xmax;
    }

    public double getXmin() {
        return xmin;
    }

    public void setXmin(double xmin) {
        this.xmin = xmin;
    }

    public double getXres() {
        return xres;
    }

    public void setXres(int xres) {
        this.xres = xres;
    }

    public int getXscl() {
        return xscl;
    }

    public void setXscl(int xscl) {
        this.xscl = xscl;
    }

    public double getYmax() {
        return ymax;
    }

    public void setYmax(double ymax) {
        this.ymax = ymax;
    }

    public double getYmin() {
        return ymin;
    }

    public void setYmin(double ymin) {
        this.ymin = ymin;
    }

    public double getYscl() {
        return yscl;
    }

    public void setYscl(int yscl) {
        this.yscl = yscl;
    }
}