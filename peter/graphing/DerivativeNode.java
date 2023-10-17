/*
 * Created on Jan 11, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package peter.graphing;

import java.util.Stack;

/**
 * @author Programming
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DerivativeNode {
    
    private String func;
    private DerivativeNode[] child;
    private boolean isRoot;
    private String deriv;

    public DerivativeNode() {
        this("");
    }        
    
    public DerivativeNode(String func) {
        this.func = func;
        this.child = new DerivativeNode[2];
    }
    
    public String derive() throws RuntimeException {

        while (this.func != Function.dePad(this.func))
            this.func = Function.dePad(this.func);
        
        if(!Function.hasOp(new Derivative(func, -1)))
            if(!Function.hasFunc(new Derivative(func, -1)))
                    return check(func);
            else
                if(func.substring(0,2).equals("ln")) {
                    child[0] = new DerivativeNode("ln(x)"); //outer
                    child[1] = new DerivativeNode(func.substring(3,func.length()-1)); //inner
                    this.deriv = new StringBuffer("((1/(" + child[1] + "))*(" + child[1].derive() + "))").toString();
                } else if(func.substring(0,3).equals("sin")) {
                    child[0] = new DerivativeNode("sin(x)"); //outer;
                	child[1] = new DerivativeNode(func.substring(4,func.length()-1)); //inner;
                	this.deriv = new StringBuffer("((cos(" + child[1] + "))*(" + child[1].derive() + "))").toString();
                } else if(func.substring(0,3).equals("cos")) {
                    child[0] = new DerivativeNode("cos(x)"); //outer;
                	child[1] = new DerivativeNode(func.substring(4,func.length()-1)); //inner;
                	this.deriv = new StringBuffer("((-sin(" + child[1] + "))*(" + child[1].derive() + "))").toString();
                } else if(func.substring(0,3).equals("tan")) {
                    child[0] = new DerivativeNode("tan(x)"); //outer;
                	child[1] = new DerivativeNode(func.substring(4,func.length()-1)); //inner;
                	this.deriv = new StringBuffer("((1/(cos(" + child[1] + ")^2))*(" + child[1].derive() + "))").toString();
                } else if(func.substring(0,3).equals("log")) {
                    child[0] = new DerivativeNode("log(x)"); //outer;
                	child[1] = new DerivativeNode(func.substring(4,func.length()-1)); //inner;
                	this.deriv = new StringBuffer("((1/(ln(10)*(" + child[1] + "))*(" + child[1].derive() + ")))").toString();
                }
                	
                    
        
        Stack stk = new Stack();

        for (int i = this.func.length() - 1; i >= 0; i--) {

            switch (this.func.charAt(i)) {
                case ')':
                    stk.push(new Integer(1));
                    break;
                case '(':
                    stk.pop();
                    break;
            }

            if (this.func.charAt(i) == '+' && stk.isEmpty()) {
                this.child[0] = new DerivativeNode(this.func.substring(0, i));
                this.child[1] = new DerivativeNode(this.func.substring(i + 1, this.func.length()));
                this.deriv = new StringBuffer("((" + child[0].derive() + ")+(" + child[1].derive() + "))").toString();
                return deriv;
            }
            if ((this.func.charAt(i) == '-' && stk.isEmpty())
                    && (i != 0 && (!FunctionNode.isOp(this.func.charAt(i - 1)) && this.func.charAt(i - 1) != 'E'))) {
                this.child[0] = new DerivativeNode(this.func.substring(0, i));
                this.child[1] = new DerivativeNode(this.func.substring(i + 1, this.func.length()));
                this.deriv = new StringBuffer("((" + child[0].derive() + ")-(" + child[1].derive() + "))").toString();
                return deriv;
            }

            if (i - 1 == 0 && this.func.charAt(i-1) != '(') {
                if (!stk.isEmpty()) {
                    System.out.println("Syntax Error at 167 " + this.func);
                    throw new RuntimeException();
                }
            }
        }

        for (int i = this.func.length() - 1; i >= 0; i--) {

            switch (this.func.charAt(i)) {
                case ')':
                    stk.push(new Integer(1));
                    break;
                case '(':
                    stk.pop();
                    break;
            }

            if (this.func.charAt(i) == '*' && stk.isEmpty()) {
                this.child[0] = new DerivativeNode(this.func.substring(0, i));
                this.child[1] = new DerivativeNode(this.func.substring(i + 1, this.func.length()));
                this.deriv = new StringBuffer("(((" + child[0].derive() + ")*(" + child[1] + "))+((" + child[1].derive() + ")*(" + child[0] + ")))").toString();
                return pad(deriv);
            }
            if (this.func.charAt(i) == '/' && stk.isEmpty()) {
                this.child[0] = new DerivativeNode(this.func.substring(0, i));
                this.child[1] = new DerivativeNode(this.func.substring(i + 1, this.func.length()));
                this.deriv = new StringBuffer("((((" + child[0].derive() + ")*(" + child[1] + "))-((" + child[1].derive() + ")*(" + child[0] + ")))" + '/' + "((" + child[1] + ")^2))").toString();
                return pad(deriv);
            }
            if ((i - 1 == 0)&& (this.func.charAt(i-1)!='(')) {
                if (!stk.isEmpty()) {
                    System.out.println("Syntax Error at 202 " + this.func);
                    throw new RuntimeException();
                }
            }
        }

        for (int i = this.func.length() - 1; i >= 0; i--) {

            switch (this.func.charAt(i)) {
                case ')':
                    stk.push(new Integer(1));
                    break;
                case '(':
                    stk.pop();
                    break;
            }

            if (this.func.charAt(i) == '^' && stk.isEmpty()) {
                this.child[0] = new DerivativeNode(this.func.substring(0, i));
                this.child[1] = new DerivativeNode(this.func.substring(i + 1, this.func.length()));
                this.deriv = new StringBuffer('(' + child[0].toString() + '*' + child[1] + ")^(" + child[1] + '-' + '1' + ")*(" + child[0].derive() + ')').toString();
                return deriv;
            }

            if (i - 1 == 0) {
                if (!stk.isEmpty()) {
                    System.out.println("Syntax Error at 230 " + this.func);
                    throw new RuntimeException();
                }
            }
        }
        
        return deriv;
    }
    
    private String replaceX(String func) {
        for(int i = 0; i<func.length()-1; i++) {
            if(func.charAt(i) == 'x')
                this.func = this.func.substring(0,i) + func + this.func.substring(i+1, func.length());
        }
        return this.func;
    }
    
    private DerivativeNode findInner(String func) {
        
        for(int i = 0; i<func.length(); i++) {
            if(func.charAt(i) == '^') {
                func = func.substring(1,i);
            	break;
            }
        }           
        return new DerivativeNode(func);
    }
    
    private DerivativeNode findOuter(String func) {        
        for(int i = 0; i<func.length(); i++) {
            if(func.charAt(i) == '^') {
                func = "x^" + func.substring(i+1, func.length());
            	break;
            }                
        }
        return new DerivativeNode(func);
    }
    
    public String toString() {
        return func;
    }
    
    private String check(String func) {
        if(func.charAt(func.length()-1) == 'x')
                return removeX(func);
        else
            return "0";
    }
    
    private String chain(String func) {
        for(int i = 0; i<func.length(); i++) {
            if(func.charAt(i) == '^')
                return new DerivativeNode(findOuter(func).derive()).replaceX(findInner(func).func) + "*" + findInner(func).derive();
       }
        
        return check(func);
    }
    
    private String removeX(DerivativeNode node) {
        return removeX(node.func);
    }
    
    private String removeX(String func) {
        if(func.length() == 1)
            return "1";
        return func.substring(0, func.length()-1);
    }
    
    private String pad(String func) {
        return new StringBuffer('(' + func + ')').toString();
    }

}
