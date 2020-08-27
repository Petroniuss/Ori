package com.softy.ori.multiplayer.model;

import com.softy.ori.util.Vector;

public class DirectionChangedMessage {

    private final String playerId;
    private final Vector direction;

    public DirectionChangedMessage(String playerId, Vector direction) {
        this.playerId = playerId;
        this.direction = direction;
    }

}
