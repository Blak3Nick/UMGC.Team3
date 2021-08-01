package com.example.umgcteam3;

//EDITED BY LCIGARRUISTA
//SplashScreen contains the code to handle the splash screen && the LoginActivity.java file
//This file is called under AndroidManifest line 48
// [DO NOT DELETE OR EDIT]

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //sets layout & starts the splash screen
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                finish();   //this is so the user can't return to the splash screen.
            }
        }, 3000);    //1000 milliseconds = 1 second, thus it'll delay 3 seconds.
    }

}
