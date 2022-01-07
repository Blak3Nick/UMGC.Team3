package com.example.umgcteam3;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum LowerBodyAssistancePull {

    Barbell_Stiff_Leg_Deadlift("Barbell Stiff-leg Deadlift", .4),
    Leg_Curl("Leg Curl", .2),
    Weighted_Glute_Ham_Raises("Weighted Glute Ham Raise", .03),
    Single_Leg_Stiff_Leg_Deadlift("Single Leg Stiff-leg Deadlift", .1);

    private static final Map<String, LowerBodyAssistancePull> BY_LABEL = new HashMap<>();
    private static final Map<Double, LowerBodyAssistancePull> BY_FACTOR = new HashMap<>();
    private static final LowerBodyAssistancePull[] listOfExercise = new LowerBodyAssistancePull[values().length];

    static {
        int i =0;
        for (LowerBodyAssistancePull e : values()) {
            BY_LABEL.put(e.label, e);
            BY_FACTOR.put(e.factor, e);
            listOfExercise[i++] = e;
        }
    }

    public final String label;
    public final double factor;

    LowerBodyAssistancePull(String label, double factor) {
        this.label = label;
        this.factor = factor;
    }
    public static LowerBodyAssistancePull getRandomExercise(){
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
    public static LowerBodyAssistancePull getSpecificExercise(int i){
        return listOfExercise[i];
    }
}
