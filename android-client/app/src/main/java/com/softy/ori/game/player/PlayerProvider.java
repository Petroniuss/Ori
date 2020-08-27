package com.softy.ori.game.player;

import java.util.List;

public interface PlayerProvider {

    List<Player> playersWithinRadius(Player bot, double radius);

}
