package com.example.umgcteam3;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class UpdateWorkout {
    Exercise[] exercises;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Exercise[] updateCurrentWorkout(String workoutType, int exerciseKey, boolean increase, int startingSet, int exerciseNumber){
        switch (workoutType){
            case "Abdominals":
                exercises = BackgroundWorker.abdominalExercises;
                break;
            case "LowerBody":
                exercises = BackgroundWorker.lowerBodyExercises;
                break;
            case "UpperBody":
                exercises = BackgroundWorker.upperBodyExercises;
        }
        System.out.println("The exercise key is " + exerciseKey + "\n\n\n\n\n");
        Exercise exercise = exercises[exerciseKey];
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
            System.out.println("The current weight is " + curWeight);
            exerciseNumber++;
            UpdateCurrentWorkoutPlan updateCurrentWorkoutPlan = new UpdateCurrentWorkoutPlan(set,workoutType, i+1, exerciseNumber);
            updateCurrentWorkoutPlan.doInBackground();
        }
        return exercises;
    }


}
