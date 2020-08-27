package com.softy.oribackend.controller;

import com.softy.oribackend.game.Game;
import com.softy.oribackend.game.Player;
import com.softy.oribackend.model.handshake.GameState;
import com.softy.oribackend.model.message.PlayerJoinMessage;
import com.softy.oribackend.service.GameProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServerController {

    private final Logger log = LoggerFactory.getLogger(ServerController.class);
    private final GameProvider gameProvider;

    @Autowired
    public ServerController(GameProvider gameProvider) {
        this.gameProvider = gameProvider;
    }

    @GetMapping("/")
    public String home() {
        return ("<body style=\"background-color: black; color: white;\"> \n<pre>" +
                "                                                                         _______                   _____                    _____          \n" +
                "                                                                        /::\\    \\                 /\\    \\                  /\\    \\         \n" +
                "                                                                      /::::\\    \\               /::\\    \\                /::\\    \\        \n" +
                "                                                                      /::::::\\    \\             /::::\\    \\               \\:::\\    \\       \n" +
                "                                                                     /::::::::\\    \\           /::::::\\    \\               \\:::\\    \\      \n" +
                "                                                                    /:::/~~\\:::\\    \\         /:::/\\:::\\    \\               \\:::\\    \\     \n" +
                "                                                                   /:::/    \\:::\\    \\       /:::/__\\:::\\    \\               \\:::\\    \\    \n" +
                "                                                                  /:::/    / \\:::\\    \\     /::::\\   \\:::\\    \\              /::::\\    \\   \n" +
                "                                                                 /:::/____/   \\:::\\____\\   /::::::\\   \\:::\\    \\    ____    /::::::\\    \\  \n" +
                "                                                                |:::|    |     |:::|    | /:::/\\:::\\   \\:::\\____\\  /\\   \\  /:::/\\:::\\    \\ \n" +
                "                                                                |:::|____|     |:::|    |/:::/  \\:::\\   \\:::|    |/::\\   \\/:::/  \\:::\\____\\\n" +
                "                                                                 \\:::\\    \\   /:::/    / \\::/   |::::\\  /:::|____|\\:::\\  /:::/    \\::/    /\n" +
                "                                                                 \\:::\\    \\ /:::/    /   \\/____|:::::\\/:::/    /  \\:::\\/:::/    / \\/____/ \n" +
                "                                                                   \\:::\\    /:::/    /          |:::::::::/    /    \\::::::/    /          \n" +
                "                                                                    \\:::\\__/:::/    /           |::|\\::::/    /      \\::::/____/           \n" +
                "                                                                     \\::::::::/    /            |::| \\::/____/        \\:::\\    \\           \n" +
                "                                                                      \\::::::/    /             |::|  ~|               \\:::\\    \\          \n" +
                "                                                                       \\::::/    /              |::|   |                \\:::\\    \\         \n" +
                "                                                                        \\::/____/               \\::|   |                 \\:::\\____\\        \n" +
                "                                                                         ~~                      \\:|   |                  \\::/    /        \n" +
                "                                                                                                  \\|___|                   \\/____/         \n" +
                "                                                                                                                                          \n" + "\n\n\n" +
                "\n" +
                "   ______                    __             __\n" +
                "  / ____/_____ ___   ____ _ / /_ ___   ____/ /\n" +
                " / /    / ___// _ \\ / __ `// __// _ \\ / __  / \n" +
                "/ /___ / /   /  __// /_/ // /_ /  __// /_/ /  \n" +
                "\\____//_/    \\___/ \\__,_/ \\__/ \\___/ \\__,_/   \n" +
                "                                              \n" +
                "\n" +
                " _            \n" +
                "| |__   _   _ \n" +
                "| '_ \\ | | | |\n" +
                "| |_) || |_| |\n" +
                "|_.__/  \\__, |\n" +
                "        |___/ \n" +
                "\n" +
                "__________          __                    __      __      __            __   __                                    __    \n" +
                "\\______   \\_____  _/  |_ _______  ___.__.|  | __ /  \\    /  \\ ____     |__|_/  |_  ___.__.  ____  ________  ____  |  | __\n" +
                " |     ___/\\__  \\ \\   __\\\\_  __ \\<   |  ||  |/ / \\   \\/\\/   //  _ \\    |  |\\   __\\<   |  |_/ ___\\ \\___   /_/ __ \\ |  |/ /\n" +
                " |    |     / __ \\_|  |   |  | \\/ \\___  ||    <   \\        /(  <_> )   |  | |  |   \\___  |\\  \\___  /    / \\  ___/ |    < \n" +
                " |____|    (____  /|__|   |__|    / ____||__|_ \\   \\__/\\  /  \\____//\\__|  | |__|   / ____| \\___  >/_____ \\ \\___  >|__|_ \\\n" +
                "                \\/                \\/          \\/        \\/         \\______|        \\/          \\/       \\/     \\/      \\/\n" +
                "</pre> </body>").replaceAll("\n", "<br>");
    }


    @GetMapping("/game")
    public ResponseEntity<String> findGame() {
        final Game game = gameProvider.findGame();

        return ResponseEntity.ok(game.getId());
    }

    @PostMapping("/game/{id}/join")
    public ResponseEntity<GameState> join(@PathVariable(value = "id") String gameId, @RequestBody PlayerJoinMessage message) {
        log.info("Player [" + message.getPlayerName() + "] connected to game: [id: " + gameId +"]");
        log.info(message.toString());

        gameProvider.joinGame(gameId, new Player(message.getPlayerName(), message.getPlayerId(), message.getSkinName()));

        return ResponseEntity.ok(GameState.fromGame(gameProvider.gameById(gameId)));
    }

    @GetMapping("/games")
    public ResponseEntity<?> findGames() {
        final List<Game> games = gameProvider.findAllGames();

        return ResponseEntity.ok(games);
    }

}
