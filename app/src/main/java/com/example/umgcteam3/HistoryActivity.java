package com.example.umgcteam3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        System.out.println("In history creation");
        updateUI();
    }
    private void updateUI(){
        System.out.println("Updating history");
        TextView upperTotal = (TextView) findViewById(R.id.upperExercises);
        TextView lowerTotal = (TextView) findViewById(R.id.lowerExercises);
        TextView abTotal = (TextView) findViewById(R.id.abExercises);
        Bundle extras = getIntent().getExtras();
        //int[] historyNumbers = extras.getIntArray("numbersHistory");
        long[] historyNumbers = MainActivity.numbers;
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append(Long.toString(historyNumbers[0]) + " exercises completed");
        abTotal.setText(stringBuilder.toString());
        stringBuilder = new StringBuilder("");
        stringBuilder.append(Long.toString(historyNumbers[1]) + " exercises completed");
        upperTotal.setText(stringBuilder.toString());
        System.out.println(stringBuilder.toString());
        stringBuilder = new StringBuilder("");
        stringBuilder.append(Long.toString(historyNumbers[2]) + " exercises completed");
        lowerTotal.setText(stringBuilder.toString());

    }
    public void proceedToWorkout(View view) {
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));

    }

}
