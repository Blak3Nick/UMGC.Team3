package com.example.umgcteam3;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Gets the workout data from the Firestore Database
public class BackgroundWorker extends AsyncTask<Void, Void, String> {
    Context context;
    BackgroundWorker (Context ctx) {
        context = ctx;
    }
    FirebaseFirestore db;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userID;
    public static Exercise[] upperBodyExercises = new Exercise[UpperBodyExercise.values().length];
    public static Exercise[] lowerBodyExercises = new Exercise[LowerBodyExercise.values().length];
    public static Exercise[] abdominalExercises = new Exercise[AbdominalExercises.values().length];
    public static Exercise[] day_1_exercises = new Exercise[WorkoutOneStrengthBaseDayOne.totalExercises];
    public static Map<String, Map> completedExercises = new HashMap<>();

    @Override
    @WorkerThread
    protected String doInBackground(Void... voids) {
        userID = fAuth.getCurrentUser().getUid();
        for (int i = 0; i < upperBodyExercises.length; i++) {
            Exercise exercise = new Exercise(i+1);
            upperBodyExercises[i] = exercise;
        }
        for (int i = 0; i < lowerBodyExercises.length; i++) {
            Exercise exercise = new Exercise(i+1);
            lowerBodyExercises[i] = exercise;
        }
        for (int i = 0; i < abdominalExercises.length; i++) {
            Exercise exercise = new Exercise(i+1);
            abdominalExercises[i] = exercise;
        }
        for (int i = 0; i < day_1_exercises.length; i++) {
            Exercise exercise = new Exercise(i+1);
            day_1_exercises[i] = exercise;
        }
        getWorkout("UpperBody", upperBodyExercises.length, upperBodyExercises);
        getWorkout("Abdominals", abdominalExercises.length, abdominalExercises);
        getWorkout("LowerBody", lowerBodyExercises.length, lowerBodyExercises);
        getWorkoutForDay("Day_1", day_1_exercises.length, day_1_exercises);
        //uncomment the line below to insert historical dummy data for statistics
        //insertDummyDataForStatistics();
        System.out.println("Added all the workouts\n\n\n\n\n");
        return null;
    }
    private void getWorkout(String workoutType, int numberOfExercises, Exercise[] workout) {
        db = FirebaseFirestore.getInstance();
        for(int j=1; j<numberOfExercises; j++) {
            String firstExPath = "Exercise_" + j + "_All_Sets";
            final String secondExPath = "Ex_" + j + "_All_Sets";
            final int finalJ = j -1;
            db.collection("users").document(userID).collection("CurrentWorkoutPlan").document(workoutType).collection(firstExPath)
                    .document(secondExPath).collection("AllSets").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document: task.getResult()) {
                            Set set = document.toObject(Set.class);
                            workout[finalJ].addSet(set);
                        }
                    } else {
                        Log.d("ERROR", "Error getting documents", task.getException());
                    }
                }
            });
        }
    }
    private void getWorkoutForDay(String workout_day, int numberOfExercises, Exercise[] workout) {
        db = FirebaseFirestore.getInstance();
        for(int j=1; j<numberOfExercises; j++) {
            String firstExPath = "Exercise" + j;
            final String secondExPath = "Ex" + j + "AllSets";
            final int finalJ = j -1;
            db.collection("users").document(userID).collection("CurrentWorkoutPlan").document(workout_day).collection(firstExPath)
                    .document(secondExPath).collection("AllSets").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document: task.getResult()) {
                            Set set = document.toObject(Set.class);
                            workout[finalJ].addSet(set);
                        }
                    } else {
                        Log.d("ERROR", "Error getting documents", task.getException());
                    }
                }
            });
        }
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onPostExecute(String result) {
        Log.d("BUILT", "Workouts built");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public static void setUpperBodyExercises(Exercise[] upperBodyExercises) {
        BackgroundWorker.upperBodyExercises = upperBodyExercises;
    }

    public static void setLowerBodyExercises(Exercise[] lowerBodyExercises) {
        BackgroundWorker.lowerBodyExercises = lowerBodyExercises;
    }

    public static void setAbdominalExercises(Exercise[] abdominalExercises) {
        BackgroundWorker.abdominalExercises = abdominalExercises;
    }
    public static void setDay_1_exercises(Exercise[] day_1_exercises){
        BackgroundWorker.day_1_exercises = day_1_exercises;
    }
    private void insertDummyDataForStatistics() {
        for (LowerBodyExercise lowerBodyExercise: LowerBodyExercise.values()){
            String exName = lowerBodyExercise.toString();
            writeDummyData(exName);
        }
        for (UpperBodyExercise upperBodyExercise: UpperBodyExercise.values()) {
            String exName = upperBodyExercise.toString();
            writeDummyData(exName);
        }
        for (AbdominalExercises abdominalExercise : AbdominalExercises.values()) {
            String exName = abdominalExercise.toString();
            writeDummyData(exName);
        }
    }
    private void writeDummyData(String exName) {
        String[] dummyDates = {"2021-07-01", "2021-07-02", "2021-07-03", "2021-07-04",
                "2021-07-05", "2021-07-06", "2021-07-07", "2021-07-08", "2021-07-09",
                "2021-07-10"};
        Map<String, Map> completedSets = new HashMap<>();
        int[] dummyWeights = {130, 135, 140, 145, 135, 140, 150, 160, 170, 175};
        int RPE = 5;
        int Reps = 8;
        int SetNumber = 1;
        int i =0;
        for (String dummyDate: dummyDates) {
            if (i>9){
                i =0;
            }
            int WeightUsed = dummyWeights[i++];
            Map set = new HashMap();
            set.put("DateCompleted", dummyDate);
            set.put("RPE", RPE);
            set.put("Reps", Reps);
            set.put("WeightUsed", WeightUsed);
            db.collection("users").document(userID).collection("CompletedWorkouts")
                    .document(exName).collection("AllCompleted")
                    .document(dummyDate)
                    .set(set, SetOptions.merge())
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
            DocumentReference documentReference = db.collection("users").document(userID)
                    .collection("CompletedWorkouts").document(exName);
            documentReference.update("dateCompleted", FieldValue.arrayUnion(dummyDate));
        }
    }

}

