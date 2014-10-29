package org.spicydog;

import static org.spicydog.Utility.log;

/**
 * Created by spicydog on 10/21/14.
 * Based on http://www.theprojectspot.com/tutorial-post/creating-a-genetic-algorithm-for-beginners/3
 */
public class Population {

    Individual[] individuals;

    /*
     * Constructors
     */
    // Create a population
    public Population(int populationSize, boolean initialise) {
        individuals = new Individual[populationSize];
        // Initialise population
        if (initialise) {
            // Loop and create individuals
            for (int i = 0; i < size(); i++) {
                Individual newIndividual = new Individual();
                saveIndividual(i, newIndividual);
            }
        }
    }

    /* Getters */
    public Individual getIndividual(int index) {
        return individuals[index];
    }

    // Get the best individual
    public Individual getFittest() {
        int[] sortedIndexes = getSortedFitnessIndex();
        return individuals[sortedIndexes[0]];
    }

    // Sort fitness index
    public int[] getSortedFitnessIndex() {

        int[] sortedIndexes = new int[size()];
        double[] fitnessValues = new double[size()];
        for (int i = 0; i < size(); i++) {
            fitnessValues[i] = individuals[i].getFitness();
            sortedIndexes[i] = i;
        }

        for (int i = 0; i < size(); i++) {
            for (int j = i+1; j < size(); j++) {
                double fitnessI = fitnessValues[sortedIndexes[i]];
                double fitnessJ = fitnessValues[sortedIndexes[j]];
                if( fitnessI < fitnessJ ) {
                    int swap = sortedIndexes[i];
                    sortedIndexes[i] = sortedIndexes[j];
                    sortedIndexes[j] = swap;
                }
            }
        }

        return sortedIndexes;
    }

    // Get population size
    public int size() {
        return individuals.length;
    }

    // Save individual
    public void saveIndividual(int index, Individual individual) {
        individuals[index] = individual;
    }
}