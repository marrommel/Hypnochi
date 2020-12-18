package com.example.hypnochi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.hypnochi.Service.KillNotificationService;
import com.example.hypnochi.Service.OnClearFromRecentService;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabAdapter adapter;
    NotificationManager nm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, KillNotificationService.class));

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);

        adapter = new TabAdapter(getSupportFragmentManager());

        adapter.addFragment(new HandoutFragment(), "Inhalte");
        adapter.addFragment(new HypnoseFragment(), "Hypnose");

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        boolean playing = MediaPlayerClass.openingMusicPlayer.isPlaying();

        createChannel();

        if(!playing) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                nm.cancelAll();
            }

            //unregisterReceiver(br);
        }
    }

    private void createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(DoNotification.ChannelId,
                    getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);

            nm = getSystemService(NotificationManager.class);

            assert nm != null;
            nm.createNotificationChannel(channel);
        }
    }
}