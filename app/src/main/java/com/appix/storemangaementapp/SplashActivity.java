package com.appix.storemangaementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.appix.storemangaementapp.LoginSignup.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    Constants constants;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        constants = new Constants(this);

        preferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        editor = preferences.edit();



        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();

            }
        }, 3000);
    }
}