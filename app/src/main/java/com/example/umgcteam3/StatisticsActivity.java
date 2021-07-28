package com.example.umgcteam3;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {
    GraphView graph;
    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(getDataPoint());
    private Spinner spinner;
    TextView fullName;
    ImageView profileImage;
    StorageReference storageReference;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    public String userId;
    FirebaseUser user;
    List<String> spinnerItems;
    HashMap statisticsData = StatisticsReport.data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        fullName = findViewById(R.id.fullName);
        profileImage = findViewById(R.id.profileImage);
        spinner = findViewById(R.id.exercise_name_spinner);
        graph = findViewById(R.id.graph);
        graph.getGridLabelRenderer().setGridColor(Color.WHITE);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        spinnerItems = new ArrayList<>();
        List[] barbellData = (List[]) statisticsData.get("Barbell_Bench_Press");
        List datesData = barbellData[0];
        List weightsData = barbellData[1];
        for (int i =0; i < datesData.size(); i++){
            System.out.println("The data is as follows: Date " + datesData.get(i) + "\n"
            + "Weight: " + weightsData.get(i));
        }

        try {
            storageReference = FirebaseStorage.getInstance().getReference();
            userId = fAuth.getCurrentUser().getUid();
            user = fAuth.getCurrentUser();
            fullName.setText(user.getDisplayName());
            StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(profileImage);
                }
            });
        } catch (Exception e) {
            Log.d("StatisticsActivity: ", e.getMessage());
            finish();
        }

        AbdominalExercises[] abdominalArray = AbdominalExercises.values();
        for (AbdominalExercises item : abdominalArray){
            String exerciseItem = item.toString().replace("_", " ");
            spinnerItems.add(exerciseItem);
        }

        LowerBodyExercise[] lowerArray = LowerBodyExercise.values();
        for (LowerBodyExercise item : lowerArray){
            String exerciseItem = item.toString().replace("_", " ");
            spinnerItems.add(exerciseItem);
        }

        UpperBodyExercise[] upperArray = UpperBodyExercise.values();
        for (UpperBodyExercise item : upperArray){
            String exerciseItem = item.toString().replace("_", " ");
            spinnerItems.add(exerciseItem);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        graph.addSeries(series);
    }

    private DataPoint[] getDataPoint(){
        DataPoint[] dataPoints = new DataPoint[]{
                //grab data points from database based on selected drop-down
        };
        return dataPoints;
    }

    public void goToProfile(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
    public void proceedToWorkout(View view) {
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        finish();
    }
    public void returnToDashboard(View view) {
        Intent dashboard = new Intent(this, DashboardActivity.class);
        startActivity(dashboard);
    }
    public void goToHistory(View view){
        Intent history = new Intent(this, HistoryActivity.class);
        startActivity(history);
    }


}
