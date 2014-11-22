package org.spicydog;

import com.sun.xml.internal.ws.util.StringUtils;

import static org.spicydog.Utility.log;

public class BruteForce {

    public static void main(String[] args) {


        long startTime = System.nanoTime();

        double maxFitness = Double.MIN_VALUE;
        String bestGene = "";
        for (int i = 0; i < Math.pow(2,Config.geneLength); i++) {
            String genes = Integer.toBinaryString(i);
            String zeros = new String(new char[Config.geneLength-genes.length()]).replace("\0", "0");
            genes = String.format("%s%s",zeros,genes);

            Individual individual = new Individual(Utility.convertStringToBoolean(genes));
            if(individual.getFitness()>maxFitness) {
                maxFitness = individual.getFitness();
                bestGene = genes;
                log(genes + " " + maxFitness);
            }
        }
        long endTime = System.nanoTime();

        Individual individual = new Individual(Utility.convertStringToBoolean(bestGene));
        log("DONE!");
        double executionTime = (double)(endTime-startTime)/1e9;
        log(String.format("Execution Time:%.4f",executionTime));
        log(String.format("fitness: %.6f",individual.getFitness()));
        log(String.format("reliability: %.6f",individual.getReliability()));
        log(String.format("cost: %.0f",individual.getCost()));
        log(String.format("weight: %.0f",individual.getWeight()));
        log(bestGene);

        log(String.format(  "%.6f\t%.6f\t" +
                            "%.0f\t%.0f\t" +
                            "%d\t%.4f\t%s",
                            individual.getFitness(),individual.getReliability(),
                            individual.getCost(), individual.getWeight(),
                            0,executionTime,Utility.printComponentAllocation(individual)));
    }
}
