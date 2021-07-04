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
//Assigns a group of exercises to the user initially
public class InitialWorkoutBuilder extends AsyncTask<Void, Void, String> {

    String[] upperExercises = new String[UpperBodyExercise.values().length];
    String[] lowerExercises = new String[LowerBodyExercise.values().length];
    String[] absExercises = new String[AbdominalExercises.values().length];
    String user_id;
    public InitialWorkoutBuilder() {
       int i =0;
        for (UpperBodyExercise a: UpperBodyExercise.values()) {
            upperExercises[i++] = a.toString();
        }
        i  = 0;
        for (LowerBodyExercise b: LowerBodyExercise.values()) {
            lowerExercises[i++] = b.toString();
        }
        i = 0;
        for (AbdominalExercises c: AbdominalExercises.values()) {
            absExercises[i++] = c.toString();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        user_id = fAuth.getCurrentUser().getUid();
        Map<String, Object> newWorkout = new HashMap<>();
        newWorkout.put("ExerciseCategory", "upper_body_exercise");
        newWorkout.put("ExerciseName", upperExercises[0]);
        newWorkout.put("Precedence", 1);
        newWorkout.put("RestPeriod", 60);
        newWorkout.put("TargetRPE", 5);
        newWorkout.put("TargetReps", 8);
        newWorkout.put("WeightUsed", 135);
        newWorkout.put("TotalSets", 5);
        newWorkout.put("SetNumber", 1);

         for (int j = 1; j < upperExercises.length+1; j++) {
            newWorkout.put("ExerciseName", upperExercises[j-1]);
            for (int i = 1; i < 6; i++) {
                newWorkout.put("setNumber", i);
                db.collection("users").document(user_id).collection("CurrentWorkoutPlan").document("UpperBody").collection("Exercise_"+j+"_All_Sets")
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
        newWorkout.put("ExerciseCategory", "lower_body_exercise");
        newWorkout.put("ExerciseName", lowerExercises[0]);
        newWorkout.put("Precedence", 1);
        newWorkout.put("RestPeriod", 60);
        newWorkout.put("TargetRPE", 5);
        newWorkout.put("TargetReps", 8);
        newWorkout.put("WeightUsed", 135);
        newWorkout.put("TotalSets", 5);
        newWorkout.put("SetNumber", 1);

        for (int j = 1; j < lowerExercises.length+1; j++) {
            newWorkout.put("ExerciseName", lowerExercises[j-1]);
            for (int i = 1; i < 6; i++) {
                newWorkout.put("setNumber", i);
                db.collection("users").document(user_id).collection("CurrentWorkoutPlan").document("LowerBody")
                        .collection("Exercise_"+j+"_All_Sets")
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
        newWorkout.put("ExerciseCategory", "abdominal_exercise");
        newWorkout.put("ExerciseName", absExercises[0]);
        newWorkout.put("Precedence", 1);
        newWorkout.put("RestPeriod", 60);
        newWorkout.put("TargetRPE", 5);
        newWorkout.put("TargetReps", 30);
        newWorkout.put("WeightUsed", -1);
        newWorkout.put("TotalSets", 5);
        newWorkout.put("SetNumber", 1);

        for (int j = 1; j < absExercises.length+1; j++) {
            newWorkout.put("ExerciseName", absExercises[j-1]);
            for (int i = 1; i < 6; i++) {
                newWorkout.put("setNumber", i);
                db.collection("users").document(user_id).collection("CurrentWorkoutPlan").document("Abdominals").collection("Exercise_"+j+"_All_Sets")
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

        return null;
    }
}
