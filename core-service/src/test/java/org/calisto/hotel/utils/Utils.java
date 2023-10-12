package org.calisto.hotel.utils;

import java.util.Random;

public final class Utils {
    private static final Random random = new Random();

    public static int generateRandomNumber(int origin, int bound) {
        return random.nextInt(origin, bound);
    }
}
