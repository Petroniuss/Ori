package com.softy.ori.game.player.bot;

import com.softy.ori.game.player.Player;
import com.softy.ori.util.Vector;

/**
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 13.09.2019 </i>
 */
public interface Brain {

    /**
     * @param bot 54 normalized values. (As per current model)
     * @return predicted direction (versor)
     */
    Vector predict(Player bot);

}
