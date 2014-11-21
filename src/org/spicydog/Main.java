package org.spicydog;

import static org.spicydog.Utility.log;

public class Main {


    public static void main(String[] args) {

        String strAlpha = "";
        String strBeta = "";
        String strGamma = "";
        String strReliability = "";
        String strCost = "";
        String strWeight = "";

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

                        double sumReliality = 0;
                        double sumCost = 0;
                        double sumWeight = 0;


                        for (int iRun = 0; iRun < n; iRun++) {

                            //log("\nRun: " + iRun+1);

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

                                sumReliality += fittestPopulation.getReliability();
                                sumCost += fittestPopulation.getCost();
                                sumWeight += fittestPopulation.getWeight();


                            } else {
//                                log("\nNot found any feasible solution :(");
//                                log("Let's try again!");
                                iRun--;

                            }
                        }

                        log(String.format("%.2f %.2f %.2f",Config.alpha, Config.beta, Config.gamma));
                        log(Utility.printReport(resultIndividual, resultGeneration, resultTime) );


                        strAlpha += String.format("%.2f,", Config.alpha);
                        strBeta  += String.format("%.2f,", Config.beta);
                        strGamma += String.format("%.2f,", Config.gamma);
                        strReliability += String.format("%.6f,", sumReliality/n);
                        strCost += String.format("%.2f,", sumCost/n);
                        strWeight += String.format("%.2f,", sumWeight/n);

                    }
                }
            }
        }

        strAlpha = String.format("alpha = [%s]",strAlpha).replace(",]","];");
        strBeta = String.format("beta = [%s]",strBeta).replace(",]","];");
        strGamma = String.format("gamma = [%s]",strGamma).replace(",]","];");
        strReliability = String.format("reliability = [%s]",strReliability).replace(",]","];");
        strCost = String.format("cost = [%s]",strCost).replace(",]","];");
        strWeight = String.format("weight = [%s]",strWeight).replace(",]","];");

        log(strAlpha);
        log(strBeta);
        log(strGamma);
        log(strReliability);
        log(strCost);
        log(strWeight);


    }
}
