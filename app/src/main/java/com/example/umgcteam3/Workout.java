package com.example.umgcteam3;

import android.os.Build;

import androidx.annotation.RequiresApi;
import java.io.Serializable;
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
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateExerciseNumbers(int key, Exercise exercise) {
        allExercises.replace(key,exercise);
    }

}
