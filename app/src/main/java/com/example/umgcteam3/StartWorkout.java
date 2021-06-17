package com.example.umgcteam3;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StartWorkout extends AppCompatActivity {
    private volatile boolean stopThread = false;
    ArrayList<String> exercise_name = new ArrayList<>();
    ArrayList<String> set_number = new ArrayList<>();
    ArrayList<String> reps = new ArrayList<>();
    ArrayList<String> weight = new ArrayList<>();
    ArrayList<String> rpe = new ArrayList<>();
    int count;
    TextView exercise1;
    TextView weightNumber;
    TextView setNumberTextDesc;
    TextView repNumber;
    TextView rpeNumber;
    Integer day_number;
    boolean increaseWeight = false;

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        try {
            increaseWeight = extras.getBoolean("increaseWeight");
            System.out.println("Assigned the boolean " + increaseWeight);
        }catch (Exception ex) {
            System.out.println("Could not assign boolean");
        }
        Calendar calendar = Calendar.getInstance();
        day_number = extras.getInt("day_number");
        System.out.println(day_number + " Is the day number");
        setContentView(R.layout.start_workout_layout);
        count = extras.getInt("count");
        exercise_name = extras.getStringArrayList("exercise_name");
        set_number = extras.getStringArrayList("set");
        System.out.println(set_number);
        reps = extras.getStringArrayList("exercise_category_name");
        rpe = extras.getStringArrayList("rpe");
        weight = extras.getStringArrayList("weight");

        exercise1 = (TextView) findViewById(R.id.exercise_name);
        exercise1.setText(exercise_name.get(count));

        setNumberTextDesc = (TextView) findViewById(R.id.actualSetNumber);
        Integer setNum = Integer.parseInt(set_number.get(count));
        System.out.println(setNum + " IS the set number");
        setNumberTextDesc.setText(set_number.get(count));

        weightNumber = findViewById(R.id.weight_number);
        weightNumber.setText(weight.get(count));

        repNumber = findViewById(R.id.rep_number);
        repNumber.setText(reps.get(count));

        rpeNumber = findViewById(R.id.actualRPENumber);
        rpeNumber.setText(rpe.get(count));


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
                    System.out.println("Progess:"   + progressStatus) ;
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
    public void updateWorkoutPage(View view) {
        Intent reloadPage = new Intent(this, StartWorkout.class);
        int last_exercise = exercise_name.size() -1;


        reloadPage.putExtra("day_number", 1);
        reloadPage.putExtra("exercise_name", exercise_name);
        reloadPage.putExtra("set", set_number);
        reloadPage.putExtra("exercise_category_name", reps);
        reloadPage.putExtra("rpe", rpe);
        reloadPage.putExtra("weight", weight);
        reloadPage.putExtra("count", count);
        sendData();
        reloadPage.putExtra("increaseWeight", increaseWeight);
        stopThread = true;
        if (count == last_exercise) {
            Intent loadCompleted = new Intent(this, CompletedWorkoutWorker.class);
            startActivity(loadCompleted);

        }else {
            startActivity(reloadPage);
        }
    }

    public void sendData() {
        String ex_name = "" + exercise1.getText();
        String weightString = "" + weightNumber.getText();
        String setString = "" +  setNumberTextDesc.getText();
        String stringRepNumber = "" +  repNumber.getText();
        String stringRPENumber = "" +  rpeNumber.getText();

        int weight = 0;
        int set = 1;
        int reps = 5;
        int rpe = -1;
        try {
            reps = Integer.parseInt(stringRepNumber);
            weight = Integer.parseInt(weightString);
            set = Integer.parseInt(setString);
            rpe = Integer.parseInt(stringRPENumber);
        } catch (Exception ex) {
            System.out.println("COULD NOT get one of the numbers from get text");
        }
        if (set == 5 && rpe <= 5) {
//            increaseWeight = true;
//            updateDatabaseExerciseWeightIncrease update = new updateDatabaseExerciseWeightIncrease(this, weight, ex_name);
//            update.execute();
        }
        UpdateDatabase updateDatabase = new UpdateDatabase(this, set, reps, rpe, weight, day_number, ex_name, false);
        updateDatabase.execute();
    }
    public void endWorkout(View view) {
//        BackgroundWorkerGetExercisesToIncrease getExercisesToIncrease = new BackgroundWorkerGetExercisesToIncrease(this);
//        getExercisesToIncrease.execute();
        Intent loadCompleted = new Intent(this, CompletedWorkoutWorker.class);
        loadCompleted.putExtra("increaseWeight", increaseWeight);

        //TO DO if true get data on the exercises needed to increase and offer user ability to review an increase if they want to
        startActivity(loadCompleted);
    }
}
