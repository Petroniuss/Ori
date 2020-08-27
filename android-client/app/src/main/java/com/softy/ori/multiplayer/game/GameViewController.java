package com.softy.ori.multiplayer.game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

public class GameViewController implements ViewController {

    @SuppressLint("StaticFieldLeak")
    private static GameViewController instance;

    private final ViewGroup viewGroup;
    private final Activity activity;

    private GameViewController(Activity activity, ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
        this.activity = activity;
    }

    static GameViewController init(Activity activity, ViewGroup viewGroup) {
        instance = new GameViewController(activity, viewGroup);

        return instance;
    }

    @Override
    public void attachViews(Collection<? extends View> toAttach) {
        activity.runOnUiThread(() -> toAttach.forEach(viewGroup::addView));
    }

    @Override
    public void detachViews(Collection<? extends View> toDelete) {
        activity.runOnUiThread(() -> toDelete.forEach(viewGroup::removeView));
    }

    @Override
    public void attachView(View view) {
        activity.runOnUiThread(() -> viewGroup.addView(view));
    }

    @Override
    public void detachView(View view) {
        activity.runOnUiThread(() -> viewGroup.removeView(view));
    }

    public static ViewController getInstance() {
        if (instance == null)
            throw new AssertionError("GameEngine hasn't started");

        return instance;
    }

    @Override
    public void destroy() {
        instance = null;
    }
}
