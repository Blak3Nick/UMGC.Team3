package com.example.umgcteam3;


import java.io.Serializable;

public class Set implements Serializable {
    private String ExerciseCategory;
    private String ExerciseName;
    private int Precedence;
    private int RestPeriod;
    private int TargetRPE;
    private int TargetReps;
    private int WeightUsed;
    private int SetNumber;
    private int TotalSets;

    public Set() {
    }

    public Set(String exerciseCategory, String exerciseName, int precedence, int restPeriod, int targetRPE, int targetReps, int weightUsed, int setNumber, int totalSets) {
        ExerciseCategory = exerciseCategory;
        ExerciseName = exerciseName;
        Precedence = precedence;
        RestPeriod = restPeriod;
        TargetRPE = targetRPE;
        TargetReps = targetReps;
        WeightUsed = weightUsed;
        SetNumber = setNumber;
        TotalSets = totalSets;
    }

    public String getExerciseCategory() {
        return ExerciseCategory;
    }

    public void setExerciseCategory(String exerciseCategory) {
        ExerciseCategory = exerciseCategory;
    }

    public String getExerciseName() {
        return ExerciseName;
    }

    public void setExerciseName(String exerciseName) {
        ExerciseName = exerciseName;
    }

    public int getPrecedence() {
        return Precedence;
    }

    public void setPrecedence(int precedence) {
        Precedence = precedence;
    }

    public int getRestPeriod() {
        return RestPeriod;
    }

    public void setRestPeriod(int restPeriod) {
        RestPeriod = restPeriod;
    }

    public int getTargetRPE() {
        return TargetRPE;
    }

    public void setTargetRPE(int targetRPE) {
        TargetRPE = targetRPE;
    }

    public int getTargetReps() {
        return TargetReps;
    }

    public void setTargetReps(int targetReps) {
        TargetReps = targetReps;
    }

    public int getWeightUsed() {
        return WeightUsed;
    }

    public void setWeightUsed(int weightUsed) {
        WeightUsed = weightUsed;
    }

    public int getSetNumber() {
        return SetNumber;
    }

    public void setSetNumber(int setNumber) {
        SetNumber = setNumber;
    }

    public int getTotalSets() {
        return TotalSets;
    }

    public void setTotalSets(int totalSets) {
        TotalSets = totalSets;
    }
}

