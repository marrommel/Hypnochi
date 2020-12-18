package com.example.hypnochi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hypnochi.Service.OnClearFromRecentService;

import java.util.ArrayList;
import java.util.List;

public class NotficationActivity extends AppCompatActivity implements Playable {

    ImageView play;
    TextView title;
    NotificationManager nm;
    List<Track> tracks;
    int position = 0;
    boolean isPlaying = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        play = findViewById(R.id.play);
        title = findViewById(R.id.title);

        populateTracks();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            createChannel();

            registerReceiver(br, new IntentFilter("TRACKS_TRACKS"));
            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        }

        play.setOnClickListener(view -> {

            if(isPlaying) {
                onTrackPause();
            } else {
                onTrackPlay();
            }
        });
    }

    private void createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(DoNotification.ChannelId,
                    getString(R.string.app_name), NotificationManager.IMPORTANCE_LOW
            );

            nm = getSystemService(NotificationManager.class);

            assert nm != null;
            nm.createNotificationChannel(channel);
        }
    }

    private void populateTracks() {

        tracks = new ArrayList<>();

        tracks.add(new Track("John Williamns", "Arrivial of Baby Harry", R.drawable.hp));
    }

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getExtras().getString("actionname");

            //if (action.equals(DoNotification.Play)) {
            if (DoNotification.Play.equals(action)) {
                if (isPlaying) {
                    onTrackPause();
                } else {
                    onTrackPlay();
                }
            }
        }
    };

    @Override
    public void onTrackPrevious() {

        position--;

        DoNotification.doNotification(NotficationActivity.this, tracks.get(position),
                R.drawable.ic_pause_not, position, tracks.size()-1);
        title.setText(tracks.get(position).getTitle());
    }

    @Override
    public void onTrackPlay() {

        DoNotification.doNotification(NotficationActivity.this, tracks.get(position),
                R.drawable.ic_pause_not, position, tracks.size()-1);
        play.setImageResource(R.drawable.ic_pause_not);
        title.setText(tracks.get(position).getTitle());

        isPlaying = true;
    }

    @Override
    public void onTrackPause() {

        DoNotification.doNotification(NotficationActivity.this, tracks.get(position),
                R.drawable.ic_play_not, position, tracks.size()-1);
        play.setImageResource(R.drawable.ic_play_not);
        title.setText(tracks.get(position).getTitle());

        isPlaying = false;

    }

    @Override
    public void onTrackNext() {

        position++;

        DoNotification.doNotification(NotficationActivity.this, tracks.get(position),
                R.drawable.ic_pause_not, position, tracks.size()-1);
        title.setText(tracks.get(position).getTitle());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.cancelAll();
        }

        unregisterReceiver(br);
    }
}
