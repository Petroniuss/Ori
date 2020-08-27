package com.softy.oribackend.util;

public class MathUtils {


    /**
     * @param a lower bound
     * @param v value
     * @param b upper bound
     * @return value if within bounds else (respectively) bound a or b.
     */
    public static int between(int a, int v, int b) {
        return Math.max(a, Math.min(v, b));
    }

    public static double pythagoras(double a, double b) {
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

}
