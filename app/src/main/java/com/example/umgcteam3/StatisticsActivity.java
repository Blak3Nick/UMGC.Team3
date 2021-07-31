package com.example.umgcteam3;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class StatisticsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    GraphView graph;
    LineGraphSeries<DataPoint> series;
    private Spinner spinner;
    TextView fullName;
    ImageView profileImage;
    StorageReference storageReference;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    public String userId;
    FirebaseUser user;
    List<String> spinnerItems;
    HashMap<String, List[]> statisticsData = StatisticsReport.data;
    ArrayAdapter<String> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        fullName = findViewById(R.id.fullName);
        profileImage = findViewById(R.id.profileImage);

        spinner = findViewById(R.id.exercise_name_spinner);
        spinner.setOnItemSelectedListener(this);
        spinnerItems = new ArrayList<>();
        spinnerItems.add("Select an exercise...");

        graph = findViewById(R.id.graph);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.WHITE);
        graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);
        graph.getGridLabelRenderer().setGridColor(Color.WHITE);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph.getGridLabelRenderer().setVerticalAxisTitle("Weight");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Day");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if (statisticsData != null){
            for (Map.Entry<String, List[]> lists : statisticsData.entrySet()){

                List[] arrayList = lists.getValue();
                if (arrayList[0] != null && arrayList[1] != null){
                    spinnerItems.add(lists.getKey().replace("_", " "));
                }
            }
            adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerItems);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        } else {
            Log.d("StatisticsActivity: ", "no data found");
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
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void processExerciseData(String exercise){
        List[] list = statisticsData.get(exercise);
        List<Integer> weights = list[1].stream().mapToInt(num -> Integer.parseInt(num.toString())).boxed().collect(Collectors.toList());
        int max = Collections.max(weights);
        int min = Collections.min(weights);

        graph.getViewport().setMaxY(max + 5);
        graph.getViewport().setMinY(min - 5);
        graph.getViewport().setMaxX(5);
        graph.getViewport().setMinX(0);

        System.out.println("Max: " + max);
        for (int i = 0; i < list[0].size() ; i++){
            List<Object> weightData = list[1];
            series.appendData(new DataPoint(i, Integer.valueOf(String.valueOf(weightData.get(i)))), true, 30);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        series = new LineGraphSeries<>();


        if (position != 0){
            graph.removeAllSeries();
            String selectedItem = parent.getItemAtPosition(position).toString();
            graph.getViewport().setScrollable(true);
            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setXAxisBoundsManual(true);
            processExerciseData(selectedItem.replace(" ", "_"));
            graph.onDataChanged(true, true);
            graph.addSeries(series);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
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
        finish();
    }
    public void goToHistory(View view){
        Intent history = new Intent(this, HistoryActivity.class);
        startActivity(history);
    }
}
