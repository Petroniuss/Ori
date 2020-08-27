package com.softy.oribackend.util;

import java.util.Random;

public final class RandomUtils {

    private final static Random random = new Random();

    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public static float nextFloat() {
        return random.nextFloat();
    }
}
