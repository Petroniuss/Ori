package com.softy.ori.util;

import android.graphics.Color;

import java.util.Random;

/**
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 08.09.2019 </i>
 */
public final class ColorUtils {

    private static final Random random = new Random();

    public static int rainbowColor() {
        final float hue = random.nextFloat() * 360;
        final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
        final float luminance = 1.0f; //1.0 for brighter, 0.0 for black

        return Color.HSVToColor(new float[] {
                hue, saturation, luminance
        });
    }

}
