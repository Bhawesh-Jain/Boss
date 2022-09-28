package com.boss.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.boss.R;
import com.boss.util.Session;


public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_SCREEN_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Window window = getWindow();
        window.setStatusBarColor(getColor(R.color.black));

        Session session = new Session(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                if (session.isLoggedIn())
                    i = new Intent(SplashActivity.this, HomeActivity.class);
                else
                    i = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                finish();
            }

        }, SPLASH_SCREEN_TIME_OUT);
    }
}