package com.softy.ori.game.player.bot;

import com.softy.ori.game.player.Player;
import com.softy.ori.util.Vector;

/**
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 13.09.2019 </i>
 */
public class NeuralNet implements Brain {

    private final Radar radar;

    public NeuralNet(Radar radar) {
        this.radar = radar;
    }

    @Override
    public Vector predict(Player bot) {
        return Vector.randomVersor();
    }
}
