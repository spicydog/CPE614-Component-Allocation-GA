package org.spicydog;

import static org.spicydog.Utility.log;

public class Main {


    public static void main(String[] args) {
        // Set a candidate solution


        double fittess = 0;
        double lastFittess = fittess;
        boolean isSolutionFound = false;
        int bestGeneration = 0;

        // Create an initial population
        Population myPopulation = new Population(Config.defaultPopulationSize, true);

        // Evolve our population until we reach an optimum solution
        int generationCount = 0;

        for (int i = 0; i < Config.nGeneration; i++) {
            generationCount++;
            fittess = myPopulation.getFittest().getFitness();
            if(fittess>lastFittess) {
                isSolutionFound = true;
                bestGeneration = i;
                lastFittess = fittess;
                log("New solution: " + generationCount + "\t\tFittest: " + String.format("%.6f",fittess) + " *");
            }
            if(i%1000==0)
                log("Generation: " + generationCount + "\t\tFittest: " + String.format("%.6f",fittess));
            myPopulation = Algorithm.evolvePopulation(myPopulation);
        }

        if(isSolutionFound) {
            Individual fittestPopulation = myPopulation.getFittest();
            log("\nSolution found :)");
            log("At generation: " + bestGeneration);
            log("Fitness: " + fittestPopulation.getFitness());
            log("Cost: " + fittestPopulation.getCost());
            log("Genes:");
            log(Utility.printSystem(fittestPopulation.toBooleans()));
        } else {
            log("\nNot found any feasible solution :(");
        }




        //System.out.println(myPopulation.getFittest().toString());

    }
}
