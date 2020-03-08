package pt.rodrigorar.utils;

public class Validator {

    public static void notNull(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
    }

    public static void checkState(boolean state) {
        if (! state) {
            throw new IllegalStateException();
        }
    }
}
