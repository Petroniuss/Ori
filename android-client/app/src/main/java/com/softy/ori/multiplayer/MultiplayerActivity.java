package com.softy.ori.multiplayer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.softy.ori.R;
import com.softy.ori.multiplayer.conectivity.AndroidClient;
import com.softy.ori.multiplayer.game.Game;
import com.softy.ori.multiplayer.game.GameViewController;
import com.softy.ori.multiplayer.model.GameUpdateMessage;
import com.softy.ori.util.IntTuple;
import com.softy.ori.util.Vector;

import java.util.Objects;

import io.reactivex.Observable;

public class MultiplayerActivity extends AppCompatActivity {

    public static final String INTENT_GEY_GAME_ID = "game_id";

    private ViewGroup viewGroup;
    private String gameId;
    private Game game;
    private IntTuple center;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiplayer_game);

        this.gameId = Objects.requireNonNull(getIntent().getExtras()).getString(INTENT_GEY_GAME_ID);
        this.center = center();
        this.viewGroup = findViewById(R.id.mutiplayer_viewgroup);

        startGame();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        GameViewController.getInstance().destroy();
        AndroidClient.getInstance(this).quit(gameId);
        super.onStop();
    }

    @SuppressLint("CheckResult")
    private void startGame() {
        final Observable<GameUpdateMessage> messageObservable =
                AndroidClient.getInstance(this).subscribe(gameId);

        AndroidClient.getInstance(this).join(gameId)
                .subscribe(gameState -> {
                    this.game = new Game(gameState, this, viewGroup);
                    messageObservable.subscribe(msg -> game.update(msg));
                });
    }

    @SuppressLint("DefaultLocale")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float absX = event.getX();
        float absY = event.getY();

        float x = absX - center.x;
        float y = absY - center.y;

        final Vector direction = Vector.create(x, y).normalize(); // This is the direction for player!

        AndroidClient.getInstance(this).updatePlayer(gameId, direction);

        return super.onTouchEvent(event);
    }

    private IntTuple center() {
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        return IntTuple.create(width, height).apply(i -> i / 2);
    }
}
