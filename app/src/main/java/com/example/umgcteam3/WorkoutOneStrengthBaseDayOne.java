package com.example.umgcteam3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class WorkoutOneStrengthBaseDayOne {

    Exercise firstExercise = new Exercise(1);
    Exercise secondExercise = new Exercise(2);
    Exercise thirdExercise = new Exercise(3);
    Exercise fourthExercise = new Exercise(4);
    Exercise fifthExercise = new Exercise(5);
    Exercise sixthExercise = new Exercise(6);
    Exercise seventhExercise = new Exercise(7);
    Exercise eighthExercise = new Exercise(8);


    @RequiresApi(api = Build.VERSION_CODES.N)
    public WorkoutOneStrengthBaseDayOne(Context context) {
        //String exerciseCategory, String exerciseName, int precedence, int restPeriod, int targetRPE, int targetReps, int weightUsed, int setNumber, int totalSets
        String exerciseCategory = "ExplosiveFirstExercise";
        String exerciseName = ExplosiveFirstExercise.getRandomExercise().toString();
        int precedence = 1;
        int restPeriod = 60;
        int targetRPE = 5;
        int targetReps = 5;
        int weightUsed = -1;
        int setNumber =1;
        int totalSets = 5;


        //Build First Exercise-- Default is bodyweight exercise
        for (int i = 1; i < 6; i++) {
            Set set = new Set(exerciseCategory,exerciseName,precedence,restPeriod, targetRPE, targetReps, weightUsed,i, totalSets);
            firstExercise.addSet(set);
        }

        exerciseCategory = "LowerMainExercisePush";
        LowerMainExercisesPush exercisesPush = LowerMainExercisesPush.getRandomExercise();
        exerciseName = exercisesPush.toString();
        double exFactor = exercisesPush.getFactor();
        double startingFactor = .60;
        SharedPreferences sharedPref = context.getSharedPreferences(GetMaxesWorker.SHARED_PREFS, Context.MODE_PRIVATE);
        int max = sharedPref.getInt("Back Squat", 0);
        for (int i = 1; i < 6; i++) {
            double newFactor = (i*.05) + startingFactor;
            double number = newFactor * exFactor * max;
            weightUsed = (int) (5*(Math.round(number/5)));
            System.out.println("The exercise is    " + exerciseName);
            System.out.println("The weight used is    " + weightUsed );
            Set set = new Set(exerciseCategory,exerciseName,precedence,restPeriod, targetRPE, targetReps, weightUsed,i, totalSets);
            secondExercise.addSet(set);
        }


    }



}
