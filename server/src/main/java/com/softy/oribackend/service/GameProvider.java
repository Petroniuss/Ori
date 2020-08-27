package com.softy.oribackend.service;

import com.softy.oribackend.game.Game;
import com.softy.oribackend.game.Player;

import java.util.List;

public interface GameProvider {

    List<Game> findAllGames();

    Game findGame();

    Game gameById(String id);

    void joinGame(String gameId, Player player);

    void quitGame(String gameId, Player player);

}
