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

    public Individual() {
        generateIndividual();
    }
    // Create a random individual
    public void generateIndividual() {
        for (int i = 0; i < size(); i++) {
            genes[i] = Utility.booleanRandom();
        }
        this.repair();
        isGeneChanged = true;
        repair();
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

    public void swapGene(int index) {
        this.setGene(index,!this.getGene(index));
    }

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
        int[] counts = new int[this.size()];
        int count;
        int nComponent = Config.nHardware + Config.nSoftware;
        int nHardware = Config.nHardware;
        int nSoftware = Config.nSoftware;
        boolean isShouldRepair = false;
        for (int i = 0; i < Config.nSubsystem; i++) {

            // Hardware
            count = 0;
            for (int j = 0; j < nHardware; j++) {
                int indexHardware = i * nComponent + j;
                boolean isHardwareSelected = this.getGene(indexHardware);
                if(isHardwareSelected)
                    count++;
            }
            for (int j = 0; j < nHardware; j++) {
                int indexHardware = i * nComponent + j;
                counts[indexHardware] = count;
            }
            if(count==0)
                isShouldRepair = true;


            // Software
            count = 0;
            for (int k = 0; k < nSoftware; k++) {
                int indexSoftware = i * nComponent + nHardware + k;
                boolean isSoftwareSelected = this.getGene(indexSoftware);
                if(isSoftwareSelected)
                    count++;
            }
            for (int k = 0; k < nSoftware; k++) { // Software
                int indexSoftware = i * nComponent + nHardware + k;
                counts[indexSoftware] = count;
            }
            if(count==0)
                isShouldRepair = true;
        }

        // There will be 3 cases here
        // 1. Subsystem has no component, we should add a component
        // 2. The cost is to high, we have to reduce components
        // 3. Case 1 occurs then case 2 occurs

        if(FitnessCalc.isPassConstrain(this) && !isShouldRepair)
            return;

        double[] swapRate = new double[]{0.3, 0.0, 0.5, 0.66, 0.75, 0.8};
        for (int i = 0; i < this.size(); i++) {
            double random = Math.random();
            if(swapRate[ counts[i] ] > random) {
                swapGene(i);
            }
        }
    }
}