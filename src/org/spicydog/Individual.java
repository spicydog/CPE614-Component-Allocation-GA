package org.spicydog;

import static org.spicydog.Utility.log;

/**
 * Created by spicydog on 10/21/14.
 * Based on http://www.theprojectspot.com/tutorial-post/creating-a-genetic-algorithm-for-beginners/3
 */
public class Individual {

    static int defaultGeneLength = Config.defaultGeneLength;

    private boolean[] genes = new boolean[defaultGeneLength];
    // Cache
    private double fitness = 0;

    // Create a random individual
    public void generateIndividual() {
        for (int i = 0; i < size(); i++) {
            genes[i] = Utility.booleanRandom();
        }
    }

    /* Getters and setters */
    // Use this if you want to create individuals with different gene lengths
    public static void setDefaultGeneLength(int length) {
        defaultGeneLength = length;
    }

    public boolean getGene(int index) {
        return genes[index];
    }

    public double getCost() {
        double sumCost = 0;
        for (int i = 0; i < Config.defaultGeneLength; i++) {
            if(this.getGene(i))
                sumCost += Config.cost[i];
        }
        return sumCost;
    }

    public void setGene(int index, boolean value) {
        genes[index] = value;
        fitness = 0;
    }

    /* Public methods */
    public int size() {
        return genes.length;
    }

    public double getFitness() {
        if (fitness == 0) {
            fitness = FitnessCalc.getFitness(this);
        }
        return fitness;
    }

    @Override
    public String toString() {
        StringBuilder geneString = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            geneString.append(getGene(i)?"1":"0");
        }
        return geneString.toString();
    }

    public boolean[] toBooleans() {
        boolean[] result = new boolean[size()];
        for (int i = 0; i < size(); i++) {
            result[i] = getGene(i);
        }
        return result;
    }
}