package org.spicydog;

/**
 * Created by spicydog on 10/22/14.
 */
public class Config {

    // Execution Parameters
    static int nRun = 1;
    static int maxGeneration = 1000;

    // Genetic Algorithm Parameters
    static int geneLength = 24;
    static int populationSize = 25;

    static int elitismSize = 2;
    static int tournamentSize = 12;
    static double crossoverUniformRate = 0.5;
    static double mutationRate = 0.015;


    // Problem Parameters
    static double maxCost = 180;

    static int nSubsystem = 6;
    static int nHardware = 3;
    static int nSoftware = 4;

                                   // HW1    HW2    HW3       SW1    SW2    SW3    SW4
    static double[] reliability = {   0.995, 0.980, 0.980,    0.950, 0.908, 0.908, 0.950,   // Subsystem 1
                                      0.995, 0.995, 0.970,    0.965, 0.908, 0.887, 0.908,   // Subsystem 2
                                      0.994, 0.995, 0.992,    0.978, 0.954, 0.860, 0.954,   // Subsystem 3
                                      0.990, 0.980, 0.985,    0.950, 0.908, 0.910, 0.950,   // Subsystem 4
                                      0.995, 0.980, 0.995,    0.905, 0.967, 0.967, 0.905,   // Subsystem 5
                                      0.998, 0.995, 0.994,    0.908, 0.968, 0.968, 0.955};  // Subsystem 6

                                   // HW1   HW2   HW3     SW1   SW2   SW3   SW4
    static double[] cost        = {   30.0, 10.0, 10.0,   30.0, 10.0, 20.0, 30.0,   // Subsystem 1
                                      30.0, 20.0, 10.0,   30.0, 20.0, 10.0, 20.0,   // Subsystem 2
                                      20.0, 30.0,100.0,   20.0, 30.0, 20.0, 30.0,   // Subsystem 3
                                      30.0, 10.0, 10.0,   20.0, 10.0, 20.0, 20.0,   // Subsystem 4
                                      30.0, 20.0, 30.0,   30.0, 20.0, 10.0, 30.0,   // Subsystem 5
                                      30.0, 20.0, 20.0,   10.0, 30.0, 20.0, 20.0};  // Subsystem 6

}
