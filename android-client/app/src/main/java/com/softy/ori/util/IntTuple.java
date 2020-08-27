package com.softy.ori.util;

import java.util.Objects;

public class IntTuple {

    public final int x;
    public final int y;

    private IntTuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static IntTuple create(int x, int y) {
        return new IntTuple(x, y);
    }

    public IntTuple apply(IntFunction f) {
        return IntTuple.create(f.apply(x), f.apply(y));
    }

    public Vector versor() {
        return Vector.normalize(this);
    }

    @Override
    public String toString() {
        return "IntTuple{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntTuple intTuple = (IntTuple) o;
        return x == intTuple.x &&
                y == intTuple.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
