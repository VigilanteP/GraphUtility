package peter.graphing;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Function {

    private double x;
    private String func;
    private String varFunc;
    private ArrayList sub_operations = new ArrayList();
    private ArrayList operands = new ArrayList();
    private FunctionNode root;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter a function: ");
        String s = scan.next();
        System.out.println("");
        System.out.print("Enter an x value: ");
        double d = scan.nextDouble();
        System.out.println("");
        System.out.println("");
        Function f = new Function(s);
        System.out.println(f.eval(d));
    }

    public static Function[] toFunc(String[] str) {

        Function[] func = new Function[str.length];

        for (int i = 0; i < str.length; i++) {
            func[i] = new Function(str[i]);
        }
        return func;
    }

    public static void errOut(String s) {
        System.err.println("Error Encountered: " + s);
        System.exit(0);
    }

    public Function(String func) {
        if(func.equals("")) {
            func = "-Infinity";
        }
        varFunc = func;
        setFunc(preFormat(removeWhiteSpace(func)));
    }

    public Function() {
        this("");
    }

    public String getFunc() {
        return func != "-Infinity" ? func : "";
    }
    
    public String toString() {
        return getFunc();
    }

    public void setFunc(String func) {
        this.func = dePad(func);
    }

    public void setRoot(FunctionNode root) {
        this.root = root;
    }

    public FunctionNode getRoot() {
        return root;
    }

    public double eval(double x) {

        for (int i = 0; i < func.length(); i++) {
            if (func.charAt(i) == 'x')
                replaceX(i, x);
        }
        parseFunc();
        func = varFunc;
        return eval(root);
    }

    public void parseFunc() {
        root = new FunctionNode(this.func);
        FunctionNode.makeTree(root);
        //printTree(root);
    }

    private double eval(FunctionNode root) {

        return root.evaluate();
    }

    private void replaceX(int i, double x) {
        func = func.substring(0, i) + String.valueOf(x) + func.substring(i + 1, func.length());

    }

    public static void printTree(FunctionNode root) {
        if (root == null)
            return;
        System.out.println("");
        System.out.print(root.getFunc() + "= " + root.getValue() + "  : ");
        if (root.getChildAt(0) != null)
            System.out.print(root.getChildAt(0).getFunc() + " " + root.getOp() + " ");
        if (root.getChildAt(1) != null)
            System.out.print(root.getChildAt(1).getFunc());
        printTree(root.getChildAt(0));
        printTree(root.getChildAt(1));
    }

    private String removeWhiteSpace(String s) {
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == ' ')
                if (i == s.length() - 1)
                    return s.substring(0, s.length() - 2);
                else
                    return s.substring(0, i) + s.substring(i + 1, s.length() - 1);
        return s;
    }

    private String preFormat(String func) {
        Integer temp;
        for (int i = 0; i < func.length() - 1; i++) {

            if (isInt(func.charAt(i)) && func.charAt(i + 1) == 'x') {
                varFunc = func = func.substring(0, i + 1) + '*'
                        + (i + 1 < func.length() ? func.substring(i + 1, func.length()) : "");
            } else if (func.charAt(i) == 'x') {
                if (isInt(func.charAt(i + 1)) || func.charAt(i + 1) == 'x')
                    varFunc = func = func.substring(0, i + 1) + '*'
                            + (i + 1 < func.length() ? func.substring(i + 1, func.length()) : "");
            }

        }

        return func;
    }
    
    public static boolean hasOp(String s) {
        return hasOp(new Function(s));
    }
    
    public static boolean hasOp(Function f) {

        String s = f.getFunc();
        Stack stk = new Stack();
        boolean hasOp = false;

        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '(': stk.push(new Object());
                			break;
                case ')': stk.pop();
                			break;
                case '-':
                    if (i == 0)
                        break;
                    if (s.charAt(i - 1) == 'E')
                        break;

                case '+':
                case '*':
                case '/':
                case '^':
                    if(f instanceof Derivative) {
                        if(stk.isEmpty())
                            hasOp = true;
                    }
                    else
                        hasOp = true;
            }

            if (!(f instanceof Derivative)) {
	            if (i < s.length() - 3) {
	                String temp = s.substring(i, i + 3);
	
	                if (temp.equals("cos"))
	                    hasOp = true;
	                if (temp.equals("sin"))
	                    hasOp = true;
	                if (temp.equals("tan"))
	                    hasOp = true;
	                if (temp.equals("log"))
	                    hasOp = true;
	                if (temp.substring(0, 2).equals("ln"))
	                    hasOp = true;
	            }
            }
        }
        
        return hasOp;
    }
    
    public static boolean hasFunc (String s) {
        return hasFunc(new Function(s));
    }
    
    public static boolean hasFunc(Function f) {
        
        String s = f.getFunc();
        
        if(s.length()<3)
            return false;
        
        if(s.substring(0,3).equals("sin"))
            return true;
        else if(s.substring(0,3).equals("cos"))
            return true;
        else if(s.substring(0,3).equals("tan"))
            return true;
        else if(s.substring(0,3).equals("log"))
            return true;
        else if(s.substring(0,2).equals("ln"))
            return true;
        
        return false;
        
    }
    
    public static String dePad(String func) {
        Stack stk = new Stack();
        for(int i = 0; i<func.length(); i++) {
            if(i!= 0 && stk.isEmpty())
                return func;
            if(func.charAt(i) == '(')
                stk.push(new Object());
            else if(func.charAt(i) == ')')
                stk.pop();
        }     
        
        if(func.charAt(0) == '(' && func.charAt(func.length()-1) == ')')
            return func.substring(1, func.length()-1);
        
        return func;              
    }

    private boolean isInt(char c) {
        if (c == 'x')
            return false;
        Character ch = new Character(c);
        try {
            if (Integer.parseInt(ch.toString()) <= 9 && Integer.parseInt(ch.toString()) >= 0)
                return true;
            if (ch.equals(new Character('(')) || ch.equals(new Character(')')))
                return true;
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }
}