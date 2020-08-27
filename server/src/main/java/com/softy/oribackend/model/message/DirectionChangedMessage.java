package com.softy.oribackend.model.message;

import com.softy.oribackend.util.Vector;

public class DirectionChangedMessage {

    private String playerId;
    private Vector direction;

    public String getPlayerId() {
        return playerId;
    }

    public Vector getDirection() {
        return direction;
    }
}
