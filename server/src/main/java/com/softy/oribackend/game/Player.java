package com.softy.oribackend.game;

import com.softy.oribackend.model.GameConstants;
import com.softy.oribackend.util.ColorUtils;
import com.softy.oribackend.util.Vector;

public class Player {

    private final String id;
    private final String name;
    private final String skinName;

    private double radius;
    private Vector position;
    private Vector direction;
    private int color;

    public Player(String name, String id, String skinName) {
        this.id = id;
        this.name = name;
        this.skinName = skinName;
        this.color = ColorUtils.rainbowColor();
        this.radius = GameConstants.PLAYER_INIT_RADIUS;
        this.direction = Vector.randomVersor();
        this.position = Vector.randomVersor()
                .apply(Math::abs)
                .apply(fi -> fi * GameConstants.BOARD_SIZE);
    }

    public void update() {
        if (position.x <= 0)
            direction = Vector.RIGHT;
        else if (position.y <= 0)
            direction = Vector.DOWN;
        else if (position.x >= GameConstants.BOARD_SIZE)
            direction = Vector.LEFT;
        else if (position.y >= GameConstants.BOARD_SIZE)
            direction = Vector.UP;

        final Vector dS = direction.scale(GameConstants.PLAYER_SPEED);

        position = position.add(dS);
    }

    public void grow(double deltaR) {
        radius = Math.sqrt(Math.pow(radius, 2) + Math.pow(deltaR, 2));
    }

    public boolean intersects(Player other) {
        return other.position.distance(position) <= radius;
    }

    public void setDirection(Vector dV) {
        direction = dV.normalize();
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSkinName() {
        return skinName;
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

    public int getColor() {
        return color;
    }

    @Override
    public String toString() {
        return String.format("%s %d", name, (int) radius);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        return getId().equals(player.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
