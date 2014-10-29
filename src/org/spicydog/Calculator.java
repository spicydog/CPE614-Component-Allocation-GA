package org.spicydog;

/**
 * Created by spicydog on 10/21/14.
 * Based on http://www.theprojectspot.com/tutorial-post/creating-a-genetic-algorithm-for-beginners/3
 */
public class Calculator {

    final private static int nHardware = Config.nHardware;
    final private static int nComponent = Config.nHardware + Config.nSoftware;
    final private static int nEncodingLenght = Config.nHardwareEncodingLength + Config.nSoftwareEncodingLength;

    static boolean isPassConstrain(Individual individual) {
        return individual.getCost() <= Config.maxCost;
    }

    static double getCost(Individual individual) {
        double sumCost = 0;
        int n = Config.nSubsystem;

        for (int i = 0; i < n; i++) {
            boolean[] hardwareGenes = new boolean[]{individual.getGene(i*nEncodingLenght),individual.getGene(i*nEncodingLenght+1)};
            boolean[] softwareGenes = new boolean[]{individual.getGene(i*nEncodingLenght+2),individual.getGene(i*nEncodingLenght+3)};

            int indexHardware = i * nComponent + Utility.convertBooleanToInt(hardwareGenes);
            int indexSoftware = i * nComponent + nHardware + Utility.convertBooleanToInt(softwareGenes);

            sumCost += Config.cost[indexHardware] + Config.cost[indexSoftware];
        }

        return sumCost;
    }

    // Calculate inidividuals fittness by comparing it to our candidate solution
    static double getFitness(Individual individual) {

        // If cost over constrain, use penalty
        double penalty = 1;
        double bonus = 0;
        if(!isPassConstrain(individual)) {
            penalty = 1/Math.pow(10,individual.getCost()-Config.maxCost);
        } else {
            // TODO Find an equation which will make algorithm choose lower cost but same reliability as better fittest
            // bonus = Math.pow(10,(-10)*(Config.maxCost-individual.getCost()));
        }

        double reliability = getReliability(individual);

        return reliability * penalty + bonus;
    }


    static double getReliability(Individual individual) {

        double fitness = 1;

        int n = Config.nSubsystem;

        for (int i = 0; i < n; i++) {
            boolean[] hardwareGenes = new boolean[]{individual.getGene(i*nEncodingLenght),individual.getGene(i*nEncodingLenght+1)};
            boolean[] softwareGenes = new boolean[]{individual.getGene(i*nEncodingLenght+2),individual.getGene(i*nEncodingLenght+3)};

            int indexHardware = i * nComponent + Utility.convertBooleanToInt(hardwareGenes);
            int indexSoftware = i * nComponent + nHardware + Utility.convertBooleanToInt(softwareGenes);

            fitness *= Config.reliability[indexHardware] * Config.reliability[indexSoftware];
        }

        return fitness;
    }



}