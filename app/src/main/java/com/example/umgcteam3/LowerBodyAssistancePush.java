package com.example.umgcteam3;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum LowerBodyAssistancePush {

    Leg_Press("Leg Press", 2.0),
    Leg_Extension("Leg Extension", .2),
    Barbell_Calf_Raises("Barbell Calf Raises", 1.0),
    Weighted_Step_Ups("Weighted Step-ups", .2);

    private static final Map<String, LowerBodyAssistancePush> BY_LABEL = new HashMap<>();
    private static final Map<Double, LowerBodyAssistancePush> BY_FACTOR = new HashMap<>();

    static {
        for (LowerBodyAssistancePush e : values()) {
            BY_LABEL.put(e.label, e);
            BY_FACTOR.put(e.factor, e);
        }
    }

    public final String label;
    public final double factor;

    LowerBodyAssistancePush(String label, double factor) {
        this.label = label;
        this.factor = factor;
    }
    public static LowerBodyAssistancePush getRandomExercise(){
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
