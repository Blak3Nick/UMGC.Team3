package com.example.umgcteam3;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class InitialWorkoutBuilder extends AsyncTask<Void, Void, String> {
    String[] allExercises = {"squat", "bench", "deadlift", "curl", "leg press", "shoulder press", "barbell row"};

    String user_id;


    @Override
    protected String doInBackground(Void... voids) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        user_id = fAuth.getCurrentUser().getUid();
        Map<String, Object> newWorkout = new HashMap<>();
        newWorkout.put("ExerciseCategory", "main_exercise");
        newWorkout.put("ExerciseName", allExercises[0]);
        newWorkout.put("Precedence", 1);
        newWorkout.put("RestPeriod", 60);
        newWorkout.put("TargetRPE", 5);
        newWorkout.put("TargetReps", 8);
        newWorkout.put("WeightUsed", 135);

        for (int k = 1; k < 5; k++) {
            for (int j = 1; j < allExercises.length+1; j++) {
                newWorkout.put("ExerciseName", allExercises[j-1]);
                for (int i = 1; i < 6; i++) {
                    db.collection("users").document(user_id).collection("CurrentWorkoutPlan").document("Day_"+k).collection("Exercise_"+j+"_All_Sets")
                            .document("Ex_"+j+"_All_Sets").collection("AllSets")
                            .document("Set"+i).set(newWorkout, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("SUCCESS", "Written to the database");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("FAILURE", e.getMessage() );
                        }
                    });
                }
            }
        }
        return null;
    }
}
