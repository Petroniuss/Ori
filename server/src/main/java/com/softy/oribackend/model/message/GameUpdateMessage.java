package com.softy.oribackend.model.message;

import com.softy.oribackend.game.Food;
import com.softy.oribackend.game.Player;

import java.util.List;

public class GameUpdateMessage {

    private final List<Food> relocatedFood;
    private final List<Player> playerList;
    private final List<Player> deletedPlayers;

    public GameUpdateMessage(List<Food> relocatedFood, List<Player> playerList, List<Player> deletedPlayers) {
        this.relocatedFood = relocatedFood;
        this.playerList = playerList;
        this.deletedPlayers = deletedPlayers;
    }

    public List<Food> getRelocatedFood() {
        return relocatedFood;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<Player> getDeletedPlayers() {
        return deletedPlayers;
    }

}
