package org.spicydog;

/**
 * Created by spicydog on 10/21/14.
 * Based on http://www.theprojectspot.com/tutorial-post/creating-a-genetic-algorithm-for-beginners/3
 */
public class Calculator {

    static int index(int x, int y) {
        int index = 0;
        for (int i = 0; i < Config.nSubsystem; i++) {
            for (int j = 0; j < Config.subsystemSizes[i]; j++) {
                if(i==x && j==y)
                    return index;
                index++;
            }
        }
        return index;
    }

    static double getCost(Individual individual) {
        double sumCost = 0;
        for (int i = 0; i < Config.geneLength; i++) {
            if(individual.getGene(i))
                sumCost += Config.cost[i];
        }
        return sumCost;
    }


    static double getWeight(Individual individual) {
        double sumWeight = 0;
        for (int i = 0; i < Config.geneLength; i++) {
            if(individual.getGene(i))
                sumWeight += Config.weight[i];
        }
        return sumWeight;
    }


    static double getReliability(Individual individual) {
        double rAll = 1;
        for (int i = 0; i < Config.nSubsystem; i++) {
            double rI = 1;
            for (int j = 0; j < Config.subsystemSizes[i]; j++) {
                int index = index(i,j);
                if(individual.getGene(index))
                    rI *= ( 1-Config.reliability[index] );
            }
            rAll *= 1-rI;
        }
        return rAll;
    }

    static double getFitness(Individual individual) {
        if(individual.getReliability()==0) {
            return 0;
        }

        double fitness =    + individual.getReliability() * Config.alpha
                            + (1 - (individual.getCost()/Config.totalCost)) * Config.beta
                            + (1 - (individual.getWeight()/Config.totalWeight)) * Config.gamma;
        return fitness;
    }

}