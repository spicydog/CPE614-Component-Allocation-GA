package org.spicydog;

import static org.spicydog.Utility.log;

/**
 * Created by spicydog on 10/21/14.
 * Based on http://www.theprojectspot.com/tutorial-post/creating-a-genetic-algorithm-for-beginners/3
 */
public class FitnessCalc {

    static boolean isPassConstrain(Individual individual) {
        return individual.getCost() <= Config.maxCost;
    }

    // Calculate inidividuals fittness by comparing it to our candidate solution
    static double getFitness(Individual individual) {
        double fitness = 0;
        // Loop through our individuals genes and compare them to our cadidates


        if(!isPassConstrain(individual)) {
            return 0;
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