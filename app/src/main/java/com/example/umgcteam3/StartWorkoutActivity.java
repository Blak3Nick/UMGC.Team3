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
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    int totalExercises;
    String strDate = "0000-00-00";
    int last_exercise;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.start_workout_layout);
        count = extras.getInt("count");
        totalExercises = extras.getInt("TotalExercises");
        exerciseNumber = extras.getInt(EXNUMBERSTRING);
        setNumber = extras.getInt(SETNUMBERSTRING);
        exercise_count = extras.getInt("exercise_count");
        exercise1 = (TextView) findViewById(R.id.exercise_name);
        allExercises = (Exercise[]) extras.get("AllExercises");
        Exercise exercise = allExercises[exercise_count];
        workoutType = extras.getString("workoutType");
        switch (workoutType){
            case "Abdominals":
                allExercises = BackgroundWorker.abdominalExercises;
                break;
            case "LowerBody":
                allExercises = BackgroundWorker.lowerBodyExercises;
                break;
            case "UpperBody":
                allExercises = BackgroundWorker.upperBodyExercises;
            case "Day1":
                allExercises = BackgroundWorker.day_1_exercises;
                last_exercise = WorkoutOneStrengthBaseDayOne.totalExercises;
        }
        System.out.println(count + " is the count");
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
        progressBar.setMax(currentSet.getRestPeriod());
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

        if(count == 5 ) {
            exercise_count++;
            count =0;
        }
        reloadPage.putExtra("count", count);
        reloadPage.putExtra("exercise_count", exercise_count);
        reloadPage.putExtra("AllExercises", allExercises);
        reloadPage.putExtra("workoutType", workoutType);
        totalExercises++;
        reloadPage.putExtra("TotalExercises", totalExercises);


        allExercises = sendData();
        stopThread = true;
        if (exercise_count == last_exercise) {
            endWorkout(this.getCurrentFocus());

        }else {
            startActivity(reloadPage);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Exercise[] sendData() {
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
        strDate = dateFormat.format(date);

        int targetRPE = currentSet.getTargetRPE();
        if (reportedRPE - targetRPE < -1 ) {
            increaseWeight = true;
            UpdateWorkout updateWorkout = new UpdateWorkout();
            allExercises = updateWorkout.updateCurrentWorkout(workoutType, exerciseNumber, true, setNumber, exerciseNumber+1 );
        }
        else if(reportedRPE - targetRPE > 1) {
            UpdateWorkout updateWorkout = new UpdateWorkout();
            allExercises = updateWorkout.updateCurrentWorkout(workoutType, exerciseNumber, false, setNumber, exerciseNumber+1 );
        }

        UpdateDatabase updateDatabase = new UpdateDatabase(this, set, reps, rpe, weight,  ex_name, strDate,  workoutType);
        updateDatabase.execute();

        return allExercises;
    }
    public void endWorkout(View view) {
        updateProgress(strDate);
        Intent loadCompleted = new Intent(this, CompletedWorkoutWorker.class);
        loadCompleted.putExtra("TotalExercises", totalExercises);
        loadCompleted.putExtra("workoutType", workoutType);
        startActivity(loadCompleted);
    }
    @WorkerThread
    protected void updateProgress(String exDate){
        LogCompletedWorkout logCompletedWorkout = new LogCompletedWorkout(exDate);
        logCompletedWorkout.doInBackground();
    }
}
