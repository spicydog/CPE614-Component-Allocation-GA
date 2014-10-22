package org.spicydog;

import static org.spicydog.Utility.log;

/**
 * Created by spicydog on 10/21/14.
 * Based on http://www.theprojectspot.com/tutorial-post/creating-a-genetic-algorithm-for-beginners/3
 */
public class FitnessCalc {


    //private static boolean[] solution = new boolean[Config.defaultGeneLength];

    /* Public methods */
    // Set a candidate solution as a byte array
    public static void setSolution(boolean[] newSolution) {
        //solution = newSolution;
    }

    // Calculate inidividuals fittness by comparing it to our candidate solution
    static double getFitness(Individual individual) {
        double fitness = 0;
        // Loop through our individuals genes and compare them to our cadidates

        double sumCost = 0;
        for (int i = 0; i < Config.defaultGeneLength; i++) {
            double cost = 0;
            if(individual.getGene(i))
                cost = Config.cost[i];
            sumCost += cost;
        }

        double R = 0;
        if(sumCost<Config.maxCost) {
            R = 1;
            for (int i = 0; i < Config.nSubsystem; i++) {
                double Ri = 0;
                for (int j = 0; j < 3; j++) { // Hardware
                    for (int k = 0; k < 4; k++) { // Software
                        int indexHardware = i * 7 + j;
                        int indexSoftware = i * 7 + 3 + k;
                        boolean isHardwareSelected = individual.getGene(indexHardware);
                        boolean isSoftwareSelected = individual.getGene(indexSoftware);
                        if (isHardwareSelected && isSoftwareSelected) {
                            Ri += Config.reliability[indexHardware] * Config.reliability[indexSoftware];
                        }
                    }
                    log("xxx"+Ri);
                }
                R *= Ri;
            }
        } else {
            return 0;
        }
        fitness = R;

        return fitness;
    }

    // Get optimum fitness
    static double getMaxFitness() {
        int maxFitness = Integer.MAX_VALUE;
        return maxFitness;
    }
}