package com.example.umgcteam3;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;



import java.util.HashMap;
import java.util.Map;

public class UpdateDatabase extends AsyncTask<Void, Void, String> {
    Context context;
    int set;
    int reps;
    int rpe;
    int weight;
    int day_number;
    String user_id;
    String exercise_name;
    String exDate;
    String setNumber;
    String workoutType;
    boolean readyToIncrease = false;
    public UpdateDatabase(Context context, int set, int reps, int rpe, int weight, int day_number,  String exercise_name, String exDate, boolean readyToIncrease, String workoutType) {
        this.context = context;
        this.set = set;
        this.reps = reps;
        this.rpe = rpe;
        this.weight = weight;
        this.day_number = day_number;
        this.exercise_name = exercise_name;
        this.exDate = exDate;
        this.setNumber = "Set" + set;
        this.readyToIncrease = readyToIncrease;
        this.workoutType = workoutType;
    }

    @Override
    protected String doInBackground(Void... voids) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        user_id = fAuth.getCurrentUser().getUid();
        System.out.println(user_id);
        Map<String, Object> completedSet = new HashMap<>();
        String date = FieldValue.serverTimestamp().toString();
        System.out.println(date);
        completedSet.put("DateCompleted", FieldValue.serverTimestamp());
        completedSet.put("SetNumber", set);
        completedSet.put("Reps", reps);
        completedSet.put("WeightUsed", weight);
        completedSet.put("RPE", rpe);
        completedSet.put("DayNumber", day_number);
        completedSet.put("Increase?", readyToIncrease);

        db.collection("users").document(user_id).collection("CompletedWorkouts").document(exercise_name).collection("AllSets").document(exDate).collection(setNumber)
                .document(setNumber+"Details").set(completedSet, SetOptions.merge())
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

        DocumentReference documentReference = db.collection("users").document(user_id).collection("CompletedWorkouts").document(exercise_name);
        documentReference.update("weightUsed", FieldValue.arrayUnion(weight));
        documentReference.update("dateCompleted", FieldValue.arrayUnion(exDate));

        documentReference = db.collection("users").document(user_id);
        String workoutUpdate = "";
        switch (workoutType){
            case "Abdominals":
                workoutUpdate = "AbdominalWorkoutTotal";
                break;
            case "LowerBody":
                workoutUpdate = "LowerBodyWorkoutTotal";
                break;
            case "UpperBody":
                workoutUpdate = "UpperBodyWorkoutTotal";
        }

        documentReference.update(workoutUpdate, FieldValue.increment(1));
        return null;
    }

    @Override
    protected void onPreExecute() {    }

    @Override
    protected void onPostExecute(String result) {    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
