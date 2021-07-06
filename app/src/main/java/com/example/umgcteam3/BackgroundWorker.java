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

    @Override
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
        getWorkout("UpperBody", upperBodyExercises.length, upperBodyExercises);
        getWorkout("Abdominals", abdominalExercises.length, abdominalExercises);
        getWorkout("LowerBody", lowerBodyExercises.length, lowerBodyExercises);
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
}

