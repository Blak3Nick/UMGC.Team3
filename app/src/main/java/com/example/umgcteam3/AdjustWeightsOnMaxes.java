package com.example.umgcteam3;

public class AdjustWeightsOnMaxes {
    int maxWeight;
    double weightFactor;

    public AdjustWeightsOnMaxes( int maxWeight, double weightFactor) {
        this.maxWeight = maxWeight;
        this.weightFactor = weightFactor;
    }

    public int getWeight() {

        return maxWeight;
    }

}
