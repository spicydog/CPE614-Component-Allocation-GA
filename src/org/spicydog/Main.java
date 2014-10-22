package org.spicydog;

import static org.spicydog.Utility.log;

public class Main {


    public static void main(String[] args) {
        // Set a candidate solution

        FitnessCalc.setSolution(Utility.convertStringToBooleans(Config.solution));

        // Create an initial population
        Population myPop = new Population(Config.defaultPopulationSize, true);

        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
            generationCount++;
            log("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
            myPop = Algorithm.evolvePopulation(myPop);
        }
        log("Solution found!");
        log("Generation: " + generationCount);
        log("Genes:");
        System.out.println( myPop.getFittest().toString() );

    }
}
