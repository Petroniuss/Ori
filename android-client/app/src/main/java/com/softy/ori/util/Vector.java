package com.softy.ori.util;

import java.util.Objects;

/**
 * Immutable class convenient for calculations performed on 2D vectors, points etc..
 *
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 05.09.2019 </i>
 */
public class Vector {

    public static final Vector UP = Vector.create(0, -1);
    public static final Vector DOWN = Vector.create(0, 1);
    public static final Vector LEFT = Vector.create(-1, 0);
    public static final Vector RIGHT = Vector.create(1, 0);
    public static final Vector ZERO = Vector.create(0, 0);

    public final double x;
    public final double y;

    private Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector create(double x, double y) {
        return new Vector(x, y);
    }

    public static Vector normalize(IntTuple tuple) {
        return create(tuple.x, tuple.y).normalize();
    }

    public static Vector randomVersor() {
        double dx = Math.random();
        double dy = Math.sqrt(1 - Math.pow(dx, 2));

        if (Math.random() <= .5)
            dx = -dx;
        if (Math.random() < .5)
            dy = -dy;

        return Vector.create(dx, dy);
    }

    public Vector normalize() {
        return scale(1.0);
    }

    public Vector scale(double len) {
        double a = len / Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

        return apply(d -> d * a);
    }

    public Vector add(Vector other) {
        return apply(Double::sum, other);
    }

    public Vector subtract(Vector other) {
        return apply((a, b) -> a - b, other);
    }

    public double scalarMultiplication(Vector other) {
        return (this.x * other.x) + (this.y + other.y);
    }

    public double distance(Vector other) {
        final Vector diff = apply((a, b) -> a - b, other).apply(Math::abs);

        return diff.x + diff.y;
    }

    public Vector apply(DoubleFunction f) {
        return Vector.create(f.apply(x), f.apply(y));
    }

    public Vector apply(DoubleBiFunction f, Vector other) {
        return Vector.create(
                f.apply(x, other.x),
                f.apply(y, other.y));
    }

    public static Vector fromAngle(double angle) {
        final double dx = Math.sin(angle);
        final double dy = Math.cos(angle);

        return Vector.create(dx, dy);
    }

    public double len() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    @Override
    public String toString() {
        return "Vector[" +
                "x=" + x +
                ", y=" + y +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector that = (Vector) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
