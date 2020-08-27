package com.softy.ori.game.perk;

import android.content.Context;

import com.softy.ori.game.GameObject;
import com.softy.ori.util.Vector;

/**
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 12.09.2019 </i>
 */
public abstract class PerkObject extends GameObject implements Perk {

    public PerkObject(Context context, Vector initPosition) {
        super(context, initPosition);
    }

}
