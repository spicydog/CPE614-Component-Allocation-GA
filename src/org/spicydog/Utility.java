package org.spicydog;

/**
 * Created by spicydog on 10/22/14.
 */

public class Utility {

    public static boolean booleanRandom() {
        int gene = (int) Math.round(Math.random());
        return gene==1;
    }

    public static void log(String msg) {
        System.out.println(msg);
    }

    public static String printSysteom(boolean[] system) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < Config.nSubsystem; i++) {
            for (int j = 0; j < 3; j++) { // Hardware
                int indexHardware = i * 7 + j;
                if(system[indexHardware]) {
                    output.append("1");
                } else {
                    output.append("0");
                }
            }
            output.append(" ");
            for (int k = 0; k < 4; k++) { // Software
                int indexSoftware = i * 7 + 3 + k;
                if(system[indexSoftware]) {
                    output.append("1");
                } else {
                    output.append("0");
                }
            }
            output.append("\n");
        }
        return output.toString();
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
