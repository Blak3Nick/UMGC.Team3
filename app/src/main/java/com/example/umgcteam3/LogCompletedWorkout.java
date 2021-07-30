package com.example.umgcteam3;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Map;

public class LogCompletedWorkout extends AsyncTask<Void, Void, String> {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String user_id = fAuth.getCurrentUser().getUid();
    Map<String, Map> completedSets = BackgroundWorker.completedExercises;
    String exDate;

    public LogCompletedWorkout(String exDate) {
        this.exDate = exDate;
    }

    @Override
    protected String doInBackground(Void... voids) {

        for(String exercise : completedSets.keySet()) {
            Map completedSet = completedSets.get(exercise);
            db.collection("users").document(user_id).collection("CompletedWorkouts")
                    .document(exercise).collection("AllCompleted")
                    .document(exDate)
                    .set(completedSet, SetOptions.merge())
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

        return null;
    }
}
