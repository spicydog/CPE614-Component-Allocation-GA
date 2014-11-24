package org.spicydog;

import java.util.Random;

/**
 * Created by spicydog on 10/22/14.
 */

public class Utility {

    final private static int nEncodingLenght = Config.nHardwareEncodingLength + Config.nSoftwareEncodingLength;

    public static boolean randomBoolean() {
        int gene = randomInt(0, 1);
        return gene==1;
    }

    public static int randomInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
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
                    i + 1, individuals[i].getReliability(),
                    individuals[i].getCost(), printComponentAllocation(individuals[i]),
                    generations[i], times[i]);
        }

        return result;

    }

    public static String printComponentAllocation(Individual individual) {
        String result = "[";
        int n = Config.nSubsystem;

        for (int i = 0; i < n; i++) {
            boolean[] hardwareGenes = new boolean[]{individual.getGene(i*nEncodingLenght),individual.getGene(i*nEncodingLenght+1)};
            boolean[] softwareGenes = new boolean[]{individual.getGene(i*nEncodingLenght+2),individual.getGene(i*nEncodingLenght+3)};

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
