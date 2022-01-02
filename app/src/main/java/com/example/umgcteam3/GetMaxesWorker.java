package com.example.umgcteam3;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


@RequiresApi(api = Build.VERSION_CODES.N)
public class GetMaxesWorker extends AppCompatActivity {
    TextView enterMax;
    String[] initialMaxes = {"Back Squat", "Deadlift", "Barbell Bench Press", "Overhead Press", "Barbell Curl", "Barbell Row"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_maxes);
        Bundle extras = getIntent().getExtras();
        int counter = extras.getInt("counter");
        enterMax = (TextView) findViewById(R.id.enterMax);
        TextView exercise = (TextView) findViewById(R.id.exerciseNameForMax);
        exercise.setText(initialMaxes[counter]);
        int max =0;
        try {
            max = Integer.parseInt(enterMax.getText().toString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

}
