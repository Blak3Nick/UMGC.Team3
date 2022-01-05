package com.example.umgcteam3;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum LowerMainExercisesPush {


    Barbell_Back_Squat("Barbell Back Squat", 1.0),
    Barbell_Front_Squat("Barbell Front Squat", .7),
    Pause_Back_Squat("3 sec. Pause Back Squat", .7),
    Pause_Front_Squat("3 sec. Pause Front Squat", .5);

    private static final Map<String, LowerMainExercisesPush> BY_LABEL = new HashMap<>();
    private static final Map<Double, LowerMainExercisesPush> BY_FACTOR = new HashMap<>();

    static {
        for (LowerMainExercisesPush e : values()) {
            BY_LABEL.put(e.label, e);
            BY_FACTOR.put(e.factor, e);
        }
    }


    public final String label;
    public final double factor;

    LowerMainExercisesPush(String label, double factor) {
        this.label = label;
        this.factor = factor;
    }
    public static LowerMainExercisesPush getRandomExercise(){
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
}
