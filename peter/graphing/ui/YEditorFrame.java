/*
 * Created on Jan 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package peter.graphing.ui;

import ics.common.AbsoluteLayout;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import peter.graphing.Function;
import peter.graphing.Graph;

/**
 * @author Programming
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class YEditorFrame extends JFrame implements ActionListener {

    private JTextField[] field;
    private JLabel[] label;
    private Function[] func;
    private GraphFrame parent;
    private String[] str;
    private JButton apply;
    private JButton cancel;
    private JButton ok;
    private int num;

    public YEditorFrame(Function[] func, int num, GraphFrame parent) {

        field = new JTextField[num];
        label = new JLabel[num];
        this.parent = parent;
        this.func = func;
        this.num = num;
        str = new String[num];

        this.setContentPane(new JPanel());
        this.setLayout(new AbsoluteLayout(230, 340));

        initializeComponents();
        drawComponents();

        this.setSize(230, 340);
        this.setVisible(true);

    }

    private void initializeComponents() {
        for (int i = 0; i < func.length; i++) {
            if (func[i] != null)
                str[i] = func[i].getFunc();
            else
                str[i] = "";
        }

        for (int i = 0; i < num; i++) {
            label[i] = new JLabel("Y" + (i + 1));
            field[i] = new JTextField(i < str.length ? str[i] : "");
        }

        apply = new JButton("Apply");
        cancel = new JButton("Cancel");
        ok = new JButton("OK");

        apply.addActionListener(this);
        cancel.addActionListener(this);
        ok.addActionListener(this);
    }

    private void drawComponents() {

        for (int i = 0; i < num; i++) {
            this.getContentPane().add(label[i], new Rectangle(10, 20 + (i * 25), 40, 20));
            this.getContentPane().add(field[i], new Rectangle(45, 20 + (i * 25), 120, 20));
        }

        this.getContentPane().add(ok, new Rectangle(10, 270, 50, 20));
        this.getContentPane().add(cancel, new Rectangle(80, 270, 70, 20));
        this.getContentPane().add(apply, new Rectangle(150, 270, 70, 20));

    }

    public void actionPerformed(ActionEvent evt) {
        JButton source = (JButton) evt.getSource();

        if (source.equals(ok)) {
            setFunctions();
            parent.getGraphPanel().setGraph(
                    new Graph(getFunctions(), parent.getWidth(), parent.getHeight(), parent.getGraphPanel().getGraph()
                            .getWindow()));
            parent.reGraph(getFunctions(), parent.getGraphPanel().getGraph().getWindow());
            this.dispose();
        } else if (source.equals(cancel)) {
            this.dispose();
        } else if (source.equals(apply)) {
            setFunctions();
        }
    }

    public void setFunctions() {
        for(int i = 0, j = 0; i<field.length; i++) {
            if(!(field[i].getText().equals("")))
                j++;
            func = new Function[j];
        }
        
        for (int i = 0, j = 0; i < field.length && j < func.length; i++) {
            if (!(field[i].getText().equals("")))
                func[j++] = new Function(field[i].getText());
        }
    }

    public Function[] getFunctions() {
        return func;
    }

}