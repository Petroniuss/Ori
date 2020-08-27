package com.softy.ori.util;

public class Tuple<A, B> {

    public final A x;
    public final B y;

    private Tuple(A x, B y) {
        this.x = x;
        this.y = y;
    }

    public static <A,B> Tuple<A, B> create(A x, B y) {
        return new Tuple<>(x, y);
    }

}
