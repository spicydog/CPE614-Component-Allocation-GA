package org.spicydog;

/**
 * Created by spicydog on 10/22/14.
 */
public class Config {

    protected static String solution = "1111000000000000000000000000000000000000000000000000000000001111";
    protected static int defaultGeneLength = 64;
    protected static int defaultPopulationSize = 50;

    protected static double defaultUniformRate = 0.5;
    protected static double defaultMutationRate = 0.015;
    protected static int defaultTournamentSize = 5;
    protected static int defaultElitismOffset = 1;

                         // HW1    HW2    HW3    SW1    SW2    SW3    SW4
    double[] reliability = {0.995, 0.980, 0.980, 0.950, 0.908, 0.908, 0.950,   // Subsystem 1
                            0.995, 0.995, 0.970, 0.965, 0.908, 0.887, 0.908,   // Subsystem 2
                            0.994, 0.995, 0.992, 0.978, 0.954, 0.860, 0.954,   // Subsystem 3
                            0.990, 9.980, 0.985, 0.950, 0.908, 0.910, 0.950,   // Subsystem 4
                            0.995, 0.980, 0.995, 0.905, 0.967, 0.967, 0.905,   // Subsystem 5
                            0.998, 0.995, 0.994, 0.908, 0.968, 0.968, 0.955};  // Subsystem 6

                         // HW1   HW2   HW3   SW1   SW2   SW3   SW4
    double[] cost        = {30.0, 10.0, 10.0, 30.0, 10.0, 20.0, 30.0,   // Subsystem 1
                            30.0, 20.0, 10.0, 30.0, 20.0, 10.0, 20.0,   // Subsystem 2
                            20.0, 30.0,100.0, 20.0, 30.0, 20.0, 30.0,   // Subsystem 3
                            30.0, 10.0, 10.0, 20.0, 10.0, 20.0, 20.0,   // Subsystem 4
                            30.0, 20.0, 30.0, 30.0, 20.0, 10.0, 30.0,   // Subsystem 5
                            30.0, 20.0, 20.0, 10.0, 30.0, 20.0, 20.0};  // Subsystem 6

}
