package com.softy.ori.multiplayer.model;

import com.softy.ori.util.Vector;

public class PlayerMessage {

    private String id;
    private String name;
    private String skinName;

    private Vector position;
    private Vector direction;
    private double radius;
    private int color;

    public int getColor() {
        return color;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRadius() {
        return radius;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getDirection() {
        return direction;
    }

    public String getSkinName() {
        return skinName;
    }

    @Override
    public String toString() {
        return "PlayerMessage{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", skinName='" + skinName + '\'' +
                ", position=" + position +
                ", direction=" + direction +
                ", radius=" + radius +
                ", color=" + color +
                '}';
    }
}
