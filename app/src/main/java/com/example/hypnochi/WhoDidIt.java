package com.example.hypnochi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class WhoDidIt extends AppCompatActivity {

    CardView all;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_murder);

        all = findViewById(R.id.whoDidIt);
        final CardView[] numbers = {findViewById(R.id.even), findViewById(R.id.uneven)};
        final CardView[] totals = {findViewById(R.id.totalEven), findViewById(R.id.totalUneven)};

        for(int i=0; i<2; i++) {
            final int finalI = i;
            numbers[i].setOnClickListener(new View.OnClickListener() {
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

                    Intent intent = new Intent(WhoDidIt.this, Murder.class);
                    startActivity(intent);
                    finish();
                    return false;
                }
            });
        }
    }
}
