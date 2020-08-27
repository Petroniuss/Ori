package com.softy.ori.customization;

import java.util.Arrays;
import java.util.List;

import static com.softy.ori.customization.SkinDrawable.*;

public enum Theme {

    FREE(TRANSPARENT, GITHUB),
    EMOJI(EMOJI_EXCITED, EMOJI_HAPPY, EMOJI_HUSH, EMOJI_SURPRISED),
    AMERICAN_2020_ELECTION_CANDIDATES(SkinDrawable.TRUMP, BERNIE, BIDEN, WARREN, HARRIS),
    AMERICAN_2020_ELECTION_PINS(PIN_TRUMP_MAKE_EM_CRY, PIN_BERNIE_2020, PIN_BERNIE_HAIRSTYLE, PIN_ELECTION_2020, PIN_REPUBLICAN_NOMINEE);

    Theme(SkinDrawable... skins) {
        this.skins = Arrays.asList(skins);
    }

    final List<SkinDrawable> skins;

}
