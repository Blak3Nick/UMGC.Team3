package com.example.umgcteam3;

import java.io.Serializable;

public class DatabaseExerciseWeightFactor implements Serializable {
    String exName;
    double factor;
    public DatabaseExerciseWeightFactor() {
    }

    public DatabaseExerciseWeightFactor(String exName, double factor) {
        this.exName = exName;
        this.factor = factor;
    }
}
