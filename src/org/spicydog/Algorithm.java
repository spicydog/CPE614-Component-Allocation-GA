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
    private static int eliminateSize = Config.eliminateSize;


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
        for (int i = 0; i < eliminateSize; i++) {
            int worstIndex = newPopulation.getSortedFitnessIndex()[newPopulation.size()-(i+1)];
            newPopulation.saveIndividual(worstIndex, new Individual());
        }

        return newPopulation;
    }

    // Crossover individuals 1
    public static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();

        if(Math.random() <= Config.crossoverRate) {
            int crossoverIndex = Utility.randomInt(0,Config.geneLength-1);
            // Loop through genes
            for (int i = 0; i < indiv1.size(); i++) {
                // Crossover
                if(i<crossoverIndex) {
                    newSol.setGene(i, indiv1.getGene(i));
                } else {
                    newSol.setGene(i, indiv2.getGene(i));
                }
            }
        } else {
            newSol = indiv1.getFitness() > indiv2.getFitness() ? indiv1 : indiv2;
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
        if(Config.enableRepairing) {
            for (int i = 0; i < Config.nSubsystem; i++) {
                int count = 0;
                int subSystemSize = Config.subsystemSizes[i];
                for (int j = 0; j < subSystemSize; j++) {
                    int index = Calculator.index(i, j);
                    if (individual.getGene(index))
                        count++;
                }
                if (count == 0) {
                    int index = Calculator.index(i, Utility.randomInt(0, subSystemSize - 1));
                    individual.setGene(index, true);
                }
            }
        }
        return individual;
    }
}