package org.spicydog;

/**
 * Created by spicydog on 10/22/14.
 */

public class Utility {

    public static void log(String msg) {
        System.out.println(msg);
    }

    public static boolean[] convertStringToBooleans(String str) {
        int length = str.length();
        boolean[] result = new boolean[length];
        for (int i = 0; i < length; i++) {
            String character = str.substring(i, i + 1);
            result[i] = !character.contentEquals("0");
        }
        return result;
    }

    public static String convertBooleansToString(boolean[] booleans) {
        StringBuilder result = new StringBuilder();
        for (boolean bool : booleans) {
            result.append( bool ? "1":"0" );
        }
        return result.toString();
    }

}
