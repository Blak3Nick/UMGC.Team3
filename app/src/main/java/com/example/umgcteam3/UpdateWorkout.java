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
            case "Day1":
                exercises = BackgroundWorker.day_1_exercises;
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
            UpdateCurrentWorkoutPlan updateCurrentWorkoutPlan = new UpdateCurrentWorkoutPlan(set,workoutType, i+1, exerciseNumber);
            System.out.println("The current set is " + i+ " exercise number" + exerciseNumber + "\n\n\n\n\n");
            updateCurrentWorkoutPlan.doInBackground();
        }

        switch (workoutType){
            case "Abdominals":
                BackgroundWorker.setAbdominalExercises(exercises);
                break;
            case "LowerBody":
                BackgroundWorker.setLowerBodyExercises(exercises);
                break;
            case "UpperBody":
                BackgroundWorker.setUpperBodyExercises(exercises);
        }
        return exercises;
    }


}
