package org.spicydog;

import java.util.Random;

/**
 * Created by spicydog on 10/22/14.
 */

public class Utility {

    public static boolean randomBoolean() {
        int gene = randomInt(0, 1);
        return gene==1;
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randomInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }


    public static void log(String msg) {
        System.out.println(msg);
    }



    public static String printReport(Individual[] individuals, int[] generations, double[] times) {
        int n = individuals.length;
        String result = "Run\tReliability(x)\t" +
                        "Cost(x)\tGeneration\t" +
                        "Computation Time (s)\tComponent Allocation\n";
        for (int i = 0; i < n; i++) {
            result += String.format("%d\t%.6f\t" +
                            "%.2f\t%s\t" +
                            "%d\t%.6f\n",
                    i + 1, individuals[i].getReliability(),
                    individuals[i].getCost(), printComponentAllocation(individuals[i]),
                    generations[i], times[i]);
        }

        return result;

    }

    public static String printComponentAllocation(Individual individual) {
        String result = "[";

        for (int i = 0; i < Config.nSubsystem; i++) {
            result += String.format("%d:{",i);
            for (int j = 0; j < Config.subsystemSizes[i]; j++) {
                int index = Calculator.index(i,j);
                if(individual.getGene(index))
                    result += String.format("%d,",j);
            }
            result += "},";
        }

        result += "]";

        result = result.replace(",]","]");
        result = result.replace(",}","}");

        return result;
    }

    public static String printSystem(boolean[] system) {


        StringBuilder output = new StringBuilder();
        for (int i = 0; i < Config.nSubsystem; i++) {
            for (int j = 0; j < Config.subsystemSizes[i]; j++) {
                int index = Calculator.index(i,j);
                output.append( system[index]?"1":"0");
            }
            output.append(" ");
        }
        output.append("\n");

        return output.toString();
    }

    public static boolean[] convertStringToBoolean(String str) {
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

    public static int sumArray(int[] values) {
        int sum = 0;
        for(int value : values) {
            sum += value;
        }
        return sum;
    }

    public static double sumArray(double[] values) {
        double sum = 0;
        for(double value : values) {
            sum += value;
        }
        return sum;
    }

}
