/*
 * Created on Nov 18, 2004
 *
 * Graph Utility : An extensible graph utility based on the abilities
 * 					of Texas Instruments TI-83 and TI-89 Calculators.
 * 
 * 
 */
package peter.graphing;

import java.util.Stack;

/**
 * Created by : Peter Bergeron
 * 
 * Contact:
 * 
 * VigilanteP@comcast.net VigilanteP on AIM
 *  
 */
public class FunctionNode {

    private double value = Double.NEGATIVE_INFINITY;
    private String func = "";
    private char op = 0;
    private FunctionNode[] child = new FunctionNode[2];
    boolean isRoot = false;

    public FunctionNode(FunctionNode[] child) {
        this.child = child;
    }

    public FunctionNode(FunctionNode lchild, FunctionNode rchild) {
        this.child[0] = lchild;
        this.child[1] = rchild;
    }

    public FunctionNode(FunctionNode lchild, FunctionNode rchild, boolean isRoot) {
        this.child[0] = lchild;
        this.child[1] = rchild;
        this.isRoot = isRoot;
    }

    public FunctionNode(String func, char op) {
        this.func = func;
        this.op = op;
    }

    public FunctionNode(String func, boolean isRoot) {
        this.func = func;
        this.isRoot = isRoot;
    }

    public FunctionNode(String func) {
        this.func = func;
    }

    public FunctionNode() {
        this.child[1] = this.child[0] = null;
    }

    public void addChild(FunctionNode child) {
        if (this.child[0] == null || this.child[1] == null) {
            if (this.child[0] == null)
                this.child[0] = child;
            else if (this.child[1] == null)
                this.child[1] = child;
        } else
            System.err.println("bad child");
    }

    public FunctionNode setLeft(FunctionNode l) {
        child[0] = l;
        return this;
    }

    public FunctionNode setRight(FunctionNode r) {
        child[1] = r;
        return this;
    }

    public FunctionNode getChildAt(int index) {
        if (child.length > 0)
            return child[index];
        else
            return null;
    }

    public double getValue() {
        return value;
    }

    public String getFunc() {
        return func;
    }

    public char getOp() {
        return op;
    }

    public double evaluate() {

        if (this.child[0] == null)
            return this.value;

        if (this.value == Double.NEGATIVE_INFINITY) {
            if (this.child[1] != null)
                this.value = evaluate(this.child[0].evaluate(), this.child[1].evaluate(), this.op);
            else
                this.value = this.child[0].evaluate();
        }

        switch (this.op) {
            case 's':
            case 't':
            case 'c':
            case 'l':
            case 'n':
                this.value = evaluate(this.value, 0, this.op);
                return this.value;
        }
        return evaluate(this.child[0].getValue(), this.child[1].getValue(), this.op);
    }

    public static double evaluate(double a, double b, char op) throws ArithmeticException {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    return Double.NaN;
                return a / b;
            case '^':
                return Math.pow(a, b);
            case 's':
                return Math.sin(a);
            case 'c':
                return Math.cos(a);
            case 't':
                return Math.tan(a);
            case 'l':
                return Math.log10(a);
            case 'n':
                if (a == 0)
                    return Double.NaN;
                return Math.log(a);
        }

        return 0;
    }

    public static void makeTree(FunctionNode root) {

        while (!(Function.dePad(root.func).equals(root.func)))
            root.func = Function.dePad(root.func);

        boolean hasOp = Function.hasOp(root.func);

        if (hasOp)
            root.parseFunc();
        else
            root.value = Double.parseDouble(root.func);

    }

    private void parseFunc() throws RuntimeException {

        Stack stk = new Stack();
        if (func.length() >= 3)
            preFormat();

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
                this.op = '+';
                this.child[0] = new FunctionNode(this.func.substring(0, i));
                this.child[1] = new FunctionNode(this.func.substring(i + 1, this.func.length()));
                makeTree(child[0]);
                makeTree(child[1]);
                return;
            }
            if ((this.func.charAt(i) == '-' && stk.isEmpty())
                    && (i != 0 && (!isOp(this.func.charAt(i - 1)) && this.func.charAt(i - 1) != 'E'))) {
                this.op = '-';
                this.child[0] = new FunctionNode(this.func.substring(0, i));
                this.child[1] = new FunctionNode(this.func.substring(i + 1, this.func.length()));
                makeTree(child[0]);
                makeTree(child[1]);
                return;
            }

            if (i - 1 == 0 && this.func.charAt(i - 1) != '(') {
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
                this.op = '*';
                this.child[0] = new FunctionNode(this.func.substring(0, i));
                this.child[1] = new FunctionNode(this.func.substring(i + 1, this.func.length()));
                makeTree(child[0]);
                makeTree(child[1]);
                return;
            }
            if (this.func.charAt(i) == '/' && stk.isEmpty()) {
                this.op = '/';
                this.child[0] = new FunctionNode(this.func.substring(0, i));
                this.child[1] = new FunctionNode(this.func.substring(i + 1, this.func.length()));
                makeTree(child[0]);
                makeTree(child[1]);
                return;
            }
            if (i - 1 == 0 && this.func.charAt(i - 1) != '(') {
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

            if (this.func.charAt(i) == '^') {
                this.op = '^';
                this.child[0] = new FunctionNode(this.func.substring(0, i));
                this.child[1] = new FunctionNode(this.func.substring(i + 1, this.func.length()));
                makeTree(child[0]);
                makeTree(child[1]);
                return;
            }

            if (i - 1 == 0 && this.func.charAt(i - 1) != '(') {
                if (!stk.isEmpty()) {
                    System.out.println("Syntax Error at 230 " + this.func);
                    throw new RuntimeException();
                }
            }
        }
    }

    public static boolean isOp(char op) {
        switch (op) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '^':
            case '(':
                return true;
        }

        return false;
    }

    private void preFormat() {
        while (!(Function.dePad(func).equals(func)))
            func = Function.dePad(func);

        if (this.func.substring(0, 3).equals("cos")) {
            if (checkFunc(func, 'c')) {
                makeTree(child[0]);
                return;
            }
        }
        if (this.func.substring(0, 3).equals("sin")) {
            if (checkFunc(func, 's')) {
                makeTree(child[0]);
                return;
            }
        }
        if (this.func.substring(0, 3).equals("tan")) {
            if (checkFunc(func, 't')) {
                makeTree(child[0]);
                return;
            }
        }
        if (this.func.substring(0, 3).equals("log")) {
            if (checkFunc(func, 'l')) {
                makeTree(child[0]);
                return;
            }
        }
        if (this.func.substring(0, 2).equals("ln")) {
            if (checkFunc(func, 'n')) {
                makeTree(child[0]);
                return;
            }
        }
    }

    private boolean checkFunc(String func, char op) {

        if (func.charAt(func.length() - 1) != ')')  //if the last character is
            return false; 							// not ')' then it must not
        											// be a single statement

        Stack temp = new Stack();
        func = func.substring((op == 'n' ? 3 : 4), func.length() - 1);
        temp.push(new Object()); //represents first '(' in the function (which
                                 // is implied)

        for (int i = 0; i < func.length() - (op == 'n' ? 2 : 3) && !temp.isEmpty(); i++) {
            if (func.charAt(i) == '(')
                temp.push(new Object());
            else if (func.charAt(i) == ')')
                temp.pop();

            if (temp.isEmpty())
                return false;
        }
        this.op = op;
        this.child[0] = new FunctionNode(this.func.substring((op == 'n' ? 3 : 4), this.func.length() - 1));
        return true;

    }
}