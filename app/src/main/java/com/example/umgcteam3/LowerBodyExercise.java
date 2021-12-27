package com.example.umgcteam3;

public enum LowerBodyExercise {
    Leg_Press {
        @Override
        public String toString() {
            return "Leg Press";
        }
    },
    Leg_Curl {
        @Override
        public String toString() {
            return "Leg Curl";
        }
    },
    Leg_Extension {
        @Override
        public String toString() {
            return "Leg Extension";
        }
    },
    Calf_Raises {
        @Override
        public String toString() {
            return "Calf Raises";
        }
    }
}
