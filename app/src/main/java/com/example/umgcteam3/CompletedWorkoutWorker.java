package com.example.umgcteam3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

//Creates a completion screen for the user with a Dashboard button to return
public class CompletedWorkoutWorker extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_workout_layout);
    }

    public void returnToDashboard(View view) {
        Intent dashboard = new Intent(this, DashboardActivity.class);
        startActivity(dashboard);
    }

}
