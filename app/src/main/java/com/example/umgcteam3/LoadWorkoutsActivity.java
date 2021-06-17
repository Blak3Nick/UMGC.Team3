package com.example.umgcteam3;



import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.content.Context;



public class LoadWorkoutsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loaded_workout_activity);
        onLogin();
    }

    public void day_1_workout_generator(View view) {
        Intent getDay1Overview = new Intent(this, OverviewActivity.class);
        getDay1Overview.putExtra("day_number", 1);
        final int result = 1;
        startActivity(getDay1Overview);
    }
    public void day_2_workout_generator(View view) {
        Intent getDay2Overview = new Intent(this, OverviewActivity.class);
        getDay2Overview.putExtra("day_number", 2);
        final int result = 1;
        startActivity(getDay2Overview);
    }
    public void day_3_workout_generator(View view) {
        Intent getDay3Overview = new Intent(this, OverviewActivity.class);
        getDay3Overview.putExtra("day_number", 3);
        final int result = 1;
        startActivity(getDay3Overview);
    }
    public void day_4_workout_generator(View view) {
        Intent getDay4Overview = new Intent(this, OverviewActivity.class);
        getDay4Overview.putExtra("day_number", 4);
        final int result = 1;
        startActivity(getDay4Overview);
    }

    public void circuit_day_generator(View view) {
        // TODO
//        Intent getCircuitOverview = new Intent(this, circuit_Overview_Activity.class);
//        final int result = 1;
//
//        startActivity(getCircuitOverview);

    }


    public void onLogin (View view) {
        //Build Workouts button
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute();
//        BackgroundWorkerCircuit circuit = new BackgroundWorkerCircuit(this);
//        circuit.execute();

    }
    public void onLogin () {
        //Build Workouts button
        LoginActivity loginActivity = new LoginActivity();
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute();
//        BackgroundWorkerCircuit circuit = new BackgroundWorkerCircuit(this);
//        circuit.execute();
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

