/*
 * Created on Jan 11, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package peter.graphing;

import java.util.Scanner;

import peter.Math;

/**
 * @author Programming
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Derivative extends Function {
    
    private String derivative;
    private DerivativeNode root;
    
    public static void main (String args[]) {
        Scanner scan = new Scanner(System.in);
        String str = scan.next();
        System.out.println("Derive at : ");
        double x = scan.nextDouble();
        
        while(!str.equals("-1")) {
	        Derivative deriv = new Derivative(str);
	        System.out.println(deriv.getDerivative());
	        System.out.println(deriv.getDerivative().eval(x));
	        System.out.println(Math.derive(x, deriv.getFunction()));
	        Function.printTree(deriv.getRoot());
	        str = scan.next();
        }
    }
    
    /*Overloaded constructor allows for a Derivative object to
     * be explicitly created without the derive() method performed
     */
    
    
    public Derivative(String func, int option) {
        this.root = new DerivativeNode(func);
        super.setFunc(func);
        if(option != -1)
            this.derivative = derive();
    }

    public Derivative(String func) {
        this.root = new DerivativeNode(func);
        super.setFunc(func);
        this.derivative = derive();
    }

    public Derivative() {
        super();
    }
    
    public String derive() {
        return root.derive();
    }
    
    public Function getDerivative() {
        return new Function(derivative);
    }
    
    public Function getFunction() {
        return new Function(getFunc());
    }
    
    public String toString() {
        return getFunc();
    }

}
