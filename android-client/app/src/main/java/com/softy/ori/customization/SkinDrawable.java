package com.softy.ori.customization;

import androidx.annotation.DrawableRes;

import com.softy.ori.R;

public enum SkinDrawable {

    TRANSPARENT(R.drawable.transparent, 1.0),
    GITHUB(R.drawable.github_skin_foreground, 1.5),

    //Emoji

    EMOJI_HAPPY(R.drawable.happy_emoji),
    EMOJI_HUSH(R.drawable.hush_emoji),
    EMOJI_EXCITED(R.drawable.excited_emoji),
    EMOJI_SURPRISED(R.drawable.surprise_emoji),

    //-----------AMERICAN-2020-ELECTION-CANDIDATES---

    TRUMP(R.drawable.trump_blinking, .95),
    BERNIE(R.drawable.bernie_smiling, 1.0),
    BIDEN(R.drawable.biden_smiling, 1.0),
    HARRIS(R.drawable.harris, 1.0),
    WARREN(R.drawable.warren,1.0),

    //----------------PINS---------------------------

    PIN_BERNIE_2020(R.drawable.pin_bernie_2020),
    PIN_BERNIE_HAIRSTYLE(R.drawable.pin_bernie_haristyle),
    PIN_ELECTION_2020(R.drawable.pin_election_2020),
    PIN_REPUBLICAN_NOMINEE(R.drawable.pin_republican_nominee),
    PIN_TRUMP_MAKE_EM_CRY(R.drawable.pin_trump_make_em_cry);



    @DrawableRes
    public final int resId;
    public final double scaleFactor;

    SkinDrawable(@DrawableRes int resId) {
        this(resId, 1.0);
    }

    SkinDrawable(@DrawableRes int resId, double scaleFactor) {
        this.resId = resId;
        this.scaleFactor = scaleFactor;
    }

}
