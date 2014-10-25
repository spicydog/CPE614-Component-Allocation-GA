package org.spicydog;

/**
 * Created by spicydog on 10/21/14.
 * Based on http://www.theprojectspot.com/tutorial-post/creating-a-genetic-algorithm-for-beginners/3
 */
public class Individual {

    static int defaultGeneLength = Config.geneLength;

    private boolean[] genes = new boolean[defaultGeneLength];
    // Cache
    private double fitness = 0;
    private boolean isGeneChanged;

    public Individual() {
        generateIndividual();
    }

    public Individual(Individual individual) {
        for (int i = 0; i < size(); i++) {
            setGene(i, individual.getGene(i));
        }
    }

    // Create a random individual
    public void generateIndividual() {
        for (int i = 0; i < size(); i++) {
            genes[i] = Utility.randomBoolean();
        }
        this.repair();
        isGeneChanged = true;
        repair();
    }

    public boolean getGene(int index) {
        return genes[index];
    }

    public double getCost() {
        return Calculator.getCost(this);
    }

    public void setGene(int index, boolean value) {
        genes[index] = value;
        isGeneChanged = true;
    }

    public void swapGene(int index) {
        setGene(index, !getGene(index));
    }

    public int size() {
        return genes.length;
    }

    public double getFitness() {
        if (isGeneChanged) {
            fitness = Calculator.getFitness(this);
            isGeneChanged = false;
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

    public void repair() {
        Algorithm.repair(this);
    }
}