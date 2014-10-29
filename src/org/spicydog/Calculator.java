package org.spicydog;

/**
 * Created by spicydog on 10/21/14.
 * Based on http://www.theprojectspot.com/tutorial-post/creating-a-genetic-algorithm-for-beginners/3
 */
public class Calculator {

    static boolean isPassConstrain(Individual individual) {
        return individual.getCost() <= Config.maxCost;
    }

    static double getCost(Individual individual) {
        double sumCost = 0;
        int n = Config.nSubsystem;

        for (int i = 0; i < n; i++) {
            boolean[] hardwareGenes = new boolean[]{individual.getGene(i*4),individual.getGene(i*4+1)};
            boolean[] softwareGenes = new boolean[]{individual.getGene(i*4+2),individual.getGene(i*4+3)};

            int indexHardware = i * 7 + Utility.convertBooleanToInt(hardwareGenes);
            int indexSoftware = i * 7 + 3 + Utility.convertBooleanToInt(softwareGenes);

            sumCost += Config.cost[indexHardware] + Config.cost[indexSoftware];
        }

        return sumCost;
    }

    // Calculate inidividuals fittness by comparing it to our candidate solution
    static double getFitness(Individual individual) {

        double fitness = 1;

        // If cost over constrain, use penalty
        double penalty = 1;
        if(!isPassConstrain(individual)) {
            penalty = 1/Math.pow(10,individual.getCost()-Config.maxCost);
        }


        int n = Config.nSubsystem;

        for (int i = 0; i < n; i++) {
            boolean[] hardwareGenes = new boolean[]{individual.getGene(i*4),individual.getGene(i*4+1)};
            boolean[] softwareGenes = new boolean[]{individual.getGene(i*4+2),individual.getGene(i*4+3)};

            int indexHardware = i * 7 + Utility.convertBooleanToInt(hardwareGenes);
            int indexSoftware = i * 7 + 3 + Utility.convertBooleanToInt(softwareGenes);

            fitness *= Config.reliability[indexHardware] * Config.reliability[indexSoftware];
        }

        return fitness * penalty;
    }

}