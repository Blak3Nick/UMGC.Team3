package com.example.umgcteam3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Workout implements Serializable {

    private HashMap<Integer, Exercise> allExercises;
    public Workout() {
        this.allExercises = new HashMap<>();
    }
    public HashMap<Integer, Exercise> getAllExercises() {
        return allExercises;
    }

    public void addExercise(Exercise exercise) {
        this.allExercises.put(exercise.exerciseNumber, exercise);
    }

    public Exercise getSpecificExercise(int key) {
        return allExercises.get(key);
    }

}
