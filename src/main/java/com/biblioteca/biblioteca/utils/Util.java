package com.biblioteca.biblioteca.utils;

public class Util {

    public static final float VALOR_MULTA = 4.50F;

    public static boolean isLong(String param) {
        try {
            Long.parseLong(param);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
