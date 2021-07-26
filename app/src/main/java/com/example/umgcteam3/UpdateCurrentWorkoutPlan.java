package com.example.umgcteam3;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class UpdateCurrentWorkoutPlan extends AsyncTask<Void, Void, String> {
    String user_id;
    Set set;
    String workoutType;
    int setNumber;
    int exerciseNumber;

    public UpdateCurrentWorkoutPlan(Set set, String workoutType, int setNumber, int exerciseNumber) {
        this.set = set;
        this.workoutType = workoutType;
        this.setNumber = setNumber;
        this.exerciseNumber = exerciseNumber;
    }

    @Override
    protected String doInBackground(Void... voids) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        user_id = fAuth.getCurrentUser().getUid();
        Map<String, Object> newWorkout = new HashMap<>();
        newWorkout.put("ExerciseCategory", set.getExerciseCategory());
        newWorkout.put("ExerciseName", set.getExerciseName());
        newWorkout.put("Precedence", set.getPrecedence());
        newWorkout.put("RestPeriod", set.getRestPeriod());
        newWorkout.put("TargetRPE", set.getTargetRPE());
        newWorkout.put("TargetReps", set.getTargetReps());
        newWorkout.put("WeightUsed", set.getWeightUsed());
        newWorkout.put("TotalSets", set.getTotalSets());
        newWorkout.put("SetNumber", set.getSetNumber());
        db.collection("users").document(user_id).collection("CurrentWorkoutPlan")
                .document(workoutType).collection("Exercise_"+exerciseNumber+"_All_Sets")
                .document("Ex_"+exerciseNumber+"_All_Sets").collection("AllSets")
                .document("Set"+setNumber).set(newWorkout, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("SUCCESS", "Wrote new workout numbers");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("FAILURE", e.getMessage() );
            }
        });
        return null;
    }
}
