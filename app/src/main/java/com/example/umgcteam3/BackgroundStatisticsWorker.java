package com.example.umgcteam3;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class BackgroundStatisticsWorker extends AsyncTask<Void, Void, String> {
    FirebaseFirestore db;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userID;
    ArrayList<String> allExNames = new ArrayList<>();
    StatisticsReport statisticsReport;

    public BackgroundStatisticsWorker(){
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
    }

    @Override
    protected String doInBackground(Void... voids) {
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
                          List<String> dates = (List<String>) document.get("dateCompleted");
                          buildReport(dates, exName);
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
    private void buildReport(List<String> dates, String exName) {
        List weights = new ArrayList();
        for (String date: dates ) {
            db.collection("users").document(userID).collection("CompletedWorkouts").document(exName)
                    .collection("AllCompleted").document(date).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            try {
                                Object weightUsed = document.get("WeightUsed");
                                weights.add(weightUsed);
                            } catch (Exception exc) {
                                System.out.println(exc.getMessage());
                            }
                            //statisticsReport.addData(exName, );
                        }
                    });
        }
        statisticsReport.addData(exName, dates, weights);
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
