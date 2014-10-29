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
                        "Cost(x)\tComponent Allocation\t" +
                        "Generation\tComputation Time (s)\n";
        for (int i = 0; i < n; i++) {
            result += String.format("%d\t%.6f\t" +
                            "%.2f\t%s\t" +
                            "%d\t%.6f\n",
                    i + 1, individuals[i].getFitness(),
                    individuals[i].getCost(), printComponentAllocation(individuals[i]),
                    generations[i], times[i]);
        }

        return result;

    }

    public static String printComponentAllocation(Individual individual) {
        String result = "[";
        int n = Config.nSubsystem;

        for (int i = 0; i < n; i++) {
            boolean[] hardwareGenes = new boolean[]{individual.getGene(i*4),individual.getGene(i*4+1)};
            boolean[] softwareGenes = new boolean[]{individual.getGene(i*4+2),individual.getGene(i*4+3)};

            int selectedHardware = Utility.convertBooleanToInt(hardwareGenes);
            int selectedSoftware = Utility.convertBooleanToInt(softwareGenes);

            result += String.format("S%d:{H%d,V%d}, ",i+1,selectedHardware+1,selectedSoftware+1);
        }
        result += "]";

        result = result.replace(", ]","]");
        result = result.replace(", }","}");

        return result;
    }

    public static String printSystem(Individual individual) {

        String result = "";
        int n = Config.nSubsystem;

        for (int i = 0; i < n; i++) {
            boolean[] hardwareGenes = new boolean[]{individual.getGene(i*4),individual.getGene(i*4+1)};
            boolean[] softwareGenes = new boolean[]{individual.getGene(i*4+2),individual.getGene(i*4+3)};

            int selectedHardware = Utility.convertBooleanToInt(hardwareGenes);
            int selectedSoftware = Utility.convertBooleanToInt(softwareGenes);

            result += String.format("S%d: H:%d V:%d\n",i+1,selectedHardware+1,selectedSoftware+1);
        }

        return result;

    }


    public static int convertBooleanToInt(boolean[] bool) {
        int sum = 0;
        for (int i = bool.length-1; i >= 0; i--) {
            if(bool[i])
                sum += (int)Math.pow(2,i);
        }
        return sum;
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

}
