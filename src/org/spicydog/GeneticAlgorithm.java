package org.spicydog;

import static org.spicydog.Utility.log;

public class GeneticAlgorithm {


    public static void main(String[] args) {

            int n = Config.nRun;
            int[] resultGeneration = new int[n];
            double[] resultTime = new double[n];
            Individual[] resultIndividual = new Individual[n];

            double sumReliality = 0;
            double sumCost = 0;
            double sumWeight = 0;


            for (int iRun = 0; iRun < n; iRun++) {

                log(String.format("\nRun: %d",iRun+1));

                double fitness = Double.MIN_VALUE;
                double lastFitness = fitness;
                boolean isSolutionFound = false;
                int solutionFoundAtGeneration = -1;

                long startTime = System.nanoTime();
                long endTime = startTime;

                // Create an initial population
                Population myPopulation = new Population(Config.populationSize, true);

                int generationCount = 0;
                for (int iGeneration = 0; iGeneration < Config.maxGeneration; iGeneration++) {
                    generationCount++;
                    fitness = myPopulation.getFittest().getFitness();
                    if(fitness>lastFitness) {
                        endTime = System.nanoTime();
                        isSolutionFound = true;
                        solutionFoundAtGeneration = iGeneration;
                        lastFitness = fitness;
                        log("New solution: " + generationCount + "\t\tFittest: " + String.format("%.6f",fitness) + " *");
                    }
                    if(iGeneration%1000==0) {
                        log("Generation: " + generationCount + "\t\tFittest: " + String.format("%.6f",fitness));
                    }
                    myPopulation = Algorithm.evolvePopulation(myPopulation);
                }

                if(isSolutionFound) {
                    double executionTime = (double)(endTime-startTime)/1e9 ;
                    Individual fittestPopulation = myPopulation.getFittest();
                    log("\nSolution found :)");
                    log("At generation: " + solutionFoundAtGeneration);
                    log("Fitness: " + fittestPopulation.getFitness());
                    log("Reliability: " + fittestPopulation.getReliability());
                    log("Cost: " + fittestPopulation.getCost());
                    log("Weight: " + fittestPopulation.getWeight());
                    log("Execution Time: " + executionTime);
                    log("Genes:");
                    log(Utility.printSystem(fittestPopulation.toBooleans()));

                    resultIndividual[iRun] = fittestPopulation;
                    resultGeneration[iRun] = solutionFoundAtGeneration;
                    resultTime[iRun] = executionTime;

                    sumReliality += fittestPopulation.getReliability();
                    sumCost += fittestPopulation.getCost();
                    sumWeight += fittestPopulation.getWeight();


                } else {
                    log("\nNot found any feasible solution :(");
                    log("Let's try again!");
                    iRun--;

                }
            }

            log(String.format("%.2f %.2f %.2f",Config.alpha, Config.beta, Config.gamma));
            log(Utility.printReport(resultIndividual, resultGeneration, resultTime) );


    }
}
