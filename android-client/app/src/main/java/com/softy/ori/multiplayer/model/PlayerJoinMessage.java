package com.softy.ori.multiplayer.model;

public class PlayerJoinMessage {

    private final String playerId;
    private final String playerName;
    private final String skinName;

    public PlayerJoinMessage(String playerId, String playerName, String skinName) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.skinName = skinName;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getSkinName() {
        return skinName;
    }
}
