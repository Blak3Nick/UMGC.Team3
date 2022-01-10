package com.example.umgcteam3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;

public class WorkoutOneStrengthBaseDayOne {
    public static int totalExercises = 8;
    int size = LowerBodyAssistancePush.values().length;
    int[] lowerBodyAssistancePushNumbers = UniqueRandomNumbers.getRandomNumbers(size, 2);
    int pullSize = LowerBodyAssistancePull.values().length;
    int[] lowerBodyAssistancePullNumbers = UniqueRandomNumbers.getRandomNumbers(pullSize, 2);
    int lowerPushCounter = 0;
    int lowerPullCounter =0;
    Exercise firstExercise = new Exercise(1);
    Exercise secondExercise = new Exercise(2);
    Exercise thirdExercise = new Exercise(3);
    Exercise fourthExercise = new Exercise(4);
    Exercise fifthExercise = new Exercise(5);
    Exercise sixthExercise = new Exercise(6);
    Exercise seventhExercise = new Exercise(7);
    Exercise eighthExercise = new Exercise(8);
    Exercise[] allExercises = new Exercise[8];


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
        int totalSets = 5;


        //Build First Exercise-- Default is body-weight exercise
        for (int i = 1; i < 6; i++) {
            Set set = new Set(exerciseCategory,exerciseName,precedence,restPeriod, targetRPE, targetReps, weightUsed,i, totalSets);
            firstExercise.addSet(set);
        }
        allExercises[0] = firstExercise;
        //Second Exercise--
        exerciseCategory = "LowerMainExercisePush";
        LowerMainExercisesPush exercisesPush = LowerMainExercisesPush.getRandomExercise();
        exerciseName = exercisesPush.toString();
        double exFactor = exercisesPush.getFactor();
        double startingFactor = .60;
        double increaseFactor = .05;
        SharedPreferences sharedPref = context.getSharedPreferences(GetMaxesWorker.SHARED_PREFS, Context.MODE_PRIVATE);
        int max = sharedPref.getInt("Back Squat", 0);
        addSets(secondExercise, exerciseCategory, exerciseName, exFactor, startingFactor, increaseFactor, max, 2, 90, 5, 5,5 );
        allExercises[1] = secondExercise;
        //Third Exercise
        startingFactor = .5;
        exerciseCategory = "LowerBodyAssistancePush";
        LowerBodyAssistancePush assistancePush = LowerBodyAssistancePush.getSpecificExercise(lowerBodyAssistancePushNumbers[lowerPushCounter++]);
        exerciseName = assistancePush.toString();
        exFactor = assistancePush.getFactor();
        addSets(thirdExercise, exerciseCategory, exerciseName, exFactor, startingFactor, increaseFactor, max, 3, 60, 4, 8,5 );
        allExercises[2] = thirdExercise;
        //Fourth Exercise
        exerciseCategory = "LowerBodyAssistancePull";
        LowerBodyAssistancePull lowerBodyAssistancePull = LowerBodyAssistancePull.getSpecificExercise(lowerBodyAssistancePullNumbers[lowerPullCounter++]);
        exerciseName = lowerBodyAssistancePull.toString();
        exFactor = lowerBodyAssistancePull.getFactor();
        addSets(fourthExercise, exerciseCategory, exerciseName, exFactor, startingFactor, increaseFactor, max, 4, 60, 4, 8,5 );
        allExercises[3] = fourthExercise;
        //Fifth Exercise
        exerciseCategory = "LowerBodyAssistancePush";
        assistancePush = LowerBodyAssistancePush.getSpecificExercise(lowerBodyAssistancePushNumbers[lowerPushCounter++]);
        exerciseName = assistancePush.toString();
        exFactor = assistancePush.getFactor();
        addSets(fifthExercise, exerciseCategory, exerciseName, exFactor, startingFactor, increaseFactor, max, 5, 60, 4, 8,5 );
        allExercises[4] = fifthExercise;
        //Sixth Exercise
        exerciseCategory = "LowerBodyAssistancePull";
        lowerBodyAssistancePull = LowerBodyAssistancePull.getSpecificExercise(lowerBodyAssistancePullNumbers[lowerPullCounter++]);
        exerciseName = lowerBodyAssistancePull.toString();
        exFactor = lowerBodyAssistancePull.getFactor();
        addSets(sixthExercise, exerciseCategory, exerciseName, exFactor, startingFactor, increaseFactor, max, 6, 60, 4, 8,5 );
        allExercises[5] = sixthExercise;
        //Seventh Exercise
        exerciseCategory = "HipExercise";
        HipExercise hipExercise = HipExercise.getRandomExercise();
        exerciseName = hipExercise.toString();
        for (int i = 1; i < 6; i++) {
            Set set = new Set(exerciseCategory,exerciseName,7,30, 5, 20, -1,i, 5);
            seventhExercise.addSet(set);
        }
        allExercises[6] = seventhExercise;
        //Eighth Exercise
        exerciseCategory = "AbdominalExercises";
        AbdominalExercises abdominalExercises = AbdominalExercises.getRandomExercise();
        exerciseName = abdominalExercises.toString();
        for (int i = 1; i < 6; i++) {
            Set set = new Set(exerciseCategory,exerciseName,8,30, 5, 15, -1,i, 5);
            eighthExercise.addSet(set);
        }
        allExercises[7] = eighthExercise;
        int[] maxes = {500,400, 300, 200, 200};
        InitialWorkoutBuilder workoutBuilder = new InitialWorkoutBuilder(maxes, allExercises);
        workoutBuilder.doInBackground();




    }
    private void addSets(Exercise exercise, String exerciseCategory, String exName, double exFactor, double startingFactor,
                         double increaseFactor, int max, int precedence, int restPeriod, int targetRPE, int targetReps, int totalSets) {
        for (int i = 1; i < totalSets+1; i++) {
            double newFactor = (i* increaseFactor) + startingFactor;
            double number = newFactor * exFactor * max;
            int weightUsed = (int) (5*(Math.round(number/5)));
            Set set = new Set(exerciseCategory,exName,precedence,restPeriod, targetRPE, targetReps, weightUsed,i, totalSets);
            exercise.addSet(set);
            targetRPE++;
        }
    }





}
