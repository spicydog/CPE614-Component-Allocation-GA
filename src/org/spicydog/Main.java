package org.spicydog;

import static org.spicydog.Utility.log;

public class Main {


    public static void main(String[] args) {
        // Set a candidate solution

        //FitnessCalc.setSolution(Utility.convertStringToBooleans(Config.solution));

        double fitness = 0;
        double lastFitness = fitness;
        int bestGeneration = 0;
        int r = 0;

        // Create an initial population
        Population myPop = new Population(Config.defaultPopulationSize, true);

        // Evolve our population until we reach an optimum solution
        int generationCount = 0;

        for (int i = 0; i < Config.nGeneration; i++) {
            generationCount++;
            fitness = myPop.getFittest().getFitness();
            if(lastFitness!=fitness) {
                bestGeneration = i;
                lastFitness = fitness;
                log("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
            }
            myPop = Algorithm.evolvePopulation(myPop);
        }

        log("Run: " + ++r);
        log("Generation: " + bestGeneration);
        log("Genes:");
        Individual fittnestPopulation = myPop.getFittest();
        log(Utility.printSysteom(fittnestPopulation.toBooleans()));
        log("Fitness: " + fittnestPopulation.getFitness());
        log("Cost: " + fittnestPopulation.getCost());



        //System.out.println(myPop.getFittest().toString());

    }
}
