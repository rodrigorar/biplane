package pt.rodrigorar.utils;

public class Validator {

    public static void notNull(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
    }
}
