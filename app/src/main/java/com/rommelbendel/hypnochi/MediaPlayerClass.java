package com.example.hypnochi;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MediaPlayerClass extends AppCompatActivity {

    public static MediaPlayer openingMusicPlayer = new MediaPlayer();

    public void makeAssets() {

    }

    public static void SoundPlayer(Context context, AssetFileDescriptor afd){
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 12, 0);

        try {
            openingMusicPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            openingMusicPlayer.prepare();
            openingMusicPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
