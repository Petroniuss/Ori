package com.softy.ori.game.player.bot;

import android.util.Log;

import com.softy.ori.game.player.Player;
import com.softy.ori.util.Vector;

/**
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 17.09.2019 </i>
 */
public class SimpleBrain implements Brain {

    private final Radar radar;

    public SimpleBrain(Radar radar) {
        this.radar = radar;
    }

    @Override
    public Vector predict(Player bot) {
        int min = 0;

        double[] input = radar.input(bot);

        for (int i = 1; i < 18; i++) {
            if (input[i] != -1 && (input[min] > input[i] || input[min] == -1))
                min = i;
        }

        double theta = min * ModelConstants.THETA  * Math.PI / (180);

        Vector vector = Vector.fromAngle((2 * Math.PI) - theta);

        Log.d("BOT", String.format("Theta: %f, %s", theta, vector.toString()));

        return vector;
    }
}
