package com.biblioteca.biblioteca.utils;

public class Util {

    public static boolean isLong(String param) {
        try {
            Long.parseLong(param);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
