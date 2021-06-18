package com.example.umgcteam3;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.HashMap;

public class OverviewActivity extends Activity {
    Bundle extras;
    private static Workout WorkoutDay1;
    private static Workout WorkoutDay2;
    private static Workout WorkoutDay3;
    HashMap<Integer, Exercise> allExercises;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extras = getIntent().getExtras();
        WorkoutDay1 = new Workout();
        WorkoutDay2 = new Workout();
        WorkoutDay3 = new Workout();
        allExercises =  WorkoutDay1.getAllExercises();
        for (Exercise ex: BackgroundWorker.day1Exercises
        ) {
            WorkoutDay1.addExercise(ex);
        }
        for (Exercise ex: BackgroundWorker.day2Exercises
        ) {
            WorkoutDay2.addExercise(ex);
        }
        for (Exercise ex: BackgroundWorker.day3Exercises
        ) {
            WorkoutDay3.addExercise(ex);
        }
        LoginActivity.setWorkoutDay1(WorkoutDay1);
        LoginActivity.setWorkoutDay2(WorkoutDay2);
        LoginActivity.setWorkoutDay3(WorkoutDay3);
        Integer day_number = extras.getInt("day_number");
        HashMap<Integer, Exercise> allExercises =  WorkoutDay1.getAllExercises();
        setContentView(R.layout.activity_overview_layout);
        switch (day_number) {
            case (1):
                allExercises =  WorkoutDay1.getAllExercises();
                break;
            case (2):
                allExercises =  WorkoutDay2.getAllExercises();
                break;
            case (3):
                allExercises =  WorkoutDay3.getAllExercises();
                break;
            case (4):
                // TO DO
                break;
        }
        TextView exercise1 = (TextView) findViewById(R.id.exercise_1);
        Set set = allExercises.get(1).getSet(0);
        exercise1.setText(set.getExerciseName());
        TextView exercise1_setDetails = (TextView) findViewById(R.id.exercise_1_set_details);
        exercise1_setDetails.setText("5 sets of " + set.getTargetReps() + " reps");

        TextView exercise2 = findViewById(R.id.exercise2_text);
        set = allExercises.get(2).getSet(0);
        exercise2.setText(set.getExerciseName());
        TextView exercise2_setDetails = (TextView) findViewById(R.id.exercise2_set_details);
        exercise2_setDetails.setText("5 sets of " + set.getTargetReps() + " reps");

        TextView exercise3 = findViewById(R.id.exercise3_text);
        set = allExercises.get(3).getSet(0);
        exercise3.setText(set.getExerciseName());
        TextView exercise3_setDetails = (TextView) findViewById(R.id.exercise3_set_details);
        exercise3_setDetails.setText("5 sets of " + set.getTargetReps() + " reps");

        TextView exercise4 = findViewById(R.id.exercise4_text);
        set = allExercises.get(4).getSet(0);
        exercise4.setText(set.getExerciseName());
        TextView exercise4_setDetails = (TextView) findViewById(R.id.exercise4_set_details);
        exercise4_setDetails.setText("5 sets of " + set.getTargetReps() + " reps");

        TextView exercise5 = findViewById(R.id.exercise5_text);
        set = allExercises.get(5).getSet(0);
        exercise5.setText(set.getExerciseName());
        TextView exercise5_setDetails = (TextView) findViewById(R.id.exercise5_set_details);
        exercise5_setDetails.setText("5 sets of " + set.getTargetReps() + " reps");

        TextView exercise6 = findViewById(R.id.exercise6_text);
        set = allExercises.get(6).getSet(0);
        exercise6.setText(set.getExerciseName());
        TextView exercise6_setDetails = (TextView) findViewById(R.id.exercise6_set_details);
        exercise6_setDetails.setText("5 sets of " + set.getTargetReps() + " reps");

        TextView exercise7 = findViewById(R.id.exercise7_text);
        set = allExercises.get(7).getSet(0);
        exercise7.setText(set.getExerciseName());
        TextView exercise7_setDetails = (TextView) findViewById(R.id.exercise7_set_details);
        exercise7_setDetails.setText("5 sets of " + set.getTargetReps() + " reps");

    }
    public void goHome(View view) {
        Intent loadHomePage = new Intent(this, LoadWorkoutsActivity.class);
        final int result = 1;
        startActivity(loadHomePage);
    }

    public void startWorkout(View view) {
        Intent startDay1 = new Intent(this, StartWorkout.class);
        startDay1.putExtra("setNumber", 0);
        startDay1.putExtra("exerciseNumber", 1);
        int dayNum = extras.getInt("day_number");
        startDay1.putExtra("day_number", dayNum);
        startDay1.putExtra("count", 0);
        startDay1.putExtra("all_exercises", allExercises);
        startDay1.putExtra("exercise_count", 0);
        startActivity(startDay1);

        finish();
    }
}

