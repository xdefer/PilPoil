package com.project.app.pilpoil.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

import com.project.app.pilpoil.R;

public class SplashScreen extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTimeIntro", false)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTimeIntro", true);
            editor.commit();
            loadIntroActivity();
        } else {
            this.setContentView(R.layout.activity_splash_screen);
            handler.sendEmptyMessageDelayed(0, 2000);
        }

    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            loadMainActivity();
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
