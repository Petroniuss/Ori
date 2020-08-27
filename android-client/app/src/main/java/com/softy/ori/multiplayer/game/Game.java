package com.softy.ori.multiplayer.game;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.softy.ori.multiplayer.model.GameState;
import com.softy.ori.multiplayer.model.GameUpdateMessage;

public class Game {

    private final FoodManager foodManager;
    private final PlayerManager playerManager;
    private final Grid grid;
    private final ViewController viewController;
    private final Activity activity;

    public Game(GameState gameState, Activity context, ViewGroup viewGroup) {
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        this.activity = context;
        this.grid = Grid.init(metrics.widthPixels, metrics.heightPixels);
        this.viewController = GameViewController.init(context, viewGroup);
        this.foodManager = new FoodManager(context, gameState);
        this.playerManager = new PlayerManager(context, gameState, this::onGameOver);

        viewController.attachView(foodManager);
    }

    public void update(GameUpdateMessage updateMessage) {
        final Player player = playerManager.update(
                updateMessage.getPlayerList(), updateMessage.getDeletedPlayers());

        grid.update(player.getAbsolutePosition(), player.getMagnitude());
        foodManager.update(updateMessage.getRelocatedFood());
    }

    //Todo
    private void onGameOver() {
        activity.runOnUiThread(() -> {

        });
    }

}
