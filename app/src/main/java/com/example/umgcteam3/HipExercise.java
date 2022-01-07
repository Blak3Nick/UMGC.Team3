package com.example.umgcteam3;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum HipExercise {
    Hip_Band_Abduction("Hip Abduction Band", -1.0),
    Hip_Band_Adduction("Hip Adduction Band", -1.0),
    Fire_Hydrants("Fire Hydrants", -1.0);

    private static final Map<String, HipExercise> BY_LABEL = new HashMap<>();
    private static final Map<Double, HipExercise> BY_FACTOR = new HashMap<>();
    private static final HipExercise[] listOfExercise = new HipExercise[values().length];

    static {
        int i =0;
        for (HipExercise e : values()) {
            BY_LABEL.put(e.label, e);
            BY_FACTOR.put(e.factor, e);
            listOfExercise[i++] = e;
        }
    }

    public final String label;
    public final double factor;

    HipExercise(String label, double factor) {
        this.label = label;
        this.factor = factor;
    }
    public static HipExercise getRandomExercise(){
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
    public static HipExercise[] getAllExercises() {
        return listOfExercise;
    }
    @Override
    public String toString() {
        return this.label;
    }

    public double getFactor() {
        return this.factor;
    }


    public static HipExercise getSpecificExercise(int i){
        return listOfExercise[i];
    }
}
