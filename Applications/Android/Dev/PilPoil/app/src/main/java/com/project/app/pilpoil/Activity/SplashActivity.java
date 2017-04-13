package com.project.app.pilpoil.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.project.app.pilpoil.R;

public class SplashActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash_screen);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTimeIntro", false)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTimeIntro", true);
            editor.commit();
            handler2.sendEmptyMessageDelayed(0, 2000);
        } else {
            handler.sendEmptyMessageDelayed(0, 2000);
        }

    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            loadMainActivity();
        }
    };

    private Handler handler2 = new Handler() {
        public void handleMessage(Message msg) {
            loadIntroActivity();
        }
    };

    private void loadIntroActivity(){
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}