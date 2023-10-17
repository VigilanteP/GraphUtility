/*
 * Created on Jan 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package peter.graphing.ui;

import ics.common.AbsoluteLayout;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import peter.Math;
import peter.graphing.Graph;

/**
 * @author Programming
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CalculationFrame extends JFrame implements ActionListener, MouseListener {

    public static final int ZEROES = 0;
    public static final int MIN = 1;
    public static final int MAX = 2;
    public static final int INTERSECT = 3;
    public static final int DERIVE = 4;
    public static final int INTEGRATE = 5;

    private double currentValue;
    private double leftBound;
    private double rightBound;
    private int type;
    private JButton lbButton;
    private JButton rbButton;
    private JButton calcButton;
    private JTextField result;

    private GraphPanel gPanel;

    public CalculationFrame(String title, int type, GraphPanel gPanel) {
        super(title);
        this.type = type;
        this.gPanel = new GraphPanel(new Graph(gPanel.getGraph().getFunction(), (int) (gPanel.getWidth() * .75),
                (int) (gPanel.getHeight() * .75), gPanel.getGraph().getWindow()));
        this.setLayout(new AbsoluteLayout(gPanel.getWidth() + 70, gPanel.getHeight() + 70, false, false));
        initializeComponents(type);
        addComponents();
        this.setSize(gPanel.getWidth() + 70, gPanel.getHeight() + 70);
        this.setVisible(true);
    }

    private void addComponents() {
        this.getContentPane().add(gPanel, new Rectangle(10, 10, gPanel.getWidth(), gPanel.getHeight()));
        this.getContentPane().add(lbButton, new Rectangle(gPanel.getWidth() + 15, 10, 110, 20));
        this.getContentPane().add(rbButton, new Rectangle(gPanel.getWidth() + 15, 35, 110, 20));
        this.getContentPane().add(calcButton, new Rectangle(10, gPanel.getHeight() + 40, 90, 20));
        this.getContentPane().add(result, new Rectangle(10, gPanel.getHeight() + 15, gPanel.getWidth(), 20));
    }

    private void initializeComponents(int type) {
        lbButton = new JButton("Left Bound");
        rbButton = new JButton("Right Bound");
        calcButton = new JButton("Calculate");
        result = new JTextField();
        result.setEditable(false);

        //Action Listeners

        lbButton.addActionListener(this);
        rbButton.addActionListener(this);
        calcButton.addActionListener(this);
        gPanel.addMouseListener(this);
    }

    private void setLeftBound(double val) {
        leftBound = val;
    }

    private void setRightBound(double val) {
        rightBound = val;
    }

    private void calculate(int type) {
        double temp;
        switch (type) {
            case 0:
                temp = Math.zero(leftBound, rightBound, gPanel.getGraph().getFunction());
                result.setText(new Double(temp).toString());
                break;
            case 1:
                temp = Math.minimum(leftBound, rightBound, gPanel.getGraph().getFunction());
                result.setText(new Double(temp).toString());
                break;
            case 2:
                temp = Math.maximum(leftBound, rightBound, gPanel.getGraph().getFunction());
                result.setText(new Double(temp).toString());
                break;
            case 3:
                break;
            case 4:
                result.setText(new Double(Math.derive(currentValue, gPanel.getGraph().getFunction())).toString());
                break;
            case 5:
                //result.setText(new Double(Math.integrate(currentValue,
                // gPanel.getGraph().getFunction())).toString());
                break;
        }
    }

    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();

        if (source.equals(lbButton))
            setLeftBound(currentValue);
        else if (source.equals(rbButton))
            setRightBound(currentValue);
        else if (source.equals(calcButton)) {
            calculate(type);
            if(type <= 3){
            int[] resultCoords = gPanel.getGraph().getCoord(Double.parseDouble(result.getText()),
                    gPanel.getGraph().getFunction().eval(Double.parseDouble(result.getText())));
            gPanel.drawLocation(resultCoords[0], gPanel.getGraph().getFunction());
        
            }
        }

    }

    public void mouseClicked(MouseEvent evt) {
        gPanel.drawLocation(evt.getX(), gPanel.getGraph().getFunction());
        currentValue = gPanel.getGraph().getPoint(evt.getX(), evt.getY())[0];
    }

    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseExited(MouseEvent evt) {
    }

    public void mousePressed(MouseEvent evt) {
    }

    public void mouseReleased(MouseEvent evt) {
    }

}