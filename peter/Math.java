/*
 * Created on Nov 25, 2004
 *
 * Graph Utility : -- A program which graphs functions
 * 					  and performs other useful mathematical operations
 * 
 * 
 */
package peter;

import peter.graphing.Derivative;
import peter.graphing.Function;

/**
 * Created by : Peter Bergeron
 * 
 * Contains basic useful Math operations
 * 
 * Contact:
 * 
 * VigilanteP@comcast.net VigilanteP on AIM
 *  
 */
public class Math {

    public static void main(String[] args) {
        System.out.println(zero(-3, 2, new Function("3x+7")));
    }

    public static Function[] array(Function f) {
        Function[] func = { f };
        return func;
    }

    public static double round(double d, int p) throws NumberFormatException {

        if (p > 17) {
            throw new NumberFormatException(Math.class + ": Decimal place accuracy must be 17 or lower");
        }

        if (d == 0) {
            return 0D;
        }

        return java.lang.Math.round((java.lang.Math.pow(10, p)) * d) / java.lang.Math.pow(10, p);

    }

    public static float round(float d, int p) {

        if (p > 17) {
            throw new NumberFormatException(Math.class + ": Decimal place accuracy must be 17 or lower");
        }

        if (d == 0) {
            return 0F;
        }

        return ((float) (java.lang.Math.round((java.lang.Math.pow(10, p)) * d) / java.lang.Math.pow(10, p)));
    }
    
    public static double zero(double left, double right, Function func) {
        return zero(left, right, left+((right-left)/2), func);
    }

    public static double zero(double left, double right, double guess, Function func) {
        
        if(guess == Double.NEGATIVE_INFINITY || guess == Double.POSITIVE_INFINITY)
            return guess == Double.NEGATIVE_INFINITY ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        String[] part ={new Double(new Derivative(func.getFunc()).getDerivative().eval(guess)).toString(), "(x-", new Double(guess).toString(), ")+", new Double(func.eval(guess)).toString()};
        String tan = new StringBuffer(part[0] + part[1] + part[2] + part[3] + part[4]).toString();
        double zero = linearZero(part);
        double temp = func.eval(zero);
        
        if(zero<=left)
            if(guess != left+((right-left)/2))
                    return Double.NEGATIVE_INFINITY;
        
        if(zero>=right)
            if(guess != left+((right-left)/2))
                    return Double.POSITIVE_INFINITY;
        
        if(java.lang.Math.abs(func.eval(zero)) < 1E-12)
                return zero;
        
        zero = zero(left, right, zero, func);
        if(zero == Double.NEGATIVE_INFINITY) 
                zero = zero(left, right, ((right-guess)/100)+guess, func);            
        else if(zero == Double.POSITIVE_INFINITY) 
                zero = zero(left, right, ((guess-left)/100)+guess, func);
        
        return zero;

    }

    private static double linearZero(String[] part) {
        double m = Double.parseDouble(part[0]);
        double x1 = Double.parseDouble(part[2]);
        double y1 = Double.parseDouble(part[4]);
        return (m*x1-y1)/m;
    }

    public static double minimum(double left, double right, Function func) {
        if (left >= right)
            throw new NumberFormatException("Invalid Bounds: Left bound exceeds right bound");
        return zero(left, right, new Derivative(func.getFunc()).getDerivative());
    }

    public static double maximum(double left, double right, Function func) {
        if (left >= right)
            throw new NumberFormatException("Invalid Bounds: Left bound exceeds right bound");
        return zero(left, right, new Derivative(func.getFunc()).getDerivative());
    }

    public static double derive(double x, Function func) {
        return new Derivative(func.getFunc()).getDerivative().eval(x);
    }
}