package com.example.umgcteam3;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class UpdateWorkout {
    Workout workout;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateCurrentWorkout(String workoutType, int exerciseKey, boolean increase, int startingSet, int exerciseNumber){
        switch (workoutType){
            case "Abdominals":
                workout = LoginActivity.getAbdominalWorkout();
                break;
            case "LowerBody":
                workout = LoginActivity.getLowerBodyWorkout();
                break;
            case "UpperBody":
                workout = LoginActivity.getUpperBodyWorkout();
        }
        System.out.println("The exercise key is " + exerciseKey + "\n\n\n\n\n");
        Exercise exercise = workout.getSpecificExercise(exerciseKey);
        ArrayList<Set> allSets = exercise.getAllSets();
        for (int i = startingSet; i < allSets.size(); i++) {
            Set set = allSets.get(i);
            int curWeight = set.getWeightUsed();
            if(increase){
                curWeight += 5;
            } else {
                curWeight -= 5;
            }
            set.setWeightUsed(curWeight);
            UpdateCurrentWorkoutPlan updateCurrentWorkoutPlan = new UpdateCurrentWorkoutPlan(set,workoutType, i+1, exerciseNumber);
            updateCurrentWorkoutPlan.doInBackground();
        }
        workout.updateExerciseNumbers(exerciseKey, exercise);
    }
}
