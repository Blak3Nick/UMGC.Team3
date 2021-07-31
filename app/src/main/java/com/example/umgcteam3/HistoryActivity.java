package com.example.umgcteam3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HistoryActivity extends AppCompatActivity {
    TextView fullName;
    FirebaseUser user;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        System.out.println("In history creation");
        try {
            fullName = findViewById(R.id.fullName);
            fAuth = FirebaseAuth.getInstance();
            user = fAuth.getCurrentUser();
            fullName.setText(user.getDisplayName());
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }

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
    public void returnToDashboard(View view) {
        Intent dashboard = new Intent(this, DashboardActivity.class);
        startActivity(dashboard);
    }
    public void goToProfile(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
    public void goToStatistics(View view){
        MainActivity.backgroundStatisticsWorker.doInBackground();
        startActivity(new Intent(getApplicationContext(),StatisticsActivity.class));
    }
}
