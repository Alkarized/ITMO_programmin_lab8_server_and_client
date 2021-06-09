package utils;

import fields.Flat;

public class FlatGetter {
    private static Flat flat;

    public static Flat getFlat() {
        return flat;
    }

    public static void setFlat(Flat flat) {
        FlatGetter.flat = flat;
    }
}
