package com.example.umgcteam3;

import java.util.Random;

public enum ExplosiveFirstExercise {

    Burpees {
        @Override
        public String toString() {
            return "Burpees";
        }
    },
    Medball_Slams {
        @Override
        public String toString() {
            return "Medicine Ball Slams";
        }
    },
    BW_Split_Jump {
        @Override
        public String toString() {
            return "Split Jump";
        }
    },
    Tuck_Jump {
        @Override
        public String toString() {
            return "Tuck Jump";
        }
    },
    Lateral_Jump_Burpees {
        @Override
        public String toString() {
            return "Lateral Jump Burpees";
        }
    };

    public static ExplosiveFirstExercise getRandomExercise(){
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
