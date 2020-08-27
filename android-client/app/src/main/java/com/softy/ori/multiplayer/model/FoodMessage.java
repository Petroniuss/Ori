package com.softy.ori.multiplayer.model;

import com.softy.ori.util.Vector;

public class FoodMessage {

    private int id;
    private Vector position;
    private double radius;
    private int color;

    public int getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public Vector getPosition() {
        return position;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "FoodMessage{" +
                "id=" + id +
                ", position=" + position +
                ", radius=" + radius +
                '}';
    }
}
