package com.example.hypnochi;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.LongDef;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 10, 0);

        try {
            MediaPlayer mp = new MediaPlayer();
            AssetFileDescriptor afd = getAssets().openFd("swoosh.wav");
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),(int) (afd.getLength()*1.1));
            mp.prepare();
            mp.start();
        } catch (Exception ignore) {}

        SharedPreferences sharedPreferences = getSharedPreferences("hypnochi_speicher", Context.MODE_PRIVATE);
        boolean opened = sharedPreferences.getBoolean("opened", false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (!opened) {
                    intent = new Intent(LoadingScreen.this, StartScreen.class);
                } else {
                    intent = new Intent(LoadingScreen.this, StartScreen.class);
                }
                startActivity(intent);
                finish();
            }
        }, 6200);
    }
}
