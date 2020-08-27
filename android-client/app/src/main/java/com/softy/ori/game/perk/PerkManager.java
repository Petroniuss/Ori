package com.softy.ori.game.perk;

import android.content.Context;

import com.softy.ori.game.Constants;
import com.softy.ori.game.GameObject;
import com.softy.ori.game.controller.GameViewController;
import com.softy.ori.game.player.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 12.09.2019 </i>
 */
public class PerkManager {

    private static final Random random = new Random();

    private final List<PerkObject> perks;
    private final Context context;

    public PerkManager(Context context) {
        perks = new LinkedList<>();
        this.context = context;

        addPerks(Constants.PERK_COUNT);
    }

    public void update(List<? extends Player> players) {
        players.forEach(this::intersects);
        perks.forEach(GameObject::update);
    }

    private void intersects(Player player) {
        List<PerkObject> toRemove = perks.stream()
                .filter(player::intersects)
                .peek(player::addPerk)
                .peek(perk -> perk.attach(player))
                .peek(perk -> GameViewController.getInstance().detachView(perk))
                .collect(Collectors.toList());

        perks.removeAll(toRemove);
        addPerks(toRemove.size());
    }

    private void addPerks(int n) {
        IntStream.range(0, n)
                .mapToObj(i -> randomPerk())
                .peek(perk -> GameViewController.getInstance().attachView(perk))
                .forEach(perks::add);
    }

    private PerkObject randomPerk() {
        final int e = random.nextInt(2);
        PerkObject perk = null;

        if (e == 0) {
            perk = new SpeedPerk(context);
        } else if (e == 1) {
            perk = new SizePerk(context);
        }

        return perk;
    }

}
