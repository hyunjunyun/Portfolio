package com;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {


    public static int getInt(int start, int end) {
        return ThreadLocalRandom.current()
                                .nextInt(start, end);
    }

    public static String getFullName() {
        int i = getInt(0, NAMES.length);
        int j = getInt(0, NAMES.length);
        return NAMES[i] + " " + NAMES[j];
    }

    public static final String[] NAMES = {"Florene", "Mckinnon", "Gonzalo", "Shade", "Britany",
            "Villanueva", "Rae", "Dow", "Maragaret", "Mcneely", "Carmelo", "Soares", "Rosita", "Slone",
            "Stan", "Healy", "Samuel", "Dangelo", "Sharron", "Landers", "Hallie", "Weston", "Hollie",
            "Andres", "Steven", "Tang", "Lulu", "Vue", "Claudie", "Hein", "Man", "Singletary", "Ciara",
            "Conover", "Richie", "Stearns", "Sharan", "Free", "Diego", "Hughey", "Kylie", "Batten", "Lady",
            "Belanger", "Ezra", "Ennis", "Denese", "Combs", "Dorinda", "Martindale"};
}