package com.softy.ori.multiplayer.game;

import android.app.Activity;

import com.softy.ori.customization.Config;
import com.softy.ori.multiplayer.model.GameState;
import com.softy.ori.multiplayer.model.PlayerMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerManager {

    private final List<Player> playerList;
    private final Activity activity;
    private final Player player;
    private final ViewController viewController;
    private final Runnable onGameOver;

    PlayerManager(Activity activity, GameState state, Runnable onGameOver) {
        this.playerList = state.getPlayerList()
                .stream()
                .map(msg -> Player.fromMessage(msg, activity))
                .collect(Collectors.toList());
        this.player = playerList.stream()
                .filter(p -> p.id().equals(Config.getInstance(activity).getPlayerId()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Not found player!"));

        this.activity = activity;
        this.onGameOver = onGameOver;
        this.viewController = GameViewController.getInstance();

        viewController.attachViews(playerList);
    }

    public Player update(List<PlayerMessage> players, List<PlayerMessage> toDeleteMsg) {
        List<Player> toAttach = new ArrayList<>();
        List<Player> toDelete = toDeleteMsg.stream()
                .map(this::mapToPlayer)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        playerList.removeAll(toDelete);

        players.forEach(msg -> {
            final Optional<Player> mapped = mapToPlayer(msg);
            if (!mapped.isPresent())
                toAttach.add(Player.fromMessage(msg, activity));

            mapped.ifPresent(player -> player.update(msg));
        });

        viewController.attachViews(toAttach);
        viewController.detachViews(toDelete);

        toDelete.forEach(Player::onDeath);
        playerList.forEach(Player::update);
        playerList.addAll(toAttach);

        if(toDelete.contains(player))
            onGameOver.run();

        return player;
    }

    private Optional<Player> mapToPlayer(PlayerMessage msg) {
        return playerList.stream()
                .filter(player -> player.id().equals(msg.getId()))
                .findFirst();
    }

}
