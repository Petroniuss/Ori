package com.softy.oribackend.model.message;

public class PlayerJoinMessage {

    private String playerId;
    private String playerName;
    private String skinName;

    public String getSkinName() {
        return skinName;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public String toString() {
        return "PlayerJoinMessage{" +
                "playerId='" + playerId + '\'' +
                ", playerName='" + playerName + '\'' +
                ", skinName='" + skinName + '\'' +
                '}';
    }
}
