package com.example.umgcteam3;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.util.HashMap;

public class WorkoutSelectionActivity extends Activity {
    Bundle extras;
    private static Workout UpperBodyWorkout;
    private static Workout LowerBodyWorkout;
    private static Workout AbdominalWorkout;
    HashMap<Integer, Exercise> allExercises;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extras = getIntent().getExtras();
        UpperBodyWorkout = new Workout();
        LowerBodyWorkout = new Workout();
        AbdominalWorkout = new Workout();
        allExercises =  UpperBodyWorkout.getAllExercises();
        for (Exercise ex: BackgroundWorker.upperBodyExercises
        ) {
            UpperBodyWorkout.addExercise(ex);
        }
        for (Exercise ex: BackgroundWorker.lowerBodyExercises
        ) {
            LowerBodyWorkout.addExercise(ex);
        }
        for (Exercise ex: BackgroundWorker.abdominalExercises
        ) {
            AbdominalWorkout.addExercise(ex);
        }
        LoginActivity.setUpperBodyWorkout(UpperBodyWorkout);
        LoginActivity.setLowerBodyWorkout(LowerBodyWorkout);
        LoginActivity.setAbdominalWorkout(AbdominalWorkout);
        setContentView(R.layout.workout_selection_activity);

    }
    public void goHome(View view) {
        Intent loadHomePage = new Intent(this, DashboardActivity.class);
        final int result = 1;
        startActivity(loadHomePage);
    }

    public void startUpperBodyWorkout(View view) {
        Intent startWorkout = new Intent(this, StartWorkoutActivity.class);
        startWorkout.putExtra("count", 0);
        startWorkout.putExtra("exercise_count", 0);
        startWorkout.putExtra("AllExercises", BackgroundWorker.upperBodyExercises);
        startWorkout.putExtra("workoutType", "UpperBody");
        startWorkout.putExtra("TotalExercises", 0);
        startActivity(startWorkout);
        finish();
    }
    public void startLowerBodyWorkout(View view) {
        Intent startWorkout = new Intent(this, StartWorkoutActivity.class);
        startWorkout.putExtra("count", 0);
        startWorkout.putExtra("exercise_count", 0);
        startWorkout.putExtra("AllExercises", BackgroundWorker.lowerBodyExercises);
        startWorkout.putExtra("workoutType", "LowerBody");
        startWorkout.putExtra("TotalExercises", 0);
        startActivity(startWorkout);
        finish();
    }
    public void startAbsBodyWorkout(View view) {
        Intent startWorkout = new Intent(this, StartWorkoutActivity.class);
        startWorkout.putExtra("count", 0);
        startWorkout.putExtra("exercise_count", 0);
        startWorkout.putExtra("AllExercises", BackgroundWorker.abdominalExercises);
        startWorkout.putExtra("workoutType", "Abdominals");
        startWorkout.putExtra("TotalExercises", 0);
        startActivity(startWorkout);
        finish();
    }
}

