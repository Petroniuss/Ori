package com.softy.ori.game.controller;

import com.softy.ori.util.Vector;

import java.util.function.Consumer;

public interface OnDirectionChangedListener {

    /**
     * Supplied Vector is always versor.
     */
    void setOnDirectionChanged(Consumer<Vector> onDirectionChanged);

}
