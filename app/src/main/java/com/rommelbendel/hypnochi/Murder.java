package com.example.hypnochi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Murder extends AppCompatActivity {

    CardView all;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_decision);

        all = findViewById(R.id.finalDecision);
        final ImageView[] suspects = {findViewById(R.id.ghost), findViewById(R.id.alien), findViewById(R.id.skelett)};
        final CardView[] totals = {findViewById(R.id.total1), findViewById(R.id.total2), findViewById(R.id.total3)};

        for(int i=0; i<3; i++) {
            final int finalI = i;
            suspects[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    all.setVisibility(View.GONE);
                    if(finalI == 0)
                        totals[0].setVisibility(View.VISIBLE);

                    else if(finalI == 1)
                        totals[1].setVisibility(View.VISIBLE);

                    else if(finalI == 2)
                        totals[2].setVisibility(View.VISIBLE);
                }
            });
        }

        for(int i=0; i<3; i++) {
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
