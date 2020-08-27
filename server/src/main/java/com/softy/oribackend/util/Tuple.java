package com.softy.oribackend.util;

public class Tuple<A, B> {

    public final A x;
    public final B y;

    public Tuple(A a, B b) {
        this.x = a;
        this.y = b;
    }

    public static <A,B> Tuple<A, B> create(A a, B b) {
        return new Tuple<>(a, b);
    }

}
