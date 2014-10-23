package org.spicydog;

import static org.spicydog.Utility.log;

/**
 * Created by spicydog on 10/21/14.
 * Based on http://www.theprojectspot.com/tutorial-post/creating-a-genetic-algorithm-for-beginners/3
 */
public class FitnessCalc {

    static Individual repairOverCostIndividual(Individual individual) {

        int[] popToRemove = new int[individual.size()];
        int pop;
        for (int i = 0; i < Config.nSubsystem; i++) {

            // Hardware
            pop = 0;
            for (int j = 0; j < 3; j++) {
                int indexHardware = i * 7 + j;
                boolean isHardwareSelected = individual.getGene(indexHardware);
                if(isHardwareSelected)
                    pop++;
            }
            for (int j = 0; j < 3; j++) {
                int indexHardware = i * 7 + j;
                popToRemove[indexHardware] = pop;
            }

            // Software
            pop = 0;
            for (int k = 0; k < 4; k++) {
                int indexSoftware = i * 7 + 3 + k;
                boolean isSoftwareSelected = individual.getGene(indexSoftware);
                if(isSoftwareSelected)
                    pop++;
            }
            for (int k = 0; k < 4; k++) { // Software
                int indexSoftware = i * 7 + 3 + k;
                popToRemove[indexSoftware] = pop;
            }
        }

        for (int i = 0; i < individual.size(); i++) {

            double[] swapRate = {0.3,0.0,0.5,0.66,0.75,0.8};
            double random = Math.random();

            if(swapRate[ popToRemove[i] ] > random) {
                individual.setGene(i, !individual.getGene(i));
            }
        }

        return individual;
    }

    static boolean isPassConstrain(Individual individual) {
        return individual.getCost() <= Config.maxCost;
    }

    // Calculate inidividuals fittness by comparing it to our candidate solution
    static double getFitness(Individual individual) {
        double fitness = 0;
        // Loop through our individuals genes and compare them to our cadidates

        for (int i = 0; i < 50; i++) {
            if(!isPassConstrain(individual)) {
                individual = repairOverCostIndividual(individual);
            }
        }

        if(!isPassConstrain(individual)) {
            return -1;
        }

        double R = 1;
            R = 1;
            for (int i = 0; i < Config.nSubsystem; i++) {

                double Rhw = 1;
                for (int j = 0; j < 3; j++) { // Hardware
                    int indexHardware = i * 7 + j;
                    boolean isHardwareSelected = individual.getGene(indexHardware);
                    if(isHardwareSelected) {
                        double hardwareReliability = Config.reliability[indexHardware];
                        Rhw *= (1-hardwareReliability);
                    }
                }
                Rhw = 1-Rhw;


                double Rsw = 1;
                for (int k = 0; k < 4; k++) { // Software
                    int indexSoftware = i * 7 + 3 + k;
                    boolean isSoftwareSelected = individual.getGene(indexSoftware);
                    if (isSoftwareSelected) {
                        double softwareReliability = Config.reliability[indexSoftware];
                        Rsw *= (1-softwareReliability);
                    }
                }
                Rsw = 1-Rsw;

                double Ri = Rhw * Rsw;
                R *= Ri;
            }

        fitness = R;

        return fitness;
    }

}