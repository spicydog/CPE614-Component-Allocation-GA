package org.spicydog;

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
                newIndividual.generateIndividual();
                saveIndividual(i, newIndividual);
            }
        }
    }

    /* Getters */
    public Individual getIndividual(int index) {
        return individuals[index];
    }


    public Individual getFittest() {
        return getFittest(1)[0];
    }
    public Individual[] getFittest(int nIndividuals) {

        double[] fitnessValues = new double[size()];
        for (int i = 0; i < size(); i++) {
            fitnessValues[i] = individuals[i].getFitness();
        }

        Individual[] selectedIndividuals = new Individual[nIndividuals];

        for (int i = 0; i < nIndividuals; i++) {
            double max = Integer.MIN_VALUE;
            int index = 0;
            for (int j = 0; j < size(); j++) {
                if(fitnessValues[j] > max) {
                    max = fitnessValues[j];
                    index = j;
                    fitnessValues[j] = Integer.MIN_VALUE;
                }
            }
            selectedIndividuals[i] = individuals[index];
        }

        return selectedIndividuals;
    }

    /* Public methods */
    // Get population size
    public int size() {
        return individuals.length;
    }

    // Save individual
    public void saveIndividual(int index, Individual individual) {
        individuals[index] = individual;
    }
}