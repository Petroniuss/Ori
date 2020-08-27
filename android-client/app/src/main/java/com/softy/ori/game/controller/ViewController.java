package com.softy.ori.game.controller;

import android.view.View;

import java.util.Collection;

public interface ViewController {

    void attachView(View view);

    void detachView(View view);

    void attachViews(Collection<? extends View> toAttach);

    void detachViews(Collection<? extends View> toDetach);

    void destroy();

}
