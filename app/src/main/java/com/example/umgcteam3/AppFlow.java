package com.example.umgcteam3;
//EDITED BY LCIGARRUISTA
//This class is meant to handle the flow of the screens.
//This class is also still in development.
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AppFlow extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //starts the code under LoginActivity when the SplashScreen starts - this file is called in
        startActivity(new Intent(this, LoginActivity.class));
    }
}
