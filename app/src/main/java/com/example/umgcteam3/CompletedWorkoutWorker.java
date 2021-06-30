package com.example.umgcteam3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import java.util.ArrayList;

//Could be used to overview progress from the completed workout
public class CompletedWorkoutWorker extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_workout_layout);
    }

    public void goHome(View view) {
        Intent loadHomePage = new Intent(this, MainActivity.class);
        final int result = 1;
        startActivity(loadHomePage);
    }
}
