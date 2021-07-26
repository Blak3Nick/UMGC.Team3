package com.example.umgcteam3;



import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import android.content.Context;



public class DashboardActivity extends AppCompatActivity{
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.dashboard_activity);
        buildWorkouts();
        BackgroundStatisticsWorker backgroundStatisticsWorker = new BackgroundStatisticsWorker();
        backgroundStatisticsWorker.doInBackground();
    }

    public void workoutSelection(View view) {
        Intent workoutSelectionPage = new Intent(this, WorkoutSelectionActivity.class);
        startActivity(workoutSelectionPage);
    }
    public void profileView(View view) {
        Intent profileClass = new Intent(this, MainActivity.class);
        startActivity(profileClass);
    }
    public void progressView(View view) {
        Intent progressClass = new Intent(this, StatisticsActivity.class);
        startActivity(progressClass);
    }
    public void returnToMain(View view) {
        Intent mainClass = new Intent(this, MainActivity.class);
        startActivity(mainClass);
    }


    public void buildWorkouts(View view) {
        //Build Workouts button
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute();


    }
    public void buildWorkouts() {
        System.out.println("Building workouts");
        //Build Workouts button
        //LoginActivity loginActivity = new LoginActivity();
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute();
    }


//Firebase boilerplate

    @VisibleForTesting
    public ProgressBar mProgressBar;

    public void setProgressBar(ProgressBar progressBar) {
        mProgressBar = progressBar;
    }

    public void showProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void hideKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressBar();
    }
    public void returnToDashboard(View view) {

    }


}

