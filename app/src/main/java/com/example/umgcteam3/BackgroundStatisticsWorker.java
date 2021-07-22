package com.example.umgcteam3;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BackgroundStatisticsWorker extends AsyncTask<Void, Void, String> {
    FirebaseFirestore db;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userID;
    ArrayList<String> allExNames = new ArrayList<>();
    StatisticsReport statisticsReport;


    @Override
    protected String doInBackground(Void... voids) {
        for (LowerBodyExercise exName: LowerBodyExercise.values()) {
            String name = exName.toString();
            allExNames.add(name);
        }
        for (AbdominalExercises exName: AbdominalExercises.values()) {
            String name = exName.toString();
            allExNames.add(name);
        }
        for (UpperBodyExercise exName: UpperBodyExercise.values()) {
            String name = exName.toString();
            allExNames.add(name);
        }
        statisticsReport = new StatisticsReport();
        userID = fAuth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        statisticsReport = new StatisticsReport();

        for (String exName: allExNames) {
            db.collection("users").document(userID).collection("CompletedWorkouts").document(exName).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       DocumentSnapshot document = task.getResult();
                       Object allData= document.getData();
                       try {
                           List weightUsed = (List<Object>) document.get("weightUsed");
                           List dates = (List<Object>) document.get("dateCompleted");
                           statisticsReport.addData(exName, dates, weightUsed);
                       } catch (Exception exc) {
                           System.out.println(exc.getMessage());
                       }
                       Log.d("myTag", allData.toString());
                       //statisticsReport.addData(exName, );
                   }
               });
        }
        return "Finished";
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.equals("Finished")) {
            List[] allData = statisticsReport.getData("Barbell_Bench_Press");
            List<Object> dates = allData[0];
            for (Object point : dates) {
                String date = point.toString();
                System.out.println(date + " Is the date string");
            }
        }
    }
}
