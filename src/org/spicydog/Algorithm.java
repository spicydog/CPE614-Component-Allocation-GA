package org.spicydog;

import static org.spicydog.Utility.log;

/**
 * Created by spicydog on 10/21/14.
 * Based on http://www.theprojectspot.com/tutorial-post/creating-a-genetic-algorithm-for-beginners/3
 */
public class Algorithm {    /* GA parameters */
    private static double uniformRate = Config.crossoverUniformRate;
    private static double mutationRate = Config.mutationRate;
    private static int tournamentSize = Config.tournamentSize;
    private static int elitismOffset = Config.elitismSize;


    /* Public methods */

    // Evolve a population
    public static Population evolvePopulation(Population population) {
        Population newPopulation = new Population(population.size(), false);

        // Keep our best individuals
        int[] sortedIndex = population.getSortedFitnessIndex();
        for (int i = 0; i < elitismOffset; i++) {
            newPopulation.saveIndividual(i, population.getIndividual(sortedIndex[i]));
        }

        // Crossover population
        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            Individual individual1 = tournamentSelection(population);
            Individual individual2 = tournamentSelection(population);
            Individual newIndividual = crossover(individual1, individual2);
            newIndividual.repair();
            newPopulation.saveIndividual(i, newIndividual);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            Individual newIndividual = new Individual(newPopulation.getIndividual(i));
            newIndividual.mutate();
            newIndividual.repair();
            newPopulation.saveIndividual(i, newIndividual);
        }

        // Random new individual on the worst offspring
        int worstIndex = newPopulation.getSortedFitnessIndex()[newPopulation.size()-1];
        newPopulation.saveIndividual(worstIndex, new Individual());

        return newPopulation;
    }

    // Crossover individuals 1
    public static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();
        if(Math.random() <= Config.crossoverRate) {
            // Loop through genes
            for (int i = 0; i < indiv1.size(); i++) {
                // Crossover
                if (Math.random() <= uniformRate) {
                    newSol.setGene(i, indiv1.getGene(i));
                } else {
                    newSol.setGene(i, indiv2.getGene(i));
                }
            }
        } else {
            double fitness1 = indiv1.getFitness();
            double fitness2 = indiv2.getFitness();
            if( fitness1 > fitness2 ) {
                newSol = indiv1;
            } else if( fitness1 < fitness2) {
                newSol= indiv2;
            } else {
                newSol = indiv1.getCost() < indiv2.getCost() ? indiv1 : indiv2;
            }
        }
        return newSol;
    }


    // Mutate an individual
    public static Individual mutate(Individual individual) {
        // Loop through genes
        for (int i = 0; i < individual.size(); i++) {
            if (Math.random() <= mutationRate) {
                // Create random gene
                individual.swapGene(i);
            }
        }
        return individual;
    }

    // Select individuals for crossover
    public static Individual tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = Utility.randomInt(0, pop.size() - 1);
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }
        // Get the fittest
        return tournament.getFittest();
    }


    public static Individual repair(Individual individual) {

//        int[] counts = new int[individual.size()];
//        int count;
//        int nComponent = Config.nHardware + Config.nSoftware;
//        int nHardware = Config.nHardware;
//        int nSoftware = Config.nSoftware;
//        boolean isShouldRepair = false;
//        for (int i = 0; i < Config.nSubsystem; i++) {
//            // Hardware
//            count = 0;
//            for (int j = 0; j < nHardware; j++) {
//                int indexHardware = i * nComponent + j;
//                boolean isHardwareSelected = individual.getGene(indexHardware);
//                if(isHardwareSelected)
//                    count++;
//            }
//            for (int j = 0; j < nHardware; j++) {
//                int indexHardware = i * nComponent + j;
//                counts[indexHardware] = count;
//            }
//            if(count==0)
//                isShouldRepair = true;
//
//
//            // Software
//            count = 0;
//            for (int k = 0; k < nSoftware; k++) {
//                int indexSoftware = i * nComponent + nHardware + k;
//                boolean isSoftwareSelected = individual.getGene(indexSoftware);
//                if(isSoftwareSelected)
//                    count++;
//            }
//            for (int k = 0; k < nSoftware; k++) { // Software
//                int indexSoftware = i * nComponent + nHardware + k;
//                counts[indexSoftware] = count;
//            }
//            if(count==0)
//                isShouldRepair = true;
//        }
//
//        // There will be 3 cases here
//        // 1. Subsystem has no component, we should add a component
//        // 2. The cost is to high, we have to reduce components
//        // 3. Case 1 occurs then case 2 occurs
//
//        if(Calculator.isPassConstrain(individual) && !isShouldRepair) {
//            return individual;
//        } else {
//            double repairRate;
//            for (int i = 0; i < Config.nSubsystem; i++) {
//                // Hardware
//                for (int j = 0; j < nHardware; j++) {
//                    int indexHardware = i * nComponent + j;
//                    if(counts[indexHardware]==0) {
//                        repairRate = 1 / (double)Config.nHardware;
//                    } else if( counts[indexHardware]>=2) {
//                        repairRate = 1-1/(double)counts[indexHardware];
//                    } else {
//                        repairRate = 0;
//                    }
//                    if(Math.random() < repairRate) {
//                        individual.swapGene(indexHardware);
//                    }
//                }
//                // Software
//                for (int k = 0; k < nSoftware; k++) { // Software
//                    int indexSoftware = i * nComponent + nHardware + k;
//                    if(counts[indexSoftware]==0) {
//                        repairRate = 1 / (double)Config.nSoftware;
//                    } else if( counts[indexSoftware]>=2) {
//                        repairRate = 1-1/(double)counts[indexSoftware];
//                    } else {
//                        repairRate = 0;
//                    }
//                    if(Math.random() < repairRate) {
//                        individual.swapGene(indexSoftware);
//                    }
//                }
//            }
//        }
        return individual;

    }
}