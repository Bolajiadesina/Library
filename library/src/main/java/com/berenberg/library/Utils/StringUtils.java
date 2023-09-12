package com.berenberg.library.Utils;

/**
 * @author Bolaji
 * created on 05/09/2023
 */
public class StringUtils {


    public static boolean isEmptyBlank(String text) {

        return text == null || text.isBlank() || text.isEmpty();
    }
}
