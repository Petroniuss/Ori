package com.softy.ori.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.softy.ori.R;
import com.softy.ori.game.controller.GameEngine;
import com.softy.ori.game.controller.OnDirectionChangedListener;
import com.softy.ori.game.controller.ScoreboardChangedListener;
import com.softy.ori.util.ActivityUtils;
import com.softy.ori.util.IntTuple;
import com.softy.ori.util.Vector;

import java.util.function.Consumer;

public class GameActivity extends AppCompatActivity implements ScoreboardChangedListener {

    private GameEngine engine;
    private OnTouchListener onTouchListener = new OnTouchListener();
    private TextView topPlayersTextView;
    private boolean scoreboardVisible = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        topPlayersTextView = findViewById(R.id.top_players_text_view);
        topPlayersTextView.setOnClickListener((click) -> {
            scoreboardVisible = !scoreboardVisible;
            if (!scoreboardVisible)
                topPlayersTextView.setText("Top Players");
        });

        ActivityUtils.fullscreen(this);
        engine = GameEngine.init(this, findViewById(R.id.game_board_layout), onTouchListener, this);
        engine.start();
    }

    @Override
    protected void onResume() {
        ActivityUtils.fullscreen(this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        engine.stop();
        super.onDestroy();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float absX = event.getX();
        float absY = event.getY();

        IntTuple center = center();

        float x = absX - center.x;
        float y = absY - center.y;

        Vector direction = Vector.create(x, y).normalize(); // This is the direction for player!

        onTouchListener.onTouch(direction);

        return super.onTouchEvent(event);
    }

    @Override
    public void onScoreChanged(String score) {
        if (scoreboardVisible)
            runOnUiThread(() -> topPlayersTextView.setText(score));
    }

    private IntTuple center() {
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        return IntTuple.create(width, height).apply(i -> i / 2);
    }

    private static class OnTouchListener implements OnDirectionChangedListener {

        private Consumer<Vector> onTouch;

        private void onTouch(Vector tuple) {
            if (onTouch != null)
                onTouch.accept(tuple);
        }

        @Override
        public void setOnDirectionChanged(Consumer<Vector> onDirection) {
            this.onTouch = onDirection;
        }
    }

}


