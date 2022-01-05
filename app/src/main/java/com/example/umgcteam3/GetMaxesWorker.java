package com.example.umgcteam3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;


@RequiresApi(api = Build.VERSION_CODES.N)
public class GetMaxesWorker extends AppCompatActivity {
    TextView enterMax;
    String[] initialMaxes = {"Back Squat", "Deadlift", "Barbell Bench Press", "Overhead Press", "Barbell Curl", "Barbell Row"};
    Button submitmaxbutton;
    int counter;
    TextView exercise;
    int[] userMaxes = new int[6];
    String userId;
    public final static String SHARED_PREFS = "shared prefs";
    public final static String BACK_SQUAT = "Back Squat";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_maxes);
        Bundle extras = getIntent().getExtras();
        counter = extras.getInt("counter");
        userId = extras.getString("userid");
        enterMax = (TextView) findViewById(R.id.enterMax);
        exercise = (TextView) findViewById(R.id.exerciseNameForMax);
        submitmaxbutton = (Button) findViewById(R.id.submitMaxButton);
        exercise.setText(initialMaxes[counter]);

        submitmaxbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int max =0;
                try {
                    max = Integer.parseInt(enterMax.getText().toString());
                    System.out.println("the max entered was: " + max);
                    userMaxes[counter] = max;
                    SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt(initialMaxes[counter], max);
                    editor.apply();
                    counter++;
                    if(counter>=userMaxes.length){
                        buildWorkouts();
                    }
                    else {
                        nextMax();
                    }

                } catch (Exception exception) {
                    exception.printStackTrace();
                }


            }
        });
    }
    void nextMax(){
        exercise.setText(initialMaxes[counter]);
    }

    void buildWorkouts(){
        InitialWorkoutBuilder workoutBuilder = new InitialWorkoutBuilder(userMaxes);
        workoutBuilder.doInBackground();
        WorkoutOneStrengthBaseDayOne workoutOneStrengthBaseDayOne = new WorkoutOneStrengthBaseDayOne(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Boolean> workout = new HashMap<>();
        workout.put("workoutsBuilt", true);

        db.collection("users").document(userId).set(workout, SetOptions.merge())
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
        Map<String, Integer> workoutTotals = new HashMap<>();
        workoutTotals.put("AbdominalWorkoutTotal", 0);
        workoutTotals.put("UpperBodyWorkoutTotal", 0);
        workoutTotals.put("LowerBodyWorkoutTotal", 0);
        db.collection("users").document(userId).set(workoutTotals, SetOptions.merge())
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
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

}
