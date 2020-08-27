package com.softy.oribackend.service;

import com.softy.oribackend.exception.GameNotFoundException;
import com.softy.oribackend.game.Game;
import com.softy.oribackend.game.Player;
import com.softy.oribackend.model.GameConstants;
import com.softy.oribackend.model.message.GameUpdateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryGameProvider implements GameProvider {

    private static final Logger log = LoggerFactory.getLogger(InMemoryGameProvider.class);

    private static AtomicLong idCounter = new AtomicLong(1L);
    private final List<Game> gameList;
    private final SimpMessagingTemplate template;

    @Autowired
    public InMemoryGameProvider(SimpMessagingTemplate template) {
        this.template = template;
        this.gameList = new ArrayList<>();
    }

    @Override
    public synchronized Game findGame() {
        final Optional<Game> optional = gameList.stream()
                .min(Comparator.comparingInt(Game::playersNumber));

        Game game;

        if (optional.isPresent() && !optional.get().isFull())
            game = optional.get();
        else {
            game = createGame();
            gameList.add(game);
            startGame(game);
        }

        return game;
    }

    @Override
    public Game gameById(String id) {
        return gameList.stream()
                .filter(game -> game.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new GameNotFoundException(id));
    }

    @Override
    public void joinGame(String gameId, Player player) {
        final var game = gameById(gameId);

        game.addPlayer(player);
    }

    @Override
    public void quitGame(String gameId, Player player) {
        final var game = gameById(gameId);

        game.removePlayer(player);

        if (game.playersNumber() == 0)
            destroyGame(game);
    }

    @Override
    public List<Game> findAllGames() {
        return Collections.unmodifiableList(gameList);
    }

    private synchronized void destroyGame(Game game) {
        game.setAlive(false);
        gameList.remove(game);
        log.info("Destroyed game [id: " + game.getId() + "]");
    }

    private void notifyPlayers(String gameId, GameUpdateMessage message) {
        template.convertAndSend("/ori/subscribe/" + gameId, message);
    }

    private void startGame(Game game) {
        new Thread(() -> {
            while (game.isAlive()) {
                try {
                    Thread.sleep(1000 / GameConstants.FPS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                final var update = game.run();
                notifyPlayers(game.getId(), update);
            }
        }).start();
    }

    private Game createGame() {
        return new Game(createId());
    }

    private static String createId() {
        return String.valueOf(idCounter.getAndIncrement());
    }
}
