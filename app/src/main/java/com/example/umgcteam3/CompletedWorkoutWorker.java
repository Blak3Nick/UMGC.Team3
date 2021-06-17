package com.example.umgcteam3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CompletedWorkoutWorker extends Activity {

    boolean exerciseIncreaseWeight = false;
    String exercises_and_weights = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.completed_workout_layout);
        Bundle extras = getIntent().getExtras();

        try {
            exerciseIncreaseWeight = extras.getBoolean("increaseWeight");
            System.out.println(exerciseIncreaseWeight + "is it true?????");
//            exercises_and_weights = BackgroundWorkerGetExercisesToIncrease.exercises_and_weights;
//            System.out.println(exercises_and_weights);


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (exerciseIncreaseWeight == true) {
            TextView infoText = findViewById(R.id.infoTextIncrease);
            infoText.setVisibility(View.VISIBLE);

            Button review = findViewById(R.id.reviewButton);
            review.setVisibility(View.VISIBLE);

        }



    }
    public void assignExercises(View view) {
//        exercises_and_weights = BackgroundWorkerGetExercisesToIncrease.exercises_and_weights;
//        String[] arrayOfExercises = exercises_and_weights.split(" ");
    }

    public void goHome(View view) {
        Intent loadHomePage = new Intent(this, MainActivity.class);
        final int result = 1;
        startActivity(loadHomePage);
    }


}
