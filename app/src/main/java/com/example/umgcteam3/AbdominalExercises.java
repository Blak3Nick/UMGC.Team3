package com.example.umgcteam3;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum AbdominalExercises {
    Situps("Situps", -1),
    Bicycle_Crunches("Bicycle Crunches", -1),
    Leg_Lifts("Leg Lifts", -1),
    Flutter_Kicks("Flutter Kicks", -1);


    private static final Map<String, AbdominalExercises> BY_LABEL = new HashMap<>();
    private static final Map<Double, AbdominalExercises> BY_FACTOR = new HashMap<>();
    private static final AbdominalExercises[] listOfExercise = new AbdominalExercises[values().length];

    static {
        int i =0;
        for (AbdominalExercises e : values()) {
            BY_LABEL.put(e.label, e);
            BY_FACTOR.put(e.factor, e);
            listOfExercise[i++] = e;
        }
    }

    public final String label;
    public final double factor;

    AbdominalExercises(String label, double factor) {
        this.label = label;
        this.factor = factor;
    }
    public static AbdominalExercises getRandomExercise(){
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
    @Override
    public String toString() {
        return this.label;
    }

    public double getFactor() {
        return this.factor;
    }
    public static AbdominalExercises getSpecificExercise(int i){
        return listOfExercise[i];
    }
}
