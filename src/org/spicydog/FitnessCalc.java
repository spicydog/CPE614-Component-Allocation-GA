package org.spicydog;

/**
 * Created by spicydog on 10/21/14.
 * Based on http://www.theprojectspot.com/tutorial-post/creating-a-genetic-algorithm-for-beginners/3
 */
public class FitnessCalc {


    private static boolean[] solution = new boolean[Config.defaultGeneLength];

    /* Public methods */
    // Set a candidate solution as a byte array
    public static void setSolution(boolean[] newSolution) {
        solution = newSolution;
    }

    // Calculate inidividuals fittness by comparing it to our candidate solution
    static double getFitness(Individual individual) {
        double fitness = 0;
        // Loop through our individuals genes and compare them to our cadidates
        for (int i = 0; i < individual.size() && i < solution.length; i++) {
            if (individual.getGene(i) == solution[i]) {
                fitness++;
            }
        }
        return fitness;
    }

    // Get optimum fitness
    static double getMaxFitness() {
        int maxFitness = solution.length;
        return maxFitness;
    }
}