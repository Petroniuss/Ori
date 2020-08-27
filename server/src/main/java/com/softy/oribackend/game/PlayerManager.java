package com.softy.oribackend.game;

import com.softy.oribackend.exception.PlayerNotFoundException;
import com.softy.oribackend.util.Tuple;
import com.softy.oribackend.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {

    private final List<Player> playerList;

    PlayerManager() {
        playerList = new ArrayList<>();
    }

    synchronized Tuple<List<Player>, List<Player>> update() {
        List<Player> losers = new ArrayList<>();

        for (int i = 0; i < playerList.size() - 1; i++) {
            final Player a = playerList.get(i);
            for (int j = i; j < playerList.size(); j++) {
                final Player b = playerList.get(j);

                if (a.intersects(b) || b.intersects(a)) {
                    if (a.getRadius() > b.getRadius()) {
                        a.grow(b.getRadius());
                        losers.add(b);
                    } else if (a.getRadius() < b.getRadius()) {
                        b.grow(a.getRadius());
                        losers.add(a);
                    }
                }
            }
        }

        playerList.removeAll(losers);
        playerList.forEach(Player::update);

        return Tuple.create(playerList, losers);
    }

    synchronized void addPlayer(Player player) {
        playerList.add(player);
    }

    synchronized void removePlayer(Player player) {
        playerList.remove(player);
    }

    int playersNumber() {
        return playerList.size();
    }

     List<Player> getPlayers() {
        return playerList;
    }

     synchronized void updatePlayer(String playerId, Vector direction) {
        playerList.stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException(playerId))
                .setDirection(direction);
    }
}
