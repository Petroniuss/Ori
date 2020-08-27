package com.softy.ori.multiplayer.model;

import java.util.List;

public class GameState {

    private List<PlayerMessage> playerList;
    private List<FoodMessage> foodList;
    private String gameId;

    public List<PlayerMessage> getPlayerList() {
        return playerList;
    }

    public List<FoodMessage> getFoodList() {
        return foodList;
    }

    public String getGameId() {
        return gameId;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "playerList=" + playerList +
                ", foodList=" + foodList +
                ", gameId='" + gameId + '\'' +
                '}';
    }
}
