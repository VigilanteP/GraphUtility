/*
 * Created on Nov 29, 2004
 *
 * Graph Utility : -- Project's Function and description
 * 
 * 
 */
package peter.graphing.ui;

import ics.common.AbsoluteLayout;

import java.awt.Button;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import peter.graphing.Function;
import peter.graphing.Graph;
import peter.graphing.Window;

/**
 * Created by : Peter Bergeron
 * 
 * Contact:
 * 
 * VigilanteP@comcast.net VigilanteP on AIM
 *  
 */
public class GraphFrame extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

    private JMenuBar menu;
    private int height, width, zoom;
    private Function[] func;
    private GraphPanel gPanel;
    private TextField gTxt;
    private Label fOfX;
    private JCheckBox trace;
    private JCheckBox xhair;
    private Button gButton;
    private Button tButton;
    private Button cButton;
    private Button wButton;
    private Button yButton;
    private Button sinButton;
    private Button cosButton;
    private Button powButton;
    private Button eButton;
    private Button piButton;
    private Button tanButton;
    private Button lnButton;
    private Button logButton;

    public static void main(String[] args) {
        GraphFrame frame = new GraphFrame(Function.toFunc(args));
    }

    public Function[] getFunc() {
        return func;
    }

    public GraphPanel getGraphPanel() {
        return gPanel;
    }

    public GraphFrame(Function[] func) {
        super("Graph Screen");
        setLocalLookAndFeel();
        this.func = func;
        this.setContentPane(new JPanel());
        this.zoom = 0;
        width = 320;
        height = 200;
        initializeComponents();
        this.setLayout(new AbsoluteLayout(width + 200, height + 100, false, false));
        this.getContentPane().add(gPanel, new Rectangle(13, 13, width + 1, height + 1));
        drawComponents();
        this.setSize(width + 200, height + 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }       
    
    private Function activeFunc() {
        return func[0];
    }

    private void drawComponents() {
        this.getContentPane().add(gTxt, new Rectangle(45, 218, 228, 20));
        this.getContentPane().add(fOfX, new Rectangle(13, 217, 32, 17));
        this.getContentPane().add(trace, new Rectangle(340, 216, 75, 25));
        this.getContentPane().add(xhair, new Rectangle(415, 216, 100, 25));
        this.getContentPane().add(gButton, new Rectangle(275, 218, 58, 20));
        this.getContentPane().add(tButton, new Rectangle(340, 13, 52, 23));
        this.getContentPane().add(cButton, new Rectangle(399, 13, 52, 23));
        this.getContentPane().add(wButton, new Rectangle(458, 13, 52, 23));
        this.getContentPane().add(yButton, new Rectangle(386, 43, 52, 23));
        /*
         * this.getContentPane().add(sinButton, new Rectangle());
         * this.getContentPane().add(cosButton, new Rectangle());
         * this.getContentPane().add(powButton, new Rectangle());
         * this.getContentPane().add(eButton, new Rectangle());
         * this.getContentPane().add(piButton, new Rectangle());
         * this.getContentPane().add(tanButton, new Rectangle());
         * this.getContentPane().add(lnButton, new Rectangle());
         * this.getContentPane().add(logButton, new Rectangle());
         * this.setSize(width + 100, height + 80);
         */

        this.setJMenuBar(menu);
    }

    private void initializeComponents() {
        gPanel = new GraphPanel(new Graph(func, width, height, new Window()));
        menu = setMenu(new JMenuBar());
        gTxt = new TextField();
        fOfX = new Label("f(x)");
        trace = new JCheckBox("Trace");
        xhair = new JCheckBox("X-Hair");
        xhair.setEnabled(false);
        gButton = new Button("Graph");
        tButton = new Button("Table");
        cButton = new Button("Console");
        wButton = new Button("Window");
        yButton = new Button("Y= Editor");
        sinButton = new Button("sin");
        cosButton = new Button("cos");
        powButton = new Button("^");
        eButton = new Button("e^(");
        piButton = new Button("pi");
        tanButton = new Button("tan");
        lnButton = new Button("ln");
        logButton = new Button("log");

        //Action

        gButton.addActionListener(this);
        wButton.addActionListener(this);
        gPanel.addMouseListener(this);
        gPanel.addMouseMotionListener(this);
        trace.addActionListener(this);
        yButton.addActionListener(this);
    }

    private void save() {
        System.out.println("Saved");
    }

    private void open() {
        System.out.println("Opened");
    }

    private void reset() {
        System.out.println("Reset");
    }

    private JMenuBar setMenu(JMenuBar menu) {

        JMenu file = new JMenu("File");
        JMenu math = new JMenu("Math");
        JMenu settings = new JMenu("Settings");
        JMenu zoom = new JMenu("Zoom");
        

        {
            JMenuItem reset = new JMenuItem("Reset");
            JMenuItem open = new JMenuItem("Open");
            JMenuItem save = new JMenuItem("Save");

            reset.addActionListener(this);
            open.addActionListener(this);
            save.addActionListener(this);

            file.add(reset);
            file.add(open);
            file.add(save);
        }

        {
            JMenuItem zero = new JMenuItem("Zero");
            JMenuItem min = new JMenuItem("Relative Minimum");
            JMenuItem max = new JMenuItem("Relative Maximum");
            JMenuItem intersect = new JMenuItem("Intersect");
            JMenuItem deriv = new JMenuItem("Derive at ... ");
            JMenuItem integrate = new JMenuItem("Integrate at ... ");

            zero.addActionListener(this);
            min.addActionListener(this);
            max.addActionListener(this);
            intersect.addActionListener(this);
            deriv.addActionListener(this);
            integrate.addActionListener(this);

            math.add(zero);
            math.add(min);
            math.add(max);
            math.add(intersect);
            math.add(deriv);
            math.add(integrate);
        }
        
        {
            JMenuItem trig = new JMenuItem("Zoom Trig");
            JMenuItem std = new JMenuItem("Zoom Standard");
            JMenuItem dec = new JMenuItem("Zoom Decimal");
            JMenuItem in = new JMenuItem("Zoom In");
            JMenuItem out = new JMenuItem("Zoom Out");
            
            trig.addActionListener(this);
            std.addActionListener(this);
            dec.addActionListener(this);
            in.addActionListener(this);
            out.addActionListener(this);
            
            zoom.add(trig);
            zoom.add(std);
            zoom.add(dec);
            zoom.add(in);
            zoom.add(out);
        }
        

        menu.add(file);
        menu.add(math);
        menu.add(settings);
        menu.add(zoom);

        return menu;
    }
    
    public void reGraph(String func, Window window) {
        Function[] funcarry = new Function[1];
        funcarry[0] = new Function(func);
        reGraph(funcarry, window);
    }

    public void reGraph(Function[] func, Window window) {
        this.getContentPane().remove(gPanel);
        gPanel = new GraphPanel(new Graph(func, width, height, window));
        gPanel.addMouseListener(this);
        gPanel.addMouseMotionListener(this);
        this.add(gPanel, new Rectangle(13, 13, width + 1, height + 1));
        this.pack();
        this.setSize(width + 200, height + 100);
        this.getContentPane().repaint();
    }

    public void setLocalLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Error setting native LAF: " + e);
        }
    }
    
    private void zoom(int x, int y, int type) {
        Window temp = this.getGraphPanel().getGraph().getWindow();
        double xcoord = temp.getXmin()+(((double)x/(double)width) * (temp.getXmax()-temp.getXmin())); //distance from left
        double ycoord = ((double)(temp.getYmax()))-((double)y/(double)height) *  (double)(temp.getYmax()-temp.getYmin()); //distance from x axis
        
        if(type == Window.ZOOM_IN)
            this.getGraphPanel().getGraph().setWindow(new Window(xcoord - (temp.getXmax()-temp.getXmin())/4, xcoord + (temp.getXmax()-temp.getXmin())/4, ycoord - (temp.getYmax()-temp.getYmin())/4, ycoord + (temp.getYmax()-temp.getYmin())/4, 1, 1, 1));
    	else if(type == Window.ZOOM_OUT)
    	    this.getGraphPanel().getGraph().setWindow(new Window(xcoord - (temp.getXmax()-temp.getXmin()), xcoord + (temp.getXmax()-temp.getXmin()), ycoord - (temp.getYmax()-temp.getYmin()), ycoord + (temp.getYmax()-temp.getYmin()), 1, 1, 1));
        else
            throw new NumberFormatException("Incorrect type for zoom parameter");
        
        reGraph(getFunc(), getGraphPanel().getGraph().getWindow());
    	this.zoom = 0;
    }

    public void actionPerformed(ActionEvent evt) {

        Object source = null;

        try {
            source = (Button) evt.getSource();
        } catch (ClassCastException e) {
        }
        try {
            source = (JCheckBox) evt.getSource();
        } catch (ClassCastException e) {
        }
        try {
            source = (JMenuItem) evt.getSource();
        } catch (ClassCastException e) {
        }

        if (source == null)
            new JOptionPane("Caution: Unhandled ActionEvent", JOptionPane.WARNING_MESSAGE);

        if (source instanceof Button) {

            Button button = (Button) source;

            if (button.equals(gButton)) {
                this.func[0] = new Function(gTxt.getText());
                reGraph(gTxt.getText(), gPanel.getGraph().getWindow());
            } else if (button.equals(wButton)) {
                WindowSetFrame win = new WindowSetFrame(gPanel.getGraph(), this);
            } else if (button.equals(yButton)) {
                YEditorFrame yEdit = new YEditorFrame(gPanel.getGraph().getFunctions(), 10, this);
            }
        } else if (source instanceof JCheckBox) {

            JCheckBox cbox = (JCheckBox) source;

            if (cbox.equals(trace)) {
                if (!trace.isSelected()) {
                    reGraph(func, gPanel.getGraph().getWindow());
                    xhair.setEnabled(false);
                } else
                    xhair.setEnabled(true);
            }
        } else if (source instanceof JMenuItem) {
            JMenuItem item = (JMenuItem) source;

            if (item.getText().equals("Reset"))
                reset();
            else if (item.getText().equals("Open"))
                open();
            else if (item.getText().equals("Save"))
                save();
            else if (item.getText().equals("Zero"))
                new CalculationFrame("Zeroes", CalculationFrame.ZEROES, gPanel);
            else if (item.getText().equals("Relative Minimum"))
                new CalculationFrame("Relative Minimum", CalculationFrame.MIN, gPanel);
            else if (item.getText().equals("Relative Maximum"))
                new CalculationFrame("Relative Maximum", CalculationFrame.MAX, gPanel);
            else if (item.getText().equals("Derive at ... "))
                new CalculationFrame("Derive at ... ", CalculationFrame.DERIVE, gPanel);
            else if (item.getText().equals("Integrate at ... "))
                new CalculationFrame("Integrate at ... ", CalculationFrame.INTEGRATE, gPanel);
            else if (item.getText().equals("Zoom Trig")) {
                this.getGraphPanel().getGraph().setWindow(new Window(Window.TRIG));
            	reGraph(getFunc(), getGraphPanel().getGraph().getWindow());
            }
            else if (item.getText().equals("Zoom Standard")) {
                this.getGraphPanel().getGraph().setWindow(new Window(Window.STANDARD));
                reGraph(getFunc(), getGraphPanel().getGraph().getWindow());
            }
            else if (item.getText().equals("Zoom Decimal")) {
                this.getGraphPanel().getGraph().setWindow(new Window(Window.DECIMAL));
                reGraph(getFunc(), getGraphPanel().getGraph().getWindow());
            }
            else if (item.getText().equals("Zoom In")) {
                this.zoom = Window.ZOOM_IN;
            }
            else if (item.getText().equals("Zoom Out")) {
                this.zoom = Window.ZOOM_OUT;
            }
        }

    }

    public void mouseClicked(MouseEvent evt) {
        if(zoom == Window.ZOOM_IN)
            zoom(evt.getX(), evt.getY(), Window.ZOOM_IN);
        else if(zoom == Window.ZOOM_OUT)
            zoom(evt.getX(), evt.getY(), Window.ZOOM_OUT);
        else
            gPanel.drawLocation(evt.getX(), this.activeFunc());
    }

    public void mouseMoved(MouseEvent evt) {
        if (trace.isSelected())
            if (xhair.isSelected())
                gPanel.drawLocation(evt.getX(), this.activeFunc());
            else
                gPanel.drawCoords(evt.getX(), this.activeFunc());
    }

    public void mouseDragged(MouseEvent evt) {

    }

    public void mousePressed(MouseEvent evt) {
    }

    public void mouseReleased(MouseEvent evt) {
    }

    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseExited(MouseEvent evt) {
    }

}