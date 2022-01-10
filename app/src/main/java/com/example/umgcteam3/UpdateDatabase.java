package com.example.umgcteam3;


import android.content.Context;
import android.os.AsyncTask;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;



import java.util.HashMap;
import java.util.Map;

public class UpdateDatabase extends AsyncTask<Void, Void, String> {
    Context context;
    int set;
    int reps;
    int rpe;
    int weight;
    String user_id;
    String exercise_name;
    String exDate;
    String setNumber;
    String workoutType;
    public UpdateDatabase(Context context, int set, int reps, int rpe, int weight,   String exercise_name, String exDate,  String workoutType) {
        this.context = context;
        this.set = set;
        this.reps = reps;
        this.rpe = rpe;
        this.weight = weight;
        this.exercise_name = exercise_name;
        this.exDate = exDate;
        this.setNumber = "Set" + set;
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
        try {
            Map previous = BackgroundWorker.completedExercises.get(exercise_name);
            if( (Integer) previous.get("WeightUsed") < (Integer) completedSet.get("WeightUsed")) {
                BackgroundWorker.completedExercises.put(exercise_name, completedSet);
            }
        } catch (NullPointerException nullPointerException) {
            BackgroundWorker.completedExercises.put(exercise_name, completedSet);
        }

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

        //documentReference.update(workoutUpdate, FieldValue.increment(1));
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
