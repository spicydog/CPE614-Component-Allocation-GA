package org.spicydog;

import static org.spicydog.Utility.log;

public class Main {


    public static void main(String[] args) {

        int n = Config.nRun;
        int[] resultGeneration = new int[n];
        double[] resultTime = new double[n];
        Individual[] resultIndividual = new Individual[n];


        for (int iRun = 0; iRun < n; iRun++) {

            log("\nRun: " + (iRun+1));

            double fittest = 0;
            double lastFitness = fittest;
            boolean isSolutionFound = false;
            int solutionFoundAtGeneration = 0;

            long startTime = System.nanoTime();
            long endTime = startTime;

            // Create an initial population
            Population myPopulation = new Population(Config.populationSize, true);

            int generationCount = 0;
            for (int iGeneration = 0; iGeneration < Config.maxGeneration; iGeneration++) {
                generationCount++;
                Individual fittestIndividual = myPopulation.getFittest();
                fittest = fittestIndividual.getFitness();
                if(fittest>lastFitness && Calculator.isPassConstrain(fittestIndividual)) {
                    isSolutionFound = true;
                    endTime = System.nanoTime();
                    solutionFoundAtGeneration = iGeneration;
                    lastFitness = fittest;
                    log("New solution: " + generationCount + "\t\tReliability: " + String.format("%.6f", fittestIndividual.getReliability()) + " *");
                }
                if(iGeneration%1000==0)
                    log("Generation: " + generationCount + "\t\tReliability: " + String.format("%.6f", fittestIndividual.getReliability()));
                myPopulation = Algorithm.evolvePopulation(myPopulation);
            }

            if(isSolutionFound) {
                double executionTime = (double)(endTime-startTime)/1e9 ;
                Individual fittestPopulation = myPopulation.getFittest();
                log("\nSolution found :)");
                log("At generation: " + solutionFoundAtGeneration);
                log("Reliability: " + fittestPopulation.getReliability());
                log("Cost: " + fittestPopulation.getCost());
                log("Execution Time: " + executionTime);
                log("Genes:");
                log(Utility.printSystem(fittestPopulation));


                resultIndividual[iRun] = fittestPopulation;
                resultGeneration[iRun] = solutionFoundAtGeneration;
                resultTime[iRun] = executionTime;


            } else {
                log("\nNot found any feasible solution :(");
                log("Let's try again!");
                iRun--;

            }
        }

        log(Utility.printReport(resultIndividual, resultGeneration, resultTime) );

    }
}
