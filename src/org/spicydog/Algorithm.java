package org.spicydog;

import static org.spicydog.Utility.log;

/**
 * Created by spicydog on 10/21/14.
 * Based on http://www.theprojectspot.com/tutorial-post/creating-a-genetic-algorithm-for-beginners/3
 */
public class Algorithm {    /* GA parameters */
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
            // Some unknown programming issues make me needs to create new individual to avoid bugs.
            // This is what it should look like "newPopulation.getIndividual(i).mutate();"
            Individual newIndividual = new Individual(newPopulation.getIndividual(i));
            newIndividual.mutate();
            newIndividual.repair();
            newPopulation.saveIndividual(i, newIndividual);
        }


        // Random new individual on the worst offspring
        int[] worstIndexes = newPopulation.getSortedFitnessIndex();
        newPopulation.saveIndividual(worstIndexes[newPopulation.size()-1], new Individual());


        return newPopulation;
    }

    // Crossover individuals 1
    public static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();
        int crossOverPosition = Utility.randomInt(0,newSol.size()/2);

        for (int i = 0; i < indiv1.size()/2; i++) {
            // Crossover
            if(i<crossOverPosition) {
                newSol.setGene(i, indiv1.getGene(i));
                newSol.setGene(i+1, indiv1.getGene(i+1));
            } else {
                newSol.setGene(i, indiv2.getGene(i));
                newSol.setGene(i+1, indiv2.getGene(i+1));
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

        int n = Config.nSubsystem;
        for (int i = 0; i < n; i++) {

            boolean[] hardwareGenes = new boolean[]{individual.getGene(i*4),individual.getGene(i*4+1)};
            int hardwareComponent = Utility.convertBooleanToInt(hardwareGenes);
            if(hardwareComponent>=Config.nHardware) {
                if(Utility.randomBoolean())
                    individual.swapGene(i*4);
                else
                    individual.swapGene(i*4 + 1);
            }
        }

        return individual;

    }
}