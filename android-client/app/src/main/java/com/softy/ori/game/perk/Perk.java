package com.softy.ori.game.perk;

import com.softy.ori.game.player.Player;

/**
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 12.09.2019 </i>
 */
public interface Perk {

    void apply(Player player);

    void attach(Player player);

    boolean isActive();

}
