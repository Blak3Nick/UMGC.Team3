package com.example.umgcteam3;



import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

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
        setContentView(R.layout.dashboard_activity);
        buildWorkouts();
        BackgroundStatisticsWorker backgroundStatisticsWorker = new BackgroundStatisticsWorker();
        backgroundStatisticsWorker.doInBackground();
    }

    public void workoutSelection(View view) {
        Intent workoutSelectionPage = new Intent(this, WorkoutSelectionActivity.class);
        startActivity(workoutSelectionPage);
    }
    public void mantraView(View view) {
        Intent getDay2Overview = new Intent(this, WorkoutSelectionActivity.class);
        startActivity(getDay2Overview);
    }
    public void progressView(View view) {
        Intent getDay3Overview = new Intent(this, StatisticsActivity.class);
        startActivity(getDay3Overview);
    }



    public void buildWorkouts(View view) {
        //Build Workouts button
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute();


    }
    public void buildWorkouts() {
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


}

