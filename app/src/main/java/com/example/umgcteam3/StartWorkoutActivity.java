package com.example.umgcteam3;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class StartWorkoutActivity extends AppCompatActivity {
    private volatile boolean stopThread = false;
    int count;
    int setNumber;
    int exerciseNumber;
    TextView exercise1;
    TextView weightNumber;
    TextView setNumberTextDesc;
    TextView repNumber;
    TextView rpeNumber;
    Integer day_number;
    Exercise[] allExercises;
    int exercise_count;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();
    Set currentSet;
    private final String SETNUMBERSTRING = "setNumber";
    private final String EXNUMBERSTRING = "exerciseNumber";
    boolean increaseWeight = false;
    String workoutType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.start_workout_layout);
        count = extras.getInt("count");
        exerciseNumber = extras.getInt(EXNUMBERSTRING);
        setNumber = extras.getInt(SETNUMBERSTRING);
        exercise_count = extras.getInt("exercise_count");
        exercise1 = (TextView) findViewById(R.id.exercise_name);
        allExercises = (Exercise[]) extras.get("AllExercises");
        Exercise exercise = allExercises[exercise_count];
        workoutType = extras.getString("workoutType");

        currentSet = exercise.getSet(count);
        exercise1.setText(currentSet.getExerciseName());

        setNumberTextDesc = (TextView) findViewById(R.id.actualSetNumber);

        setNumberTextDesc.setText(Integer.toString(count+1));

        weightNumber = findViewById(R.id.weight_number);
        weightNumber.setText(Integer.toString(currentSet.getWeightUsed()));

        repNumber = findViewById(R.id.rep_number);
        repNumber.setText(Integer.toString(currentSet.getTargetReps()));

        rpeNumber = findViewById(R.id.actualRPENumber);
        rpeNumber.setText(Integer.toString(currentSet.getTargetRPE()));
        count++;
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textViewProgress);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < progressBar.getMax()) {
                    if(stopThread){
                        stopThread = false;
                        return;
                    }
                    progressStatus += 1;
                    if(stopThread){
                        stopThread = false;
                        return;
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            textView.setText(progressStatus + "/" + progressBar.getMax());
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }



    public void goHome(View view) {
        Intent loadHomePage = new Intent(this, MainActivity.class);
        final int result = 1;
        startActivity(loadHomePage);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateWorkoutPage(View view) {
        Intent reloadPage = new Intent(this, StartWorkoutActivity.class);
        int last_exercise = 6;
        if(count == 5 ) {
            exercise_count++;
            count =0;
        }
        reloadPage.putExtra("count", count);
        reloadPage.putExtra("exercise_count", exercise_count);
        reloadPage.putExtra("AllExercises", allExercises);
        reloadPage.putExtra("workoutType", workoutType);
        sendData();
        stopThread = true;
        if (exercise_count == last_exercise) {
            Intent loadCompleted = new Intent(this, CompletedWorkoutWorker.class);
            startActivity(loadCompleted);

        }else {
            startActivity(reloadPage);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sendData() {
        String ex_name = "" + exercise1.getText();
        String weightString = "" + weightNumber.getText();
        String setString = "" +  setNumberTextDesc.getText();
        String stringRepNumber = "" +  repNumber.getText();
        String stringRPENumber = "" +  rpeNumber.getText();
        int reportedRPE = currentSet.getTargetRPE();
        int weight = 0;
        int set = 1;
        int reps = 5;
        int rpe = -1;
        try {
            reps = Integer.parseInt(stringRepNumber);
            weight = Integer.parseInt(weightString);
            set = Integer.parseInt(setString);
            rpe = Integer.parseInt(stringRPENumber);
            reportedRPE = Integer.parseInt(rpeNumber.getText().toString());
        } catch (Exception ex) {
            System.out.println("COULD NOT get one of the numbers from get text");
        }
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        System.out.println("Date = " + strDate);

        int targetRPE = currentSet.getTargetRPE();
        if (reportedRPE - targetRPE < -1 ) {
            increaseWeight = true;
            UpdateWorkout updateWorkout = new UpdateWorkout();
            System.out.println(workoutType + "is the workout type" + exerciseNumber + "is the exercise number\n\n\n\n\n");
            updateWorkout.updateCurrentWorkout(workoutType, exerciseNumber, true, setNumber, exerciseNumber );
        }
        else if(reportedRPE - targetRPE > 1) {
            UpdateWorkout updateWorkout = new UpdateWorkout();
            updateWorkout.updateCurrentWorkout(workoutType, exerciseNumber, false, setNumber, exerciseNumber );
        }

        UpdateDatabase updateDatabase = new UpdateDatabase(this, set, reps, rpe, weight, 1, ex_name, strDate, false);
        updateDatabase.execute();
    }
    public void endWorkout(View view) {
        Intent loadCompleted = new Intent(this, CompletedWorkoutWorker.class);
        startActivity(loadCompleted);
    }
}
