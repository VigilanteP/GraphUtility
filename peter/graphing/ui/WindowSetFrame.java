/*
 * Created on Dec 20, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package peter.graphing.ui;

import ics.common.AbsoluteLayout;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import peter.graphing.Graph;

/**
 * @author Peter
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WindowSetFrame extends JFrame implements KeyListener, ActionListener{
	
	private JTextField xmin, xmax, ymin, ymax, xscl, xres;
	private JLabel xminlbl, xmaxlbl, yminlbl, ymaxlbl, xscllbl, xreslbl;
	private JButton ok;
	private JButton cancel;
	private Graph g;
	private GraphFrame parent;
	
	public WindowSetFrame(Graph g, GraphFrame parent) {
		super("Window");
		this.parent = parent;
		setGraph(g);
		initializeComponents();
		this.setContentPane(new JPanel(new AbsoluteLayout(150,320,false,false)));
		this.setLayout(new AbsoluteLayout(150,235,false,false));
        this.setResizable(false);
        this.setVisible(true);
		addComponents();	
		this.setSize(150,235);	
		this.pack();	
	}

	private void initializeComponents() {
		xmin = new JTextField(String.valueOf(g.getWindow().getXmin()));
		xmax = new JTextField(String.valueOf(g.getWindow().getXmax()));
		ymin = new JTextField(String.valueOf(g.getWindow().getYmin()));
		ymax = new JTextField(String.valueOf(g.getWindow().getYmax()));
		xscl = new JTextField(String.valueOf((int)g.getWindow().getXscl()));
		xres = new JTextField(String.valueOf((int)g.getWindow().getXres()));
		xminlbl = new JLabel("X Min");
		xmaxlbl = new JLabel("X Max");
		yminlbl = new JLabel("Y Min");
		ymaxlbl = new JLabel("Y Max");
		xscllbl = new JLabel("X Scl");
		xreslbl = new JLabel("X Res");
		ok = new JButton("OK");
		cancel = new JButton("Cancel");
		
		//Action
		
		xmin.addKeyListener(this);
		xmax.addKeyListener(this);
		ymin.addKeyListener(this);
		ymax.addKeyListener(this);
		xscl.addKeyListener(this);
		xres.addKeyListener(this);
		ok.addActionListener(this);
		cancel.addActionListener(this);
	}
	
	private void addComponents() {
		this.getContentPane().add(xmin, new Rectangle(50,15,50,20));
		this.getContentPane().add(xmax, new Rectangle(50,40,50,20));
		this.getContentPane().add(ymin, new Rectangle(50,65,50,20));
		this.getContentPane().add(ymax, new Rectangle(50,90,50,20));
		this.getContentPane().add(xscl, new Rectangle(50,115,50,20));
		this.getContentPane().add(xres, new Rectangle(50,140,50,20));
		this.getContentPane().add(xminlbl, new Rectangle(5,15,40,20));
		this.getContentPane().add(xmaxlbl, new Rectangle(5,40,40,20));
		this.getContentPane().add(yminlbl, new Rectangle(5,65,40,20));
		this.getContentPane().add(ymaxlbl, new Rectangle(5,90,40,20));
		this.getContentPane().add(xscllbl, new Rectangle(5,115,40,20));
		this.getContentPane().add(xreslbl, new Rectangle(5,140,40,20));
		this.getContentPane().add(ok, new Rectangle(5,165,60,25));
		this.getContentPane().add(cancel, new Rectangle(70,165,70,25));
	}
	
	public void setGraph(Graph g) {
		this.g = g;	
	}
	
	public Graph getGraph() {
		return this.g;
	}
	
	public void setAll() {
		g.getWindow().setXmin(Double.parseDouble(xmin.getText()));
		g.getWindow().setXmax(Double.parseDouble(xmax.getText()));
		g.getWindow().setYmin(Double.parseDouble(ymin.getText()));
		g.getWindow().setYmax(Double.parseDouble(ymax.getText()));
		g.getWindow().setXscl(Integer.parseInt(xscl.getText()));
		g.getWindow().setXres(Integer.parseInt(xres.getText()));
	}
	
	public void keyTyped(KeyEvent evt) {
		if(evt.getSource().equals(xmin)){
			if(evt.getKeyChar() == '\n');
			g.getWindow().setXmin(Double.parseDouble(((JTextField)evt.getSource()).getText()));
		}
		if(evt.getSource().equals(xmax)){
			if(evt.getKeyChar() == '\n');
			g.getWindow().setXmax(Double.parseDouble(((JTextField)evt.getSource()).getText()));
		}		
		if(evt.getSource().equals(ymin)){
			if(evt.getKeyChar() == '\n');
			g.getWindow().setYmin(Double.parseDouble(((JTextField)evt.getSource()).getText()));
		}
		if(evt.getSource().equals(ymax)){
			if(evt.getKeyChar() == '\n');
			g.getWindow().setYmax(Double.parseDouble(((JTextField)evt.getSource()).getText()));
		}
		if(evt.getSource().equals(xscl)){
			if(evt.getKeyChar() == '\n');
			g.getWindow().setXscl(Integer.parseInt(((JTextField)evt.getSource()).getText()));
		}		
		if(evt.getSource().equals(xres)){
			if(evt.getKeyChar() == '\n');
			g.getWindow().setXres(Integer.parseInt(((JTextField)evt.getSource()).getText()));
		}
	}
	
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource().equals(ok)){
			this.dispose();
			setAll();
    		parent.getGraphPanel().setGraph(getGraph());
    		parent.reGraph(parent.getFunc(), parent.getGraphPanel().getGraph().getWindow());
		}
		else if(evt.getSource().equals(cancel))	
			this.dispose();
		
			
	}
	
	public void keyReleased(KeyEvent evt) {}
	public void keyPressed(KeyEvent evt) {}
}
