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
    private boolean isGeneChanged;

    // Create a random individual
    public void generateIndividual() {
        for (int i = 0; i < size(); i++) {
            genes[i] = Utility.booleanRandom();
        }
        this.repair();
        isGeneChanged = true;
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
        isGeneChanged = true;
    }

    /* Public methods */
    public int size() {
        return genes.length;
    }

    public double getFitness() {
        if (isGeneChanged) {
            fitness = FitnessCalc.getFitness(this);
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

        int[] popToRemove = new int[this.size()];
        int pop;
        for (int i = 0; i < Config.nSubsystem; i++) {

            // Hardware
            pop = 0;
            for (int j = 0; j < 3; j++) {
                int indexHardware = i * 7 + j;
                boolean isHardwareSelected = this.getGene(indexHardware);
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
                boolean isSoftwareSelected = this.getGene(indexSoftware);
                if(isSoftwareSelected)
                    pop++;
            }
            for (int k = 0; k < 4; k++) { // Software
                int indexSoftware = i * 7 + 3 + k;
                popToRemove[indexSoftware] = pop;
            }
        }


        double[] swapRate;
        if(this.getCost()<Config.maxCost)
            swapRate = new double[]{0.3, 0.0, 0.5, 0.66, 0.75, 0.8};
        else
            swapRate = new double[]{0.3, 0.0, 0.5, 0.66, 0.75, 0.8};

        for (int i = 0; i < this.size(); i++) {

            double random = Math.random();

            if(swapRate[ popToRemove[i] ] > random) {
                this.setGene(i, !this.getGene(i));
            }
        }
    }
}