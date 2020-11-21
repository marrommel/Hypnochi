package com.example.hypnochi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class RedOrBlue extends AppCompatActivity {

    CardView all;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redoblue);

        all = findViewById(R.id.redOblueAll);
        final CardView[] colors = {findViewById(R.id.red), findViewById(R.id.blue)};
        final CardView[] totals = {findViewById(R.id.totalRed), findViewById(R.id.totalBlue)};

        for(int i=0; i<2; i++) {
            final int finalI = i;
            colors[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    all.setVisibility(View.GONE);
                    if(finalI == 0)
                      totals[0].setVisibility(View.VISIBLE);

                    else if(finalI == 1)
                        totals[1].setVisibility(View.VISIBLE);
                }
            });
        }

        for(int i=0; i<2; i++) {
            totals[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    finish();
                    return false;
                }
            });
        }
    }
}
