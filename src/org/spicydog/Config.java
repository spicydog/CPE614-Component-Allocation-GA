package org.spicydog;

/**
 * Created by spicydog on 10/22/14.
 */
public class Config {

    // Execution Parameters
    static int nRun = 10;
    static int maxGeneration = 5000;
    static boolean enableRepairing = true;

    // Genetic Algorithm Parameters
    static int geneLength = 25;
    static int populationSize = 50;

    static int elitismSize = 3;
    static int eliminateSize = 10;

    static double crossoverRate = 0.8;
    static int tournamentSize = 5;
    static double mutationRate = 0.2;


    // Problem Parameters
    static double alpha    = 0.2;  // Weight of system reliability
    static double beta     = 0.4;  // Weight of system cost
    static double gamma    = 0.4;  // Weight of system weight


    static int[] subsystemSizes = {4,3,4,3,4,4,3};
    static int nSubsystem = subsystemSizes.length; // 7
    static int totalComponent = Utility.sumArray(subsystemSizes);

                                 // COMP1   COMP2   COMP3   COMP4
    static double[] reliability = { 0.90,   0.93,   0.91,   0.95,   // Subsystem 1
                                    0.95,   0.94,   0.93,           // Subsystem 2
                                    0.85,   0.90,   0.87,   0.92,   // Subsystem 3
                                    0.83,   0.87,   0.85,           // Subsystem 4
                                    0.94,   0.93,   0.95,   0.94,   // Subsystem 5
                                    0.99,   0.98,   0.97,   0.96,   // Subsystem 6
                                    0.91,   0.92,   0.94};          // Subsystem 7

                               // COMP1   COMP2   COMP3   COMP4
    static double[] cost        = { 1,      1,      2,      4,      // Subsystem 1
                                    4,      2,      1,              // Subsystem 2
                                    2,      3,      1,      4,      // Subsystem 3
                                    3,      4,      5,              // Subsystem 4
                                    2,      2,      5,      2,      // Subsystem 5
                                    6,      4,      2,      2,      // Subsystem 6
                                    4,      4,      5};             // Subsystem 7

    static double totalCost = Utility.sumArray(cost);

                               // COMP1   COMP2   COMP3   COMP4
    static double[] weight      = { 3,      4,      2,      5,      // Subsystem 1
                                    8,      10,     9,              // Subsystem 2
                                    7,      5,      6,      4,      // Subsystem 3
                                    5,      6,      4,              // Subsystem 4
                                    4,      3,      5,      4,      // Subsystem 5
                                    5,      4,      5,      4,      // Subsystem 6
                                    7,      8,      9};             // Subsystem 7

    static double totalWeight = Utility.sumArray(weight);
}
