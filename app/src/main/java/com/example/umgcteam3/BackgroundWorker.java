package com.example.umgcteam3;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class BackgroundWorker extends AsyncTask<Void, Void, String> {
    Context context;
    AlertDialog alertDialog;
    BackgroundWorker (Context ctx) {
        context = ctx;
    }
    FirebaseFirestore db;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userID;
    public static Exercise[] day1Exercises = new Exercise[7];
    public static Exercise[] day2Exercises = new Exercise[7];
    public static Exercise[] day3Exercises = new Exercise[7];
    @Override
    protected String doInBackground(Void... voids) {
        userID = fAuth.getCurrentUser().getUid();
        getWorkoutData(1);
        getWorkoutData2(2);
        getWorkoutData3(3);
        for (int i = 0; i < day1Exercises.length; i++) {
            Exercise exercise = new Exercise(i+1);
            day1Exercises[i] = exercise;
            Exercise exercise2 = new Exercise(i+1);
            day2Exercises[i] = exercise2;
            Exercise exercise3 = new Exercise(i+1);
            day3Exercises[i] = exercise3;
        }
        return null;
    }
    private void getWorkoutData(int dayNum) {
        int dayTracker = dayNum;
        db = FirebaseFirestore.getInstance();
        String day = "Day_" + dayTracker;
        for(int j=1; j<8; j++) {
            String firstExPath = "Exercise_" + j + "_All_Sets";
            final String secondExPath = "Ex_" + j + "_All_Sets";
            final HashMap<String, String[]> allSets = new HashMap<>();
            final int finalJ = j -1;
            db.collection("users").document(userID).collection("CurrentWorkoutPlan").document(day).collection(firstExPath)
                    .document(secondExPath).collection("AllSets").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document: task.getResult()) {
                            Set set = document.toObject(Set.class);
                            day1Exercises[finalJ].addSet(set);
                        }
                    } else {
                        Log.d("ERROR", "Error getting documents", task.getException());
                    }
                }
            });
        }
    }
    private void getWorkoutData2(int dayNum) {
        int dayTracker = dayNum;
        db = FirebaseFirestore.getInstance();
        String day = "Day_" + dayTracker;
        for(int j=1; j<8; j++) {
            String firstExPath = "Exercise_" + j + "_All_Sets";
            final String secondExPath = "Ex_" + j + "_All_Sets";
            final HashMap<String, String[]> allSets = new HashMap<>();
            final int finalJ = j -1;
            db.collection("users").document(userID).collection("CurrentWorkoutPlan").document(day).collection(firstExPath)
                    .document(secondExPath).collection("AllSets").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document: task.getResult()) {
                            Set set = document.toObject(Set.class);
                            day2Exercises[finalJ].addSet(set);
                        }
                    } else {
                        Log.d("ERROR", "Error getting documents", task.getException());
                    }
                }
            });
        }
    }
    private void getWorkoutData3(int dayNum) {
        int dayTracker = dayNum;
        db = FirebaseFirestore.getInstance();
        String day = "Day_" + dayTracker;
        for(int j=1; j<8; j++) {
            String firstExPath = "Exercise_" + j + "_All_Sets";
            final String secondExPath = "Ex_" + j + "_All_Sets";
            final HashMap<String, String[]> allSets = new HashMap<>();
            final int finalJ = j -1;
            db.collection("users").document(userID).collection("CurrentWorkoutPlan").document(day).collection(firstExPath)
                    .document(secondExPath).collection("AllSets").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document: task.getResult()) {
                            Set set = document.toObject(Set.class);
                            day3Exercises[finalJ].addSet(set);
                        }
                    } else {
                        Log.d("ERROR", "Error getting documents", task.getException());
                    }
                }
            });
        }
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("BUILT", "Workouts built");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

