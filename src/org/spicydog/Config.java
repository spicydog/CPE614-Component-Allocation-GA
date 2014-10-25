package org.spicydog;

/**
 * Created by spicydog on 10/22/14.
 */
public class Config {

    // Genetic Algorithm Parameters
    protected static int geneLength = 42;
    protected static int populationSize = 50;

    protected static int elitismSize = 3;
    protected static double crossoverRate = 0.8;
    protected static int tournamentSize = 10;
    protected static double crossoverUniformRate = 0.5;
    protected static double mutationRate = 0.015;

    // Execution Parameters
    protected static int maxGeneration = 10000;

    // Problem Parameters
    protected static double maxCost = 160;

    protected static int nSubsystem = 6;
    protected static int nHardware = 3;
    protected static int nSoftware = 4;

                                             // HW1    HW2    HW3    SW1    SW2    SW3    SW4
    protected static double[] reliability = {   0.995, 0.980, 0.980,    0.950, 0.908, 0.908, 0.950,   // Subsystem 1
                                                0.995, 0.995, 0.970,    0.965, 0.908, 0.887, 0.908,   // Subsystem 2
                                                0.994, 0.995, 0.992,    0.978, 0.954, 0.860, 0.954,   // Subsystem 3
                                                0.990, 0.980, 0.985,    0.950, 0.908, 0.910, 0.950,   // Subsystem 4
                                                0.995, 0.980, 0.995,    0.905, 0.967, 0.967, 0.905,   // Subsystem 5
                                                0.998, 0.995, 0.994,    0.908, 0.968, 0.968, 0.955};  // Subsystem 6

                                             // HW1   HW2   HW3     SW1   SW2   SW3   SW4
    protected static double[] cost        = {   30.0, 10.0, 10.0,   30.0, 10.0, 20.0, 30.0,   // Subsystem 1
                                                30.0, 20.0, 10.0,   30.0, 20.0, 10.0, 20.0,   // Subsystem 2
                                                20.0, 30.0,100.0,   20.0, 30.0, 20.0, 30.0,   // Subsystem 3
                                                30.0, 10.0, 10.0,   20.0, 10.0, 20.0, 20.0,   // Subsystem 4
                                                30.0, 20.0, 30.0,   30.0, 20.0, 10.0, 30.0,   // Subsystem 5
                                                30.0, 20.0, 20.0,   10.0, 30.0, 20.0, 20.0};  // Subsystem 6

}
