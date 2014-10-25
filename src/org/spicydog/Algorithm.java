package org.spicydog;

/**
 * Created by spicydog on 10/21/14.
 * Based on http://www.theprojectspot.com/tutorial-post/creating-a-genetic-algorithm-for-beginners/3
 */
public class Algorithm {    /* GA parameters */
    private static double uniformRate = Config.defaultUniformRate;
    private static double mutationRate = Config.defaultMutationRate;
    private static int tournamentSize = Config.defaultTournamentSize;
    private static int elitismOffset = Config.defaultElitismOffset;


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
            Individual newIndividual =  mutate(newPopulation.getIndividual(i));
            newIndividual.repair();
        }

        // Random new individual on the worst offspring
        int worstIndex = newPopulation.getSortedFitnessIndex()[newPopulation.size()-1];
        newPopulation.saveIndividual(worstIndex, new Individual());

        return newPopulation;
    }

    // Crossover individuals 1
    private static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();
        if(Math.random() <= Config.defaultCrossoverRate) {
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
    private static Individual mutate(Individual individual) {
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
    private static Individual tournamentSelection(Population pop) {
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
}