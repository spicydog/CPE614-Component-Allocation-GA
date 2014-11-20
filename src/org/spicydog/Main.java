package org.spicydog;

import static org.spicydog.Utility.log;

public class Main {


    public static void main(String[] args) {


        for (int x = 1; x <= 10; x++) {
            for (int y = 0; y <= 10; y++) {
                for (int z = 0; z <= 10; z++) {
                    if(x+y+z==10) {


                        int n = Config.nRun;
                        int[] resultGeneration = new int[n];
                        double[] resultTime = new double[n];
                        Individual[] resultIndividual = new Individual[n];

                        Config.alpha   = (double) x/10;
                        Config.beta    = (double) y/10;
                        Config.gamma = (double) z/10;


                        for (int iRun = 0; iRun < n; iRun++) {

                            //log("\nRun: " + iRun+1);

                            double fitness = Double.MIN_VALUE;
                            double lastFitness = fitness;
                            boolean isSolutionFound = false;
                            int solutionFoundAtGeneration = 0;

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
                                    //log("New solution: " + generationCount + "\t\tFittest: " + String.format("%.6f",fitness) + " *");
                                }
                                if(iGeneration%1000==0)
                                    //log("Generation: " + generationCount + "\t\tFittest: " + String.format("%.6f",fitness));
                                myPopulation = Algorithm.evolvePopulation(myPopulation);
                            }

                            if(isSolutionFound) {
                                double executionTime = (double)(endTime-startTime)/1e9 ;
                                Individual fittestPopulation = myPopulation.getFittest();
//                                log("\nSolution found :)");
//                                log("At generation: " + solutionFoundAtGeneration);
//                                log("Fitness: " + fittestPopulation.getFitness());
//                                log("Reliability: " + fittestPopulation.getReliability());
//                                log("Cost: " + fittestPopulation.getCost());
//                                log("Weight: " + fittestPopulation.getWeight());
//                                log("Execution Time: " + executionTime);
//                                log("Genes:");
//                                log(Utility.printSystem(fittestPopulation.toBooleans()));

                                resultIndividual[iRun] = fittestPopulation;
                                resultGeneration[iRun] = solutionFoundAtGeneration;
                                resultTime[iRun] = executionTime;


                            } else {
//                                log("\nNot found any feasible solution :(");
//                                log("Let's try again!");
                                iRun--;

                            }
                        }

                        log(String.format("%.1f %.1f %.1f",Config.alpha, Config.beta, Config.gamma));
                        log(Utility.printReport(resultIndividual, resultGeneration, resultTime) );
                    }
                }
            }
        }


    }
}
