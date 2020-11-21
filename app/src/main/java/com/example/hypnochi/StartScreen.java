package com.example.hypnochi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class StartScreen extends AppCompatActivity {

    private Map<String, Integer> startTime = new HashMap<>();
    private final String startTime_api_url = "https://scivolemo.000webhostapp.com/AppOpening/opening_api.php?authentification=p97Fg5b3HkJ5";
    private Timer timer;

    private TextView remainingTimeText;
    private TextView showBeginnsText;

    private RemainingTimeViewModel remainingTimeViewModel;


    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted) {
                    AlertDialog.Builder warning = new AlertDialog.Builder(StartScreen.this);
                    warning.setTitle("Diese App benötigt diese Berechtigung um zu funktionieren!");
                    warning.setPositiveButton("OK", (dialog, which) -> {
                        dialog.cancel();
                        if (!checkInternetPermission()) {
                            requestInternetPermission();
                        }
                        if (!checkModifyAudioSettingsPermission()) {
                            requestModifyAudioSettingsPermissio();
                        }
                    });
                    warning.show();
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        remainingTimeText = findViewById(R.id.remaining_time_text);
        showBeginnsText = findViewById(R.id.show_beginns_text);

        if (!checkInternetPermission()) {
            requestInternetPermission();
        }
        if (!checkModifyAudioSettingsPermission()) {
            requestModifyAudioSettingsPermissio();
        }

        remainingTimeViewModel = new ViewModelProvider(this).get(RemainingTimeViewModel.class);

        final Observer<String> remainingTimeObserver = s -> remainingTimeText.setText(s);

        remainingTimeViewModel.getCurrentRemainingTime().observe(this, remainingTimeObserver);

        this.startTime.put("days", 9);
        this.startTime.put("month", 11);
        this.startTime.put("years", 2020);
        this.startTime.put("hours", 24);
        this.startTime.put("minutes", 60);
        this.startTime.put("seconds", 60);

        updateStartTime();

        int DELAY_MILLISECONDS = 30000;

        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateStartTime();
                Log.d("StartTime", "aktualisiert");
            }
        }, 0, DELAY_MILLISECONDS);

        int DELAY_OPENING_CHECK = 250;

        Timer openingCheckTimer = new Timer();
        openingCheckTimer.scheduleAtFixedRate(new TimerTask() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                List<Integer> timeLeft = getTimeLeft();
                Log.d("timeLeft", timeLeft.toString());
                if (timeLeft.get(0) == 0 && timeLeft.get(1) == 0 && timeLeft.get(2) == 0 && timeLeft.get(3) == 0) {
                    openingCheckTimer.cancel();
                    timer.cancel();
                    remainingTimeText.setVisibility(View.INVISIBLE);
                    showBeginnsText.setVisibility(View.INVISIBLE);
                    AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 4, 0);
                    try {
                        AssetFileDescriptor afd = getAssets().openFd("main_theme.mp3");
                        MediaPlayer openingMusicPlayer = new MediaPlayer();
                        openingMusicPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        openingMusicPlayer.prepare();
                        openingMusicPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    SharedPreferences sharedPreferences = getSharedPreferences("hypnochi_speicher", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("opened", true);
                    editor.apply();

                    Intent intent = new Intent(StartScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();


                } else {
                    remainingTimeViewModel.getCurrentRemainingTime().postValue(String.format("%dd %dh %dm %ds",
                            timeLeft.get(0), timeLeft.get(1), timeLeft.get(2), timeLeft.get(3)));
                }
            }
        }, 2000, DELAY_OPENING_CHECK);

    }

    private void updateStartTime() {
        RequestQueue queue = Volley.newRequestQueue(StartScreen.this);

        StringRequest request = new StringRequest(Request.Method.GET, startTime_api_url, response -> {
            try {
                Log.d("Request", "Response arrived");
                JSONObject responseJSON = new JSONObject(response);

                String day = responseJSON.getString("Date");
                String time = responseJSON.getString("Time");

                String[] daySplit = day.split("\\.");
                String[] timeSplit = time.split(":");

                this.startTime.clear();

                this.startTime.put("days", Integer.parseInt(daySplit[0]));
                this.startTime.put("month", Integer.parseInt(timeSplit[1]));
                this.startTime.put("years", Integer.parseInt(timeSplit[2]));
                this.startTime.put("hours", Integer.parseInt(timeSplit[0]));
                this.startTime.put("minutes", Integer.parseInt(timeSplit[1]));
                this.startTime.put("seconds", Integer.parseInt(timeSplit[2]));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            AlertDialog.Builder warning = new AlertDialog.Builder(StartScreen.this);
            warning.setTitle("Fehler bei der Synchronisation");
            warning.setMessage("Die Zeit konnte nicht mit dem Server synchronisiert werden!");
            warning.setPositiveButton("OK", (dialog, which) -> dialog.cancel());
            warning.show();
        }
        );

        queue.add(request);
    }

    private List<Integer> getTimeLeft() {
        List<Integer> timeLeft = new ArrayList<>();

        int days;
        int hours;
        int minutes;
        int seconds;

        Calendar calendar = Calendar.getInstance();

        long startTime = this.startTime.get("days") * 24 * 60 * 60 +
                this.startTime.get("hours") * 60 * 60 + this.startTime.get("minutes") * 60 + this.startTime.get("seconds");
        long actualTime = calendar.get(Calendar.DAY_OF_MONTH) * 24 * 60 * 60 +
                calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND);
        long difference = startTime - actualTime;

        if (difference < 0) {
            Log.d("StartÜberschritten", "alle 0");
            days = 0;
            hours = 0;
            minutes = 0;
            seconds = 0;
        } else {
            days = (int) (difference / (60 * 60 * 24));
            hours = (int) (difference / (60 * 60) % 24);
            minutes = (int) (difference / 60 % 60);
            seconds = (int) (difference % 60);
        }



        //Log.d("Days", String.valueOf(days));
        //Log.d("hours", String.valueOf(hours));
        //Log.d("minutes", String.valueOf(minutes));
        //Log.d("seconds", String.valueOf(seconds));

        timeLeft.add(days);
        timeLeft.add(hours);
        timeLeft.add(minutes);
        timeLeft.add(seconds);

        return timeLeft;
    }

    private boolean checkInternetPermission() {
        return ContextCompat.checkSelfPermission(StartScreen.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestInternetPermission() {
        requestPermissionLauncher.launch(Manifest.permission.INTERNET);
    }

    private boolean checkModifyAudioSettingsPermission() {
        return ContextCompat.checkSelfPermission(StartScreen.this,
                Manifest.permission.MODIFY_AUDIO_SETTINGS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestModifyAudioSettingsPermissio() {
        requestPermissionLauncher.launch(Manifest.permission.MODIFY_AUDIO_SETTINGS);
    }


}

