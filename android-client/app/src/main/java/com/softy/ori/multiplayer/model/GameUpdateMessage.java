package com.softy.ori.multiplayer.model;

import java.util.List;

public class GameUpdateMessage {

    private List<FoodMessage> relocatedFood;
    private List<PlayerMessage> playerList;
    private List<PlayerMessage> deletedPlayers;

    public List<FoodMessage> getRelocatedFood() {
        return relocatedFood;
    }

    public List<PlayerMessage> getPlayerList() {
        return playerList;
    }

    public List<PlayerMessage> getDeletedPlayers() {
        return deletedPlayers;
    }
}
