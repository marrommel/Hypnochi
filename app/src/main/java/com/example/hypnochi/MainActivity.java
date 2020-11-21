package com.example.hypnochi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);

        adapter = new TabAdapter(getSupportFragmentManager());

        adapter.addFragment(new HandoutFragment(), "Inhalte");
        adapter.addFragment(new HypnoseFragment(), "Hypnose");

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }
}