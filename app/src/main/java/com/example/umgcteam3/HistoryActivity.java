package com.example.umgcteam3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class HistoryActivity extends AppCompatActivity {

    int abs, upper, lower;
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
        int[] historyNumbers = MainActivity.numbers;
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append(Integer.toString(historyNumbers[0]) + " exercises completed");
        abTotal.setText(stringBuilder.toString());
        stringBuilder = new StringBuilder("");
        stringBuilder.append(Integer.toString(historyNumbers[1]) + " exercises completed");
        upperTotal.setText(stringBuilder.toString());
        System.out.println(stringBuilder.toString());
        stringBuilder = new StringBuilder("");
        stringBuilder.append(Integer.toString(historyNumbers[2]) + " exercises completed");
        lowerTotal.setText(stringBuilder.toString());

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        FirebaseAuth fAuth = FirebaseAuth.getInstance();
//        String user_id = fAuth.getCurrentUser().getUid();
//
//        DocumentReference documentReference = db.collection("users").document(user_id);
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                if (documentSnapshot != null && documentSnapshot.exists()) {
//                    System.out.println("Updating textview");
//                    try {
//                        abs = (int) documentSnapshot.get("AbdominalWorkoutTotal");
//                        upper = (int) documentSnapshot.get("UpperBodyWorkoutTotal");
//                        lower = (int) documentSnapshot.get("LowerBodyWorkoutTotal");
//                        StringBuilder stringBuilder = new StringBuilder("");
//                        stringBuilder.append(Integer.toString(abs) + " exercises completed");
//                        abTotal.setText(stringBuilder.toString());
//                        stringBuilder = new StringBuilder("");
//                        stringBuilder.append(Integer.toString(upper) + " exercises completed");
//                        upperTotal.setText(stringBuilder.toString());
//                        System.out.println(stringBuilder.toString());
//                        stringBuilder = new StringBuilder("");
//                        stringBuilder.append(Integer.toString(lower) + " exercises completed");
//                        lowerTotal.setText(stringBuilder.toString());
//                    } catch (Exception exception){
//                    }
//
//                } else {
//                    Log.d("tag", "onEvent: Document does not exist");
//                }
//            }
//        });
    }
    public void proceedToWorkout(View view) {
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));

    }

}
